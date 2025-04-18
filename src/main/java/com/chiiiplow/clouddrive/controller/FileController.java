package com.chiiiplow.clouddrive.controller;

import com.chiiiplow.clouddrive.dto.FileDTO;
import com.chiiiplow.clouddrive.dto.PageDTO;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.BreadcrumbVO;
import com.chiiiplow.clouddrive.vo.FileVO;
import com.chiiiplow.clouddrive.vo.FolderVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("file")
public class FileController extends BaseController {

    @Resource
    private IFileService fileService;


    @PostMapping("pages")
    public R<PageDTO<FileDTO>> pages(@RequestBody FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return fileService.pagesByPageQuery(fileVO, currentUserId);
    }

    @PostMapping("search")
    public R<List<FileDTO>> search(@RequestBody @Validated FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return fileService.search(fileVO, currentUserId);
    }

    @PostMapping("breadcrumb")
    public R<List<FileDTO>> breadcrumb(@RequestBody FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return fileService.breadcrumb(fileVO, currentUserId);
    }

    @PostMapping("recycle")
    public R recycle(@RequestBody FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return fileService.recycle(fileVO, currentUserId);
    }


    @PostMapping("initUpload")
    public R initUpload(HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return null;
    }

    @PostMapping("addFolder")
    public R addFolder(@RequestBody @Validated FolderVO folderVO, HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return fileService.addFolder(folderVO, currentUserId);
    }


    @PostMapping("delete")
    public R delete(@RequestBody FileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
        String currentUserId = getCurrentUserId(request, response);
        return fileService.delete(fileVO, currentUserId);
    }



}
