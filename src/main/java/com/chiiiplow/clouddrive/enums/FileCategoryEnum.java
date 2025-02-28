package com.chiiiplow.clouddrive.enums;

import com.chiiiplow.clouddrive.entity.File;

/**
 * 文件类别枚举
 *
 * @author yangzhixiong
 * @date 2025/02/10
 */
public enum FileCategoryEnum {


    /**
     * 文件夹
     */
    FOLDER("0", "Folder"),
    /**
     * 图像
     */
    IMAGE("2", "Image"),
    /**
     * 文档
     */
    DOCUMENT("3", "Document"),
    /**
     * 压缩文件
     */
    ARCHIVE("3", "Archive"),
    /**
     * 视频
     */
    VIDEO("4", "Video"),
    /**
     * 音频
     */
    AUDIO("5", "Audio"),
    /**
     * 其他
     */
    OTHER("6", "Other"),
    /**
     * 回收
     */
    RECYCLE("7", "Recycle"),;

    private final String fileType;
    private final String category;

    FileCategoryEnum(String fileType, String category) {
        this.fileType = fileType;
        this.category = category;
    }

    public String getFileType() {
        return fileType;
    }


    public String getCategory() {
        return category;
    }

    /**
     * 根据 category 获取对应的枚举值
     *
     * @param category 文件类别
     * @return 对应的枚举值，如果没有找到则返回 null
     */
    public static FileCategoryEnum fromCategory(String category) {
        for (FileCategoryEnum fileCategoryEnum : FileCategoryEnum.values()) {
            if (fileCategoryEnum.getFileType().equalsIgnoreCase(category)) {
                return fileCategoryEnum;
            }
        }
        return null;
    }
}
