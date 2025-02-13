package com.chiiiplow.clouddrive;

import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.enums.FileCategoryEnum;
import com.chiiiplow.clouddrive.mapper.UserMapper;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.EmailUtils;
import com.chiiiplow.clouddrive.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class CloudDriveApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private EmailUtils emailUtils;

    @Resource
    private IFileService fileService;

    @Test
    void emailTest(){
        emailUtils.sendEmail("q641484973@gmail.com", "test", "testtest");
    }

    @Test
    void redisTest() {

    }

    @Test
    void test2() {
        User user = new User().setUsername("test").setNickname("test1").setEmail("test123@qq.com").setPassword("123123").setSalt("123123");
        userMapper.insert(user);
    }

    @Test
    void testMp(){
        Object o = redisUtils.get("user:email:q641484973@gmail.com");
        System.out.println(o);

    }


    public static void main(String[] args) {
        System.out.println(FileCategoryEnum.fromCategory("Audio").getFileType());
    }
}
