package com.chiiiplow.clouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddrive.entity.File;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.FileVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 文件业务接口
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public interface IFileService extends IService<File> {

    /**
     * 按文件类型列出
     *
     * @param fileVO   文件 vo
     * @param currentUserId
     * @param response 响应
     * @param request  请求
     * @return {@link R}<{@link List}<{@link File}>>
     */
    R<List<File>> listByFileType(FileVO fileVO, Long currentUserId, HttpServletResponse response, HttpServletRequest request);

    /**
     * 初始化上传
     *
     * @return {@link R}
     */
    R initUpload();
}
