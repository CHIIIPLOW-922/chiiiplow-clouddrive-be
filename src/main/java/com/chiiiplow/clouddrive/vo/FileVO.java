package com.chiiiplow.clouddrive.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chiiiplow.clouddrive.entity.File;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import query.PageQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件 vo
 *
 * @author yangzhixiong
 * @date 2025/02/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileVO extends PageQuery {


//    private String mimeTypeName;

    private String fileTypeName;

    private String parentId;

    @NotBlank(message = "搜索关键字不能为空")
    private String search;
}
