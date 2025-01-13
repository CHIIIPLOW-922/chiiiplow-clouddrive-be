package com.chiiiplow.clouddrive.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CAPTCHA VO
 *
 * @author yangzhixiong
 * @date 2025/01/03
 */
@Data
@Accessors(chain = true)
public class CaptchaVO {

    private String captchaKey;
}
