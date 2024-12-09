package com.chiiiplow.clouddrive.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 文件
 *
 * @author yangzhixiong
 * @date 2024/12/06
 */
@Data
@Accessors(chain = true)
@TableName("file")
public class File {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long userId;

    private Long parentId;

    private String uploadId;

    private Integer isFolder;

    private String md5;

    private String fileName;

    private Long fileSize;

    private Integer fileType;

    private String filePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recyclingTime;

    private Long chunkSize;

    private Integer chunkNum;

    private Integer deleteStatus;
}
