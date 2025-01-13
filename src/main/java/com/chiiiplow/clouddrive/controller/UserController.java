package com.chiiiplow.clouddrive.controller;

import com.chiiiplow.clouddrive.dto.CaptchaDTO;
import com.chiiiplow.clouddrive.service.IUserService;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.validation.Group;
import com.chiiiplow.clouddrive.vo.CaptchaVO;
import com.chiiiplow.clouddrive.vo.EmailVO;
import com.chiiiplow.clouddrive.vo.LoginVO;
import com.chiiiplow.clouddrive.vo.RegisterVO;
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


    @PostMapping("/sendCaptcha")
    public R<CaptchaDTO> sendCaptcha(@RequestBody CaptchaVO captcha, HttpServletRequest request) throws IOException {
//        log.info();
        return userService.sendCaptcha(captcha);

    }


    @PostMapping("/sendEmailCode")
    public R sendEmailCode(@RequestBody @Validated EmailVO emailVO) {
        return userService.sendEmailCode(emailVO);
    }

    @PostMapping("/editProfile")
    public R editProfile(@RequestBody Map<String, Object> body) {
        return userService.editProfile(body);
    }
}
