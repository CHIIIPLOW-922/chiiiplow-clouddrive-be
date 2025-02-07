package com.chiiiplow.clouddrive.config;

import com.chiiiplow.clouddrive.interceptor.AccessTokenInterceptor;
import com.chiiiplow.clouddrive.interceptor.RequestLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置
 *
 * @author yangzhixiong
 * @date 2025/01/10
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    private AccessTokenInterceptor accessTokenInterceptor;

    @Autowired
    private RequestLimitInterceptor requestLimitInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        //请求限制拦截器
        registry.addInterceptor(requestLimitInterceptor)
                .order(1)
                .addPathPatterns("/**");



        registry.addInterceptor(accessTokenInterceptor)
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/sendEmailCode", "/user/sendCaptcha");

    }


}
