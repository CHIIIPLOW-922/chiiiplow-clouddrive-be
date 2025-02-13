package com.chiiiplow.clouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddrive.dto.FileDTO;
import com.chiiiplow.clouddrive.dto.PageDTO;
import com.chiiiplow.clouddrive.entity.File;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.FileVO;
import com.chiiiplow.clouddrive.vo.FolderVO;

import java.util.List;


/**
 * 文件业务接口
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public interface IFileService extends IService<File> {

    /**
     * 列表
     * 按文件类型列出
     *
     * @param fileVO        文件 vo
     * @param currentUserId 当前用户 ID
     * @return {@link R}<{@link List}<{@link FileDTO}>>
     */
//    R<List<FileDTO>> list(FileVO fileVO, Long currentUserId);

    /**
     * 初始化上传
     *
     * @return {@link R}
     */
    R initUpload();


    /**
     * 添加文件夹
     *
     * @param userId   用户 ID
     * @param folderVO 文件夹 vo
     * @return {@link R}
     */
    R addFolder(FolderVO folderVO, String userId);

    /**
     * 逐页查询
     *
     * @param fileVO        文件 vo
     * @param currentUserId 当前用户 ID
     * @return {@link R}<{@link PageDTO}<{@link FileDTO}>>
     */
    R<PageDTO<FileDTO>> pagesByPageQuery(FileVO fileVO, String currentUserId);

    /**
     * 面包屑
     *
     * @param currentUserId 当前用户 ID
     * @param fileVO        文件 vo
     * @return {@link R}<{@link List}<{@link FileDTO}>>
     */
    R<List<FileDTO>> breadcrumb(FileVO fileVO, String currentUserId);

    /**
     * 搜索
     *
     * @param fileVO        文件 vo
     * @param currentUserId 当前用户 ID
     * @return {@link R}<{@link PageDTO}<{@link FileDTO}>>
     */
    R<List<FileDTO>> search(FileVO fileVO, String currentUserId);
}
