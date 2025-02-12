package com.chiiiplow.clouddrive.controller;


import com.chiiiplow.clouddrive.enums.HttpCode;
import com.chiiiplow.clouddrive.exception.CustomException;
import com.chiiiplow.clouddrive.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    protected String getCurrentUserId(HttpServletRequest request, HttpServletResponse response) {
        String accessToken;
        String authorization  = response.getHeader("Authorization");
        accessToken = StringUtils.isEmpty(authorization) ? request.getHeader("Authorization").substring(7) : authorization;
        if (StringUtils.isEmpty(accessToken)) {
            throw new CustomException(HttpCode.UNAUTHORIZED.getCode(), HttpCode.UNAUTHORIZED.getMessage());
        }
        Claims claims = jwtUtils.validatedAccessToken(accessToken);
        if (ObjectUtils.isEmpty(claims)) {
            throw new CustomException("获取UserId失败");
        }
        Long userId = (Long) claims.get("userId");
        String strUserId = userId.toString();
        return strUserId;
    }
}
