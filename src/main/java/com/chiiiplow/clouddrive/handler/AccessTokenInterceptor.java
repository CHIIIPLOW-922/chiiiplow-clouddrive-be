package com.chiiiplow.clouddrive.handler;

import com.chiiiplow.clouddrive.util.JwtUtils;
import com.chiiiplow.clouddrive.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

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
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            log.info("无token");
            writeResponse(response, R.fail(555, "账户验证失效"));
            return false;
//            throw new CustomException()
        }
        String accessToken = token.substring(7);
        Claims claims = jwtUtils.validatedAccessToken(accessToken);
        if (Objects.isNull(claims)) {
            log.info("token错误");
            writeResponse(response, R.fail(401, null));
            return false;
        }
        return true;
    }

    private void writeResponse(HttpServletResponse response, R<?> result) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(result));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), "refresh_token")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
