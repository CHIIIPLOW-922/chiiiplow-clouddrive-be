package com.chiiiplow.clouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddrive.component.CustomMinioAsyncClient;
import com.chiiiplow.clouddrive.entity.File;
import com.chiiiplow.clouddrive.exception.CustomException;
import com.chiiiplow.clouddrive.mapper.FileMapper;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.FileVO;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 文件业务实现
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private CustomMinioAsyncClient customMinioAsyncClient;

    @Resource
    private MinioClient minioClient;




    @Override
    public R<List<File>> listByFileType(FileVO fileVO, Long currentUserId, HttpServletResponse response, HttpServletRequest request) {

        String fileType = fileVO.getFileTypeName();
        List<File> files = fileMapper.selectList(new LambdaQueryWrapper<File>().eq(File::getUserId, currentUserId));
        return R.ok(files, "获取当前用户文件信息成功");
    }

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
}
