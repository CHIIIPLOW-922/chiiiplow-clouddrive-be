package com.chiiiplow.clouddrive.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CAPTCHA DTO
 *
 * @author yangzhixiong
 * @date 2024/12/25
 */
@Accessors(chain = true)
@Data
public class CaptchaDTO {

    private String captchaKey;


    private String captchaImage;
}
