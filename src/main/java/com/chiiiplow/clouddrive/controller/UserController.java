package com.chiiiplow.clouddrive.controller;

import com.chiiiplow.clouddrive.dto.CaptchaDTO;
import com.chiiiplow.clouddrive.dto.UserInfoDTO;
import com.chiiiplow.clouddrive.service.IUserService;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.validation.Group;
import com.chiiiplow.clouddrive.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户控制层
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Resource
    private IUserService userService;


    @PostMapping("/register")
    public R register(@RequestBody @Validated(Group.class) RegisterVO registerVO, HttpServletRequest request, HttpServletResponse response) {
        return userService.register(registerVO);
    }

    @PostMapping("/refresh")
    public R refresh(HttpServletRequest request, HttpServletResponse response) {
        return userService.refresh(request, response);
    }


    @PostMapping("/login")
    public R login(@RequestBody @Validated(Group.class) LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) {
        return userService.login(loginVO, response);
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }


    @PostMapping("/sendCaptcha")
    public R<CaptchaDTO> sendCaptcha(@RequestBody CaptchaVO captcha, HttpServletRequest request) throws IOException {
        return userService.sendCaptcha(captcha);
    }


    @PostMapping("/sendEmailCode")
    public R sendEmailCode(@RequestBody @Validated EmailVO emailVO) {
        return userService.sendEmailCode(emailVO);
    }

    @PostMapping("/editProfile")
    public R editProfile(@RequestBody @Validated(Group.class) EditProfileVO editProfileVO, HttpServletRequest request, HttpServletResponse response) {
        Long currentUserId = getCurrentUserId(request);
        return userService.editProfile(editProfileVO, currentUserId);
    }


    @PostMapping("/userInfo")
    public R<UserInfoDTO> userInfo(HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        return userService.userInfo(currentUserId);
    }

}
