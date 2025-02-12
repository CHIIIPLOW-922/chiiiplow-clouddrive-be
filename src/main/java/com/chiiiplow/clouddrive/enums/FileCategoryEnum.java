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
    FOLDER(0, "Folder"),
    /**
     * 文档
     */
    DOCUMENT(1, "Document"),
    /**
     * 图像
     */
    IMAGE(2, "Image"),
    /**
     * 音频
     */
    AUDIO(3, "Audio"),
    /**
     * 视频
     */
    VIDEO(4, "Video"),
    /**
     * 压缩文件
     */
    ARCHIVE(5, "Archive"),
    /**
     * 其他
     */
    OTHER(6, "Other");

    private final int fileType;
    private final String category;

    FileCategoryEnum(int fileType, String category) {
        this.fileType = fileType;
        this.category = category;
    }

    public int getFileType() {
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
            if (fileCategoryEnum.getCategory().equalsIgnoreCase(category)) {
                return fileCategoryEnum;
            }
        }
        return null;
    }
}
