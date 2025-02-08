package com.chiiiplow.clouddrive;

import com.chiiiplow.clouddrive.config.MinioInfo;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始 Bucket Runner
 *
 * @author yangzhixiong
 * @date 2025/02/07
 */
@Slf4j
@Component
public class InitialBucketRunner implements ApplicationRunner {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioInfo minioInfos;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioInfos.getBucketName()).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioInfos.getBucketName()).build());
            log.info("created Bucket: {}", minioInfos.getBucketName());
        }
        log.info("Bucket: {} Ready", minioInfos.getBucketName());
    }
}
