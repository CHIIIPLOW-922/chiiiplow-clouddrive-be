package com.chiiiplow.clouddrive.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户信息 DTO
 *
 * @author yangzhixiong
 * @date 2025/01/14
 */
@Data
@Accessors(chain = true)
public class UserInfoDTO implements Serializable {

    private String username;

    private String nickname;

    private String avatarPath;

    private String email;

    private Float usedSpaceRate;

    private String usedSpace;

    private String totalSpace;
}
