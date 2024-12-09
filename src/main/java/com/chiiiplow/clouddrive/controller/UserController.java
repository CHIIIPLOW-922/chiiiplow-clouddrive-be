package com.chiiiplow.clouddrive.controller;

import com.chiiiplow.clouddrive.service.IUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户控制层
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@RestController
public class UserController extends BaseController {

    @Resource
    private IUserService userService;




}
