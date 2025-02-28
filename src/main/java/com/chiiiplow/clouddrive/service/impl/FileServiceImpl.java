package com.chiiiplow.clouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddrive.component.CustomMinioAsyncClient;
import com.chiiiplow.clouddrive.dto.FileDTO;
import com.chiiiplow.clouddrive.dto.PageDTO;
import com.chiiiplow.clouddrive.entity.File;
import com.chiiiplow.clouddrive.enums.FileCategoryEnum;
import com.chiiiplow.clouddrive.enums.FileTypeEnum;
import com.chiiiplow.clouddrive.exception.CustomException;
import com.chiiiplow.clouddrive.mapper.FileMapper;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.BeanCopyUtils;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.FileVO;
import com.chiiiplow.clouddrive.vo.FolderVO;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件业务实现
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    private FileMapper fileMapper;


    @Resource
    private CustomMinioAsyncClient customMinioAsyncClient;

    @Resource
    private MinioClient minioClient;


    @Override
    public R initUpload() {
        try {
            if (minioClient.statObject(StatObjectArgs.builder().build()) != null) {
                throw new CustomException("该文件已存在");
            }

        } catch (Exception e) {
            throw new CustomException("Minio服务出错");
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addFolder(FolderVO folderVO, String userId) {
        String parentId = !StringUtils.isBlank(folderVO.getParentId()) ? folderVO.getParentId() : "0";
        String folderName = folderVO.getFolderName();
        String id = folderVO.getId();
        if (StringUtils.isBlank(id) && fileMapper.exists(new LambdaQueryWrapper<File>()
                .eq(File::getParentId, parentId)
                .eq(File::getUserId, userId)
                .eq(File::getFileName, folderName)
                .eq(File::getIsFolder, 1))) {
            throw new CustomException("当前路径下已存在同名文件夹");
        }
        File file = new File().setFileName(folderName).setIsFolder(1).setUserId(userId).setParentId(parentId).setId(id).setFileType(FileCategoryEnum.FOLDER.getFileType());
        try {
            this.saveOrUpdate(file);
        } catch (Exception e) {
            log.info("添加文件夹错误:{}", e.getMessage());
            throw new CustomException("添加文件夹错误");
        }
        return R.ok(null, "操作成功!");
    }

    @Override
    public R<PageDTO<FileDTO>> pagesByPageQuery(FileVO fileVO, String currentUserId) {
        Page<File> page = new Page<>();
        page.setSize(fileVO.getPageSize()).setCurrent(fileVO.getPageNo());
        LambdaQueryWrapper<File> fileLambdaQueryWrapper = new LambdaQueryWrapper<File>().eq(File::getUserId, currentUserId);
        String parentId = fileVO.getParentId() != null ? fileVO.getParentId() : "0";
        String fileTypeName = fileVO.getFileTypeName();
        FileCategoryEnum fileCategoryEnum = FileCategoryEnum.fromCategory(fileTypeName);
        queryCondition(fileLambdaQueryWrapper, fileCategoryEnum, parentId);
        Page<File> filePage = fileMapper.selectPage(page, fileLambdaQueryWrapper);
        PageDTO<FileDTO> pageDTO = PageDTO.of(filePage, FileDTO.class);
        return R.ok(pageDTO, null);
    }

    private static void queryCondition(LambdaQueryWrapper<File> lambdaQueryWrapper, FileCategoryEnum fileCategoryEnum, String parentId) {
        if (ObjectUtils.isEmpty(fileCategoryEnum)) {
            lambdaQueryWrapper.eq(File::getParentId, parentId).eq(File::getDeleteStatus, "0");
            return;
        }
        if (fileCategoryEnum.getFileType().equals(FileCategoryEnum.RECYCLE.getFileType())) {
            lambdaQueryWrapper.eq(File::getDeleteStatus, "1");
        } else {
            lambdaQueryWrapper.eq(File::getFileType, fileCategoryEnum.getFileType()).eq(File::getDeleteStatus, "0");
        }

    }


    @Override
    public R<List<FileDTO>> breadcrumb(FileVO fileVO, String currentUserId) {
        List<FileDTO> breadcrumb = new ArrayList<>();
        String parentId = fileVO.getParentId();
        if (StringUtils.isBlank(parentId)) {
            return R.ok(breadcrumb, null);
        }
        List<File> files = fileMapper.selectList(new LambdaQueryWrapper<File>().eq(File::getUserId, currentUserId));
        List<FileDTO> fileDTOS = BeanCopyUtils.copyBeanList(files, FileDTO.class);
        treeBreadcrumb(fileDTOS, breadcrumb, parentId);
        Collections.reverse(breadcrumb);
        return R.ok(breadcrumb, null);
    }

    @Override
    public R<List<FileDTO>> search(FileVO fileVO, String currentUserId) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getUserId, currentUserId).eq(File::getDeleteStatus, "0").like(File::getFileName, fileVO.getSearch());
        List<File> files = fileMapper.selectList(queryWrapper);
        List<FileDTO> fileDTOS = BeanCopyUtils.copyBeanList(files, FileDTO.class);
        return R.ok(fileDTOS, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R recycle(FileVO fileVO, String currentUserId) {
        File file = new File().setId(fileVO.getId()).setDeleteStatus(1);
        try {
            this.updateById(file);
        } catch (Exception e) {
            throw new CustomException("文件删除至回收站失败");
        }
        return R.ok(null, "操作成功");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R delete(FileVO fileVO, String currentUserId) {
        Integer isFolder = fileVO.getIsFolder();
        if (isFolder == 1) {
        }
        return null;
    }

    private void treeBreadcrumb(List<FileDTO> files, List<FileDTO> breadcrumb, String currentId) {
        if (breadcrumb.size() == 4 || StringUtils.equals(currentId, "0")) {
            return;
        }
        FileDTO currentFile = files.stream().filter(file -> StringUtils.equals(file.getId(), currentId)).findFirst().orElseThrow(() -> new CustomException("找不到文件"));
        breadcrumb.add(currentFile);
        treeBreadcrumb(files, breadcrumb, currentFile.getParentId());
    }


}
