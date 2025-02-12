package com.chiiiplow.clouddrive.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 文件夹 vo
 *
 * @author yangzhixiong
 * @date 2025/02/10
 */
@Data
public class FolderVO {

    private String parentId;

    @NotBlank(message = "文件夹命名不允许为空！")
    private String folderName;

}
