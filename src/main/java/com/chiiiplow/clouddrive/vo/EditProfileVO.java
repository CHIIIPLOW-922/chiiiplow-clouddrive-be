package com.chiiiplow.clouddrive.vo;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 编辑个人资料 VO
 *
 * @author yangzhixiong
 * @date 2025/01/17
 */
@Data
@Accessors(chain = true)
public class EditProfileVO {

    private String nickname;

    private String email;

    private String avatarPath;

}
