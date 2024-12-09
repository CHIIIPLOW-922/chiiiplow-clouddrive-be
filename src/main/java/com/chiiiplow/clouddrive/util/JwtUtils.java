package com.chiiiplow.clouddrive.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JWT工具
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Component
public class JwtUtils {

    @Value(value = "${chiiiplow.clouddisk.jwt.secret}")
    private String secret;

    @Value(value = "${chiiiplow.clouddisk.jwt.expire}")
    private int expire;


}
