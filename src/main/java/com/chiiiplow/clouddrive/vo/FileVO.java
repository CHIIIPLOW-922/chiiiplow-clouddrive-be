package com.chiiiplow.clouddrive.vo;

import com.chiiiplow.clouddrive.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 文件 vo
 *
 * @author yangzhixiong
 * @date 2025/02/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileVO extends PageQuery {


    private String id;

    private String fileTypeName;

    private String userId;

    private String parentId;

    private Integer isFolder;

    @NotBlank(message = "搜索关键字不能为空")
    private String search;
}
