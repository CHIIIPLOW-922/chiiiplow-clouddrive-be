package com.chiiiplow.clouddrive.util;

import com.chiiiplow.clouddrive.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 邮箱工具
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Component
public class EmailUtils {

    @Resource
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String senderMail;


    /**
     * 发送验证码到电子邮件
     *
     * @param to      自
     * @param subject 主题
     * @param text    发短信
     */
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(senderMail);
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new CustomException("邮件发送失败");
        }
    }


    public String buildEmailContent(String verifyCode) {
        StringBuilder builder = new StringBuilder();
        builder.append("[CloudDisk网盘系统] 亲爱的用户，您的邮箱验证码为: ")
                .append(verifyCode)
                .append("。请在10分钟内，输入此验证码进行验证。如果您没有请求该系统邮箱验证码，请忽略此邮件，谢谢！[CHIIIPLOW]");
        return builder.toString();
    }
}
