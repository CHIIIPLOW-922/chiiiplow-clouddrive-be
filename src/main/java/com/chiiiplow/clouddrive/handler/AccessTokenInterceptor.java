package com.chiiiplow.clouddrive.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.enums.HttpCode;
import com.chiiiplow.clouddrive.mapper.UserMapper;
import com.chiiiplow.clouddrive.util.JwtUtils;
import com.chiiiplow.clouddrive.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * AccessToken 拦截器
 *
 * @author yangzhixiong
 * @date 2025/01/06
 */
@Component
@Slf4j
public class AccessTokenInterceptor implements HandlerInterceptor {



    @Autowired
    private JwtUtils jwtUtils;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截成功"+request.getRequestURI());
        String accessToken = getAccessToken(request);
        Claims claims = jwtUtils.validatedAccessToken(accessToken);
        if (!Objects.isNull(claims)) {
            return true;
        }
        String refreshToken = getRequestRefreshToken(request);
        if (StringUtils.isEmpty(refreshToken)) {
            jwtUtils.cleanRefreshToken(response);
            writeResponse(response, R.http(HttpCode.UNAUTHORIZED, null));
            return false;
        }
        Claims refreshTokenClaims = jwtUtils.validateRefreshToken(refreshToken);
        if (Objects.isNull(refreshTokenClaims)) {
            jwtUtils.cleanRefreshToken(response);
            writeResponse(response, R.http(HttpCode.UNAUTHORIZED, null));
            return false;
        }
        Long userId = (Long) refreshTokenClaims.get("userId");
        String username = (String) refreshTokenClaims.get("username");
        User user = new User().setId(userId).setUsername(username);
        String refreshAccessToken = jwtUtils.generateAccessToken(user);
        response.setHeader("Authorization", refreshAccessToken);
        return true;
    }


    /**
     * 获取请求刷新令牌
     *
     * @param request 请求
     * @return {@link String}
     */
    private String getRequestRefreshToken(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                if (StringUtils.equals(cookie.getName(), "refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        return refreshToken;
    }




    /**
     * 获取访问令牌
     *
     * @param request 请求
     * @return {@link String}
     */
    private String getAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer")) {return null;}
        return authorization.substring(7);
    }

    /**
     * 写入响应
     *
     * @param response 响应
     * @param result   结果
     */
    private void writeResponse(HttpServletResponse response, R<?> result) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
