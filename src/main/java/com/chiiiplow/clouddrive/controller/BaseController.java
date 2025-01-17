package com.chiiiplow.clouddrive.controller;


import com.chiiiplow.clouddrive.exception.CustomException;
import com.chiiiplow.clouddrive.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基础控制层
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Slf4j
public class BaseController {

    @Resource
    private JwtUtils jwtUtils;


    /**
     * 获取当前用户 ID
     *
     * @param request 请求
     * @return {@link Long}
     */
    protected Long getCurrentUserId(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.substring(7);
        Claims claims = jwtUtils.validatedAccessToken(accessToken);
        if (ObjectUtils.isEmpty(claims)) {
            throw new CustomException("获取UserId失败");
        }
        Long userId = (Long) claims.get("userId");
        return userId;
    }
}
