package com.chiiiplow.clouddrive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chiiiplow.clouddrive.entity.User;
import com.chiiiplow.clouddrive.enums.FileCategoryEnum;
import com.chiiiplow.clouddrive.mapper.UserMapper;
import com.chiiiplow.clouddrive.service.IFileService;
import com.chiiiplow.clouddrive.util.CommonUtils;
import com.chiiiplow.clouddrive.util.EmailUtils;
import com.chiiiplow.clouddrive.util.RedisUtils;
import com.google.common.hash.Hashing;
import com.sun.xml.bind.v2.model.runtime.RuntimeEnumLeafInfo;
import io.lettuce.core.RedisBusyException;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.Collectors;

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
    private MinioClient minioClient;

    @Resource
    private IFileService fileService;

    @Value("${siliconflow.token}")
    private String token;


    @Test
    void emailTest() {
        emailUtils.sendEmail("q641484973@gmail.com", "test", "testtest");
    }

    @Test
    void minioUtils() {
    }

    @Test
    void test01() {
        System.out.println(token);
    }

    @Test
    void test2() {
        User user = new User().setUsername("test").setNickname("test1").setEmail("test123@qq.com").setPassword("123123").setSalt("123123");
        userMapper.insert(user);
    }

    @Test
    void testMp() {
        Object o = redisUtils.get("user:email:q641484973@gmail.com");
        System.out.println(o);

    }

    @Test
    void testPassword() {

    }


    // 固定的起始时间戳（2024-01-01）
    private static final long FIXED_TIMESTAMP = new Date("2025-08-01").getTime();

    // 提取字段组合的 hash 值的后缀部分（4位数字）
    private static int hashSuffix(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            int tail = 0;
            for (int i = hash.length - 4; i < hash.length; i++) {
                tail = (tail << 4) | (hash[i] & 0x0F); // 取低4位 nibbles
            }
            return Math.abs(tail % 10_000); // 保留4位数字
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long generateId(String orgCode, String vcode, String materialCode) {
        String key = orgCode + "|" + vcode + "|" + materialCode;
        int suffix = hashSuffix(key);                      // 取 hash 后缀扰动
        return FIXED_TIMESTAMP * 10_000 + suffix;          // 拼成一个 18~19 位纯数字 ID
    }

    public static void main(String[] args) {
//        try (FileInputStream inputStream = new FileInputStream("/Users/jojireal/Documents/立高-用友BIP项目资料/cost_price.json");){
//            String next = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
//            JSONObject resp = JSONObject.parseObject(next);
//            JSONArray data = resp.getJSONArray("data");
//            List<String> productCodes = data.stream().map(item -> (JSONObject) item).map(jsonObject -> "\"" + jsonObject.getString("material_code") + "\"").collect(Collectors.toList());
//            System.out.println(productCodes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(generateId("101", "222", "E004070095"));
        System.out.println(generateId("101", "999", "E004070096"));
        System.out.println(generateId("101", "999", "E004070097"));
        System.out.println(generateId("101", "999", "E004070098"));
        System.out.println(generateId("101", "999", "E004070095"));

    }

    public static long stableMurmurId(String key) {
        return Hashing.murmur3_128()
                .hashString(key, StandardCharsets.UTF_8)
                .asLong();  // 返回前64位
    }

}
