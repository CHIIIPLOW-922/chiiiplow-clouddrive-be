package com.chiiiplow.clouddrive.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 文件 vo
 *
 * @author yangzhixiong
 * @date 2025/02/07
 */
@Data
public class FileVO {


    private String mimeTypeName;

    private String fileTypeName;
}
