package com.chiiiplow.clouddrive.config;

import com.chiiiplow.clouddrive.component.CustomMinioAsyncClient;
import com.chiiiplow.clouddrive.exception.CustomException;
import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * minio 配置
 *
 * @author yangzhixiong
 * @date 2025/01/14
 */
@Configuration
@Order(1)
public class MinioConfig {


    @Resource
    private MinioInfo minioInfos;

    @Bean
    public MinioClient minioClient() {
        try {
            return new MinioClient.Builder()
                    .endpoint(minioInfos.getHost())
                    .credentials(minioInfos.getAccessKey(), minioInfos.getSecretKey())
                    .build();
        } catch (Exception e) {
            throw new CustomException("MinIO服务初始化失败", e);
        }
    }

    @Bean
    public CustomMinioAsyncClient customMinioAsyncClient() {
        try {
            MinioAsyncClient minioAsyncClient = new MinioAsyncClient.Builder()
                    .endpoint(minioInfos.getHost())
                    .credentials(minioInfos.getAccessKey(), minioInfos.getSecretKey())
                    .build();
            return new CustomMinioAsyncClient(minioAsyncClient);
        } catch (Exception e) {
            throw new CustomException("异步MinIO服务初始化失败", e);
        }
    }
}
