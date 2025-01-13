package com.chiiiplow.clouddrive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddrive.constants.CommonConstant;
import com.chiiiplow.clouddrive.constants.RedisConstants;
import com.chiiiplow.clouddrive.dto.CaptchaDTO;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.exception.CustomException;
import com.chiiiplow.clouddrive.mapper.UserMapper;
import com.chiiiplow.clouddrive.service.IUserService;
import com.chiiiplow.clouddrive.util.*;
import com.chiiiplow.clouddrive.vo.CaptchaVO;
import com.chiiiplow.clouddrive.vo.EmailVO;
import com.chiiiplow.clouddrive.vo.LoginVO;
import com.chiiiplow.clouddrive.vo.RegisterVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 用户业务实现
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtils redisUtils;


    @Resource
    private EmailUtils emailUtils;

    @Resource
    private JwtUtils jwtUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R register(RegisterVO registerVO) {
        String redisEmailCode = (String) redisUtils.get(RedisConstants.EMAIL_KEY + registerVO.getEmail());
        if (!StringUtils.equals(redisEmailCode, registerVO.getEmailValidCode())) {
            throw new CustomException("邮箱验证码错误，请重试！");
        }
        String password = registerVO.getPassword();
        String repassword = registerVO.getRepassword();
        if (!StringUtils.equals(password, repassword)) {
            throw new CustomException("两次密码输入不一致，请重试！");
        }
        User registerUser = new User();
        BeanUtils.copyProperties(registerVO, registerUser);
        String salt = CommonUtils.generateSalt();
        String encodePassword = CommonUtils.encode(password, salt);
        registerUser
                .setPassword(encodePassword)
                .setSalt(salt)
                .setNickname(registerUser.getUsername())
                //注册账号，初始给10GB空间
                .setTotalSpace(CommonConstant.ONE_GB * 10);
        try {
            userMapper.insert(registerUser);
        } catch (DuplicateKeyException dke) {
            throw new CustomException("账号或邮箱已存在");
        } catch (Exception e) {
            throw new CustomException("注册用户失败,请重试!");
        }
        return R.ok(null, "注册成功！");
    }

    @Override
    public R login(LoginVO loginVO, HttpServletResponse response) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginVO.getUsername()));
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("用户不存在!");
        }
        if (Boolean.valueOf(loginVO.getShowCaptcha())) {
            String captchaRedisCode = (String) redisUtils.get(RedisConstants.CAPTCHA_KEY + loginVO.getCaptchaKey());
            if (StringUtils.isEmpty(loginVO.getCaptchaCode())) {
                throw new CustomException("图形验证码为空");
            }
            if (!StringUtils.equalsIgnoreCase(captchaRedisCode, loginVO.getCaptchaCode())) {
                throw new CustomException("图形验证码错误或过期");
            }
        }
        String salt = user.getSalt();
        String loginPassword = loginVO.getPassword();
        String encodePassword = CommonUtils.encode(loginPassword, salt);
        if (!StringUtils.equals(user.getPassword(), encodePassword)) {
            throw new CustomException("密码错误");
        }
        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        response.addHeader("Authorization", accessToken);
        Cookie cookieRefreshToken = new Cookie("refreshToken", refreshToken);
        cookieRefreshToken.setHttpOnly(true);
        cookieRefreshToken.setSecure(true);
        cookieRefreshToken.setPath("/");
        cookieRefreshToken.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookieRefreshToken);

        return R.ok(null, "登录成功!");
    }

    @Override
    public R<CaptchaDTO> sendCaptcha(CaptchaVO captchaVO) throws IOException {
        String captchaKey = StringUtils.isBlank(captchaVO.getCaptchaKey()) ? UUID.randomUUID().toString() : captchaVO.getCaptchaKey();
        String captchaText = CommonUtils.generateCaptchaText(4);
        redisUtils.setex(RedisConstants.CAPTCHA_KEY + captchaKey, captchaText, CommonConstant.ONE_MINUTE);
        String captchaImage = CommonUtils.generateCaptchaImage(captchaText);
        CaptchaDTO captchaDTO = new CaptchaDTO().setCaptchaKey(captchaKey).setCaptchaImage(captchaImage);
        return R.ok(captchaDTO, null);
    }

    @Override
    public R sendEmailCode(EmailVO emailVO) {
        String email = emailVO.getEmail();
        if (userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getEmail, email))) {
            throw new CustomException("该邮箱已被使用！");
        }
        String verifyCode = String.format("%06d", (int) (Math.random() * 1000000));
        redisUtils.setex(RedisConstants.EMAIL_KEY + email, verifyCode, CommonConstant.ONE_MINUTE * 10);
        emailUtils.sendEmail(email, "[CloudDisk网盘系统]短信验证", buildEmailContent(verifyCode));

        return R.ok(null, "发送成功");
    }

    @Override
    public R editProfile(Map<String, Object> body) {
        return null;
    }

    @Override
    public R refresh(HttpServletRequest request, HttpServletResponse response) {
        String requestRefreshToken = getRequestRefreshToken(request);
        if (StringUtils.isEmpty(requestRefreshToken)) {
            cleanRefreshToken(response);
            throw new CustomException(555, "刷新Token为空");
        }
        Claims claims = jwtUtils.validateRefreshToken(requestRefreshToken);
        if (Objects.isNull(claims)) {
            cleanRefreshToken(response);
            throw new CustomException(555, "刷新Token失效");
        }
        Long userId = (Long) claims.get("userId");
        String username = (String) claims.get("username");
        if (!userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getUsername, username).eq(User::getId, userId))) {
            cleanRefreshToken(response);
            throw new CustomException(555, "Token校验用户不存在");
        }
        User user = new User().setId(userId).setUsername(username);
        String accessToken = jwtUtils.generateAccessToken(user);
        response.addHeader("Authorization", accessToken);
        return R.ok(null, "Token刷新成功");
    }


    private void cleanRefreshToken(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 设置为过期
        response.addCookie(refreshTokenCookie);
    }

    private String getRequestRefreshToken(HttpServletRequest request) {
        String refreshToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (StringUtils.equals(cookie.getName(), "refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }
        return refreshToken;
    }

    private String buildEmailContent(String verifyCode) {
        StringBuilder builder = new StringBuilder();
        builder.append("[CloudDisk网盘系统] 亲爱的用户，您的邮箱验证码为: ")
                .append(verifyCode)
                .append("。请在10分钟内，输入此验证码进行验证。如果您没有请求该系统邮箱验证码，请忽略此邮件，谢谢！[CHIIIPLOW]");
        return builder.toString();
    }


}
