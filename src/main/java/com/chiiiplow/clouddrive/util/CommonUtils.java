package com.chiiiplow.clouddrive.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Random;

/**
 * 通用工具
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public class CommonUtils {

    private static final String UNKNOWN = "unknown";

    // 定义硬盘空间常量
    private static final long KB = 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;
    private static final long TB = GB * 1024;


    /**
     * 将字节大小转换为带单位的字符串表示
     *
     * @param bytes 字节大小
     * @return 带单位的字符串表示
     */
    public static String convertBytesToReadableSize(long bytes) {
        if (bytes < KB) {
            return bytes + " B";
        } else if (bytes < MB) {
            return String.format("%.00fKB", (double) bytes / KB);
        } else if (bytes < GB) {
            return String.format("%.1fMB", (double) bytes / MB);
        } else if (bytes < TB) {
            return String.format("%.2fGB", (double) bytes / GB);
        } else {
            return String.format("%.1fTB", (double) bytes / TB);
        }
    }

    /**
     * 计算已用空间%
     *
     * @param used  使用
     * @param total 总
     * @return float
     */
    public static float calculateUsedSpaceRate(Long used, Long total) {
        if (total == 0) {
            throw new IllegalArgumentException("分母不能为0");
        }

        // 将Long转换为float进行浮点数除法
        float result = (float) used / total * 100;

        // 格式化为两位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedResult = decimalFormat.format(result);

        // 将格式化后的字符串转换回float
        return Float.parseFloat(formattedResult);
    }

    /**
     * 获取 IP 地址
     *
     * @param request 请求
     * @return {@link String}
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个 IP 值，第一个 IP 为客户端真实 IP
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0  || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 生成盐
     *
     * @return {@link String}
     */
    public static String generateSalt() {
        // 创建一个 SecureRandom 对象
        SecureRandom random = new SecureRandom();
        // 生成一个足够长的盐值，通常至少 16 个字节
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        // 将盐值转换为十六进制字符串
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 加密
     *
     * @param originalPassword 原始密码
     * @param salt             盐
     * @return {@link String}
     */
    public static String encode(String originalPassword, String salt) {
        String saltedPassword = originalPassword + salt;
        return DigestUtils.sha256Hex(saltedPassword);
    }



    /**
     * 生成 CAPTCHA 图像
     *
     * @return {@link String}
     * @throws IOException io异常
     */
    public static String generateCaptchaImage(String captchaText) throws IOException {
        int width = 115;
        int height = 35;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();

        Random random = new Random();

        // 设置背景颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 设置字体
        graphics.setFont(new Font("Fixedsys", Font.BOLD, 30));

        // 绘制扭曲的文本
        for (int i = 0; i < captchaText.length(); i++) {
            graphics.setColor(getRandomColor());
            char c = captchaText.charAt(i);
            int x = 10 + i * 20;
            int y = 25 + random.nextInt(6) - 3;
            graphics.translate(x, y);
            double theta = (random.nextDouble() - 0.5) * 0.2;
            graphics.rotate(theta);
            graphics.drawString(String.valueOf(c), 0, 0);
            graphics.rotate(-theta);
            graphics.translate(-x, -y);
        }

        // 添加干扰线
        for (int i = 0; i < 8; i++) {
            graphics.setColor(getRandomColor());
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }

        // 添加噪点
        for (int i = 0; i < width * height * 0.02; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomColor().getRGB();
            bufferedImage.setRGB(x, y, rgb);
        }

        graphics.dispose();
        byte[] imageBytes = null;
        // 将 BufferedImage 转换为字节数组
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
        }
        // Write the image to the response output stream

        // 将字节数组编码为 Base64 字符串
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // 构建 data:image 格式字符串
        String dataImage = "data:image/png;base64," + base64Image;
        return dataImage;
    }

    private static Color getRandomColor() {
        Random random = new Random();
        float hue = random.nextFloat();
        float saturation = 0.7f;
        float brightness = 0.9f;
        return Color.getHSBColor(hue, saturation, brightness);
    }

    /**
     * 生成 CAPTCHA 文本
     *
     * @param length 长度
     * @return {@link String}
     */
    public static String generateCaptchaText(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            captchaText.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captchaText.toString();
    }
}
