package com.chiiiplow.clouddrive.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 文件 DTO
 *
 * @author yangzhixiong
 * @date 2025/02/10
 */
@Data
@Accessors(chain = true)
public class FileDTO {

    private String id;

    private String parentId;

    private String userId;

    private Integer isFolder;

    private String fileName;

    private Long fileSize;

    private Integer fileType;

    private String filePath;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

}
