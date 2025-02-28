package com.chiiiplow.clouddrive.enums;

/**
 * 文件类型枚举
 *
 * @author yangzhixiong
 * @date 2025/02/07
 */

public enum FileTypeEnum {

    // Document types
    PDF(3, "PDF File", "application/pdf", "Document"),
    DOC(3, "Word File", "application/msword", "Document"),
    DOCX(3, "Word Document", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "Document"),
    XLS(3, "Excel File", "application/vnd.ms-excel", "Document"),
    XLSX(3, "Excel Document", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Document"),
    PPT(3, "PowerPoint File", "application/vnd.ms-powerpoint", "Document"),
    PPTX(3, "PowerPoint Document", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "Document"),
    TXT(3, "Text File", "text/plain", "Document"),
    HTML(3, "HTML File", "text/html", "Document"),
    XML(3, "XML File", "application/xml", "Document"),
    JSON(3, "JSON File", "application/json", "Document"),
    CSV(3, "CSV File", "text/csv", "Document"),
    // Archive types
    ZIP(3, "ZIP Archive", "application/zip", "Archive"),
    RAR(3, "RAR Archive", "application/vnd.rar", "Archive"),
    SEVEN_ZIP(3, "7-Zip Archive", "application/x-7z-compressed", "Archive"),

    // Image types
    GIF(2, "GIF Image", "image/gif", "Image"),
    JPEG(2, "JPEG Image", "image/jpeg", "Image"),
    PNG(2, "PNG Image", "image/png", "Image"),
    BMP(2, "BMP Image", "image/bmp", "Image"),
    TIFF(2, "TIFF Image", "image/tiff", "Image"),


    // Video types
    MP4(4, "MP4 Video", "video/mp4", "Video"),
    AVI(4, "AVI Video", "video/x-msvideo", "Video"),
    MOV(4, "MOV Video", "video/quicktime", "Video"),

    // Audio types
    MP3(5, "MP3 Audio", "audio/mpeg", "Audio"),
    WAV(5, "WAV Audio", "audio/wav", "Audio"),

    // Other types
    OCTET_STREAM(6, "Binary File", "application/octet-stream", "Other");


    private final int fileType;
    private final String fileTypeName;
    private final String mimeType;
    private final String category;

    FileTypeEnum(int fileType, String fileTypeName, String mimeType, String category) {
        this.fileType = fileType;
        this.fileTypeName = fileTypeName;
        this.mimeType = mimeType;
        this.category = category;
    }

    public int getFileType() {
        return fileType;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getCategory() {
        return category;
    }

    public static FileTypeEnum fromFileTypeEnum(String mimeType) {
        for (FileTypeEnum typeEnum : FileTypeEnum.values()) {
            if (typeEnum.getMimeType().equalsIgnoreCase(mimeType)) {
                return typeEnum;
            }
        }
        return null;
    }
}
