package com.chiiiplow.clouddrive.controller;

import com.chiiiplow.clouddrive.entity.File;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.FileVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件控制层
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@RestController
public class FileController extends BaseController {

    @Resource
    private IFileService fileService;


    @PostMapping("files/list")
    public R<List<File>> list(@RequestBody FileVO fileVO, HttpServletResponse response, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request, response);
        return fileService.listByFileType(fileVO, currentUserId, response, request);
    }


    @PostMapping("files/initUpload")
    public R initUpload(HttpServletRequest request, HttpServletResponse response) {
        Long currentUserId = getCurrentUserId(request, response);
        return null;
    }


}
