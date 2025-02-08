package com.chiiiplow.clouddrive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 属性
 *
 * @author yangzhixiong
 * @date 2025/01/14
 */
@ConfigurationProperties(prefix = "minio")
@Data
@Configuration
public class MinioInfo {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String host;
}
