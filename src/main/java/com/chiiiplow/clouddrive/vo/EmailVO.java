package com.chiiiplow.clouddrive.vo;

import com.chiiiplow.clouddrive.constants.CommonConstant;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 电子邮件 VO
 *
 * @author yangzhixiong
 * @date 2024/12/24
 */
@Data
public class EmailVO {

    @Email(regexp = CommonConstant.EMAIL_PATTERN, message = "邮箱格式不正确！")
    @NotBlank(message = "邮箱不能为空！")
    private String email;
}
