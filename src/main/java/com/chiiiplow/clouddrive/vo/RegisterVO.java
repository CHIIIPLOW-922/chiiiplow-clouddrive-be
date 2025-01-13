package com.chiiiplow.clouddrive.vo;

import com.chiiiplow.clouddrive.constants.CommonConstant;
import com.chiiiplow.clouddrive.validation.Group;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;

/**
 * 用户 VO
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
@Data
@Accessors(chain = true)
public class RegisterVO {

    @NotBlank(message = "注册账号不能为空", groups = Group.G1.class)
//    @Pattern(regexp = CommonConstant.USERNAME_PATTERN, message = "注册账号只能使用数字和字母，且长度为6到25个字符")
    @Size(min = 6, max = 25, message = "账号长度必须在6~25位", groups = Group.G1.class)
    private String username;

    @NotBlank(message = "注册密码不能为空", groups = Group.G2.class)
    @Size(min = 8, max = 25, message = "注册密码长度必须在8~25位", groups = Group.G2.class)
//    @Pattern(regexp = CommonConstant.PASSWORD_PATTERN, message = "注册密码必须数字和字母组合")
    private String password;

    @NotBlank(message = "二次确认密码不能为空", groups = Group.G3.class)
    @Size(min = 8, max = 25, message = "注册密码长度必须在8~25位", groups = Group.G3.class)
//    @Pattern(regexp = CommonConstant.PASSWORD_PATTERN, message = "注册密码必须数字和字母组合")
    private String repassword;


    @NotBlank(message = "注册邮箱不能为空", groups = Group.G4.class)
    @Email(regexp = CommonConstant.EMAIL_PATTERN, message = "邮箱格式不正确", groups = Group.G4.class)
    private String email;

    @NotBlank(message = "邮箱验证码不能为空", groups = Group.G5.class)
    private String emailValidCode;


}
