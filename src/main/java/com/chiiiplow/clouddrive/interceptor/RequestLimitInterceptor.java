package com.chiiiplow.clouddrive.interceptor;


import com.chiiiplow.clouddrive.constants.RedisConstants;
import com.chiiiplow.clouddrive.enums.HttpCode;
import com.chiiiplow.clouddrive.util.CommonUtils;
import com.chiiiplow.clouddrive.util.R;
import com.chiiiplow.clouddrive.util.RedisUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求限制拦截器
 *
 * @author yangzhixiong
 * @date 2025/01/30
 */
@Component
@Slf4j
public class RequestLimitInterceptor implements HandlerInterceptor {

    private static final int TIME_WINDOW = 1;

    private static final int LIMIT_COUNT = 10;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = CommonUtils.getIpAddress(request);
        if (!redisUtils.allowRequestWithLua(RedisConstants.IP_KEY + ipAddress, LIMIT_COUNT, TIME_WINDOW)) {
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(R.http(HttpCode.LIMIT_REQUESTED, null)));
            return false;
        }
        return true;
    }


}
