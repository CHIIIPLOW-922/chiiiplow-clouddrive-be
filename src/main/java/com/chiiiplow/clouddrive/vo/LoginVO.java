package com.chiiiplow.clouddrive.vo;

import com.chiiiplow.clouddrive.validation.Group;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 登录 VO
 * 登录 VO
 *
 * @author yangzhixiong
 * @date 2024/12/31
 */
@Data
@Accessors(chain = true)
public class LoginVO {

    @NotBlank(message = "登录账号不能为空", groups = {Group.G1.class})
    private String username;

    @NotBlank(message = "登录密码不能为空", groups = {Group.G2.class})
    private String password;

    private String captchaCode;

    private String captchaKey;

    @NotNull(message = "是否显示验证码不能为空", groups = {Group.G3.class})
    private Boolean showCaptcha;
}
