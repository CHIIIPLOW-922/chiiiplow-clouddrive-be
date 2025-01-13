package com.chiiiplow.clouddrive;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.mapper.UserMapper;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.EmailUtils;
import com.chiiiplow.clouddrive.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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

    @Test
    void test() {
        System.out.println(111);
    }

    public static void main(String[] args) {
        String s = "abcba";
        char[] chars = s.toCharArray();
        int r = 0, l = chars.length - 1;
        for (int i = l; i > 0; i--) {

        }
    }
}
