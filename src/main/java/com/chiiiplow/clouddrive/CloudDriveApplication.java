package com.chiiiplow.clouddrive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网盘！启动！
 *
 * @author yangzhixiong
 * @date 2024/12/05
 */
@SpringBootApplication(scanBasePackages = "com.chiiiplow.clouddrive")
@MapperScan(basePackages = "com.chiiiplow.clouddrive.mapper")
public class CloudDriveApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDriveApplication.class);
    }
}
