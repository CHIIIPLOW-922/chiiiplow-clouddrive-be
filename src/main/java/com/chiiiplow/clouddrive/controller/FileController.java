package com.chiiiplow.clouddrive.controller;

import com.chiiiplow.clouddrive.service.IFileService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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




}
