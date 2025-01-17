package com.chiiiplow.clouddrive.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chiiiplow.clouddrive.dto.CaptchaDTO;
import com.chiiiplow.clouddrive.dto.UserInfoDTO;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 用户业务接口
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public interface IUserService extends IService<User> {


    /**
     * 注册
     *
     * @param registerVO 注册 VO
     * @return {@link R}
     */
    R register(RegisterVO registerVO);


    /**
     * 登录
     *
     * @param loginVO 登录 VO
     * @param response
     * @return {@link R}
     */
    R login(LoginVO loginVO, HttpServletResponse response);

    /**
     * 发送验证码
     *
     * @return {@link R}
     * @param captchaVO
     */
    R<CaptchaDTO> sendCaptcha(CaptchaVO captchaVO) throws IOException;


    /**
     * 发送电子邮件代码
     * @param emailVO
     * @return {@link R}
     */
    R sendEmailCode(EmailVO emailVO);

    /**
     * 编辑个人资料
     *
     * @param editProfileVO 编辑个人资料 VO
     * @return {@link R}
     */
    R editProfile(EditProfileVO editProfileVO, Long currentUserId);

    /**
     * 刷新AccessToken
     *
     * @return {@link R}
     * @param request
     * @param response
     */
    R refresh(HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户信息
     *
     * @param userId 用户 ID
     * @return {@link R}<{@link UserInfoDTO}>
     */
    R<UserInfoDTO> userInfo(Long userId);

    /**
     * 注销
     *
     * @return {@link R}
     */
    R logout(HttpServletRequest request, HttpServletResponse response);
}
