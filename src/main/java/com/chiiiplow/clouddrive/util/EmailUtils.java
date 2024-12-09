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
}
