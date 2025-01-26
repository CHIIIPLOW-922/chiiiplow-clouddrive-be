package com.chiiiplow.clouddrive.util;

import com.chiiiplow.clouddrive.constants.CommonConstant;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.exception.CustomException;
import com.chiiiplow.clouddrive.vo.LoginVO;
import com.mysql.cj.exceptions.DataReadException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT工具
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Component
public class JwtUtils {

    @Value(value = "${chiiiplow.clouddisk.jwt.access.secret}")
    private String ACCESS_SECRET;

    @Value(value = "${chiiiplow.clouddisk.jwt.refresh.secret}")
    private String REFRESH_SECRET;

    @Value(value = "${chiiiplow.clouddisk.jwt.access.expire}")
    private int ACCESS_EXPIRE;

    @Value(value = "${chiiiplow.clouddisk.jwt.refresh.expire}")
    private int REFRESH_EXPIRE;



    /**
     * 生成访问令牌
     *
     * @param user 用户
     * @return {@link String}
     */
    public String generateAccessToken(User user) {
        long now = System.currentTimeMillis();
        String jwtId = UUID.randomUUID().toString();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", user.getUsername());
        resultMap.put("userId", user.getId());
        Date expiration = new Date(now + CommonConstant.ONE_MINUTE * ACCESS_EXPIRE);
        return Jwts.builder()
                .setClaims(resultMap)
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .setId(jwtId)
                .signWith(SignatureAlgorithm.HS512, ACCESS_SECRET)
                .compact();
    }

    /**
     * 生成刷新令牌
     *
     * @param user 用户
     * @return {@link String}
     */
    public String generateRefreshToken(User user){
        long now = System.currentTimeMillis();
        String jwtId = UUID.randomUUID().toString();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", user.getUsername());
        resultMap.put("userId", user.getId());
        Date expiration = new Date(now + CommonConstant.ONE_MINUTE * REFRESH_EXPIRE);
        return Jwts.builder()
                .setClaims(resultMap)
                .setSubject(user.getUsername())
                .setId(jwtId)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET)
                .compact();
    }


    /**
     * 获取访问令牌
     *
     * @param token 令 牌
     * @return {@link Claims}
     */
    public Claims validatedAccessToken(String token) {
        try {
            return Jwts.parser().setSigningKey(ACCESS_SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取刷新令牌
     *
     * @param token 令 牌
     * @return {@link Claims}
     */
    public Claims validateRefreshToken(String token) {
        try {
            return Jwts.parser().setSigningKey(REFRESH_SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 清除刷新令牌
     *
     * @param response 响应
     */
    public void cleanRefreshToken(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
    }

}
