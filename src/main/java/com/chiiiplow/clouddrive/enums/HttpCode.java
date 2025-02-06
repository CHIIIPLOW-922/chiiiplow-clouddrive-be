package com.chiiiplow.clouddrive.enums;

/**
 * 状态码枚举
 *
 * @author yangzhixiong
 * @date 2024/12/10
 */
public enum HttpCode {

    // 2xx Success
    OK(200, "操作成功"),
    CREATED(201, "资源创建成功"),
    NO_CONTENT(204, "操作成功，无内容返回"),

    // 4xx Client Errors
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请登录"),
    FORBIDDEN(403, "没有操作权限"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不被允许"),
    PAYLOAD_TOO_LARGE(413, "上传文件大小超出限制"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的文件类型"),

    // 5xx Server Errors
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂时不可用，请稍后重试"),

    // Custom Codes for Cloud Drive
    USER_ALREADY_EXISTS(409, "用户已存在"),
    EMAIL_NOT_VERIFIED(403, "邮箱未验证"),
    FILE_UPLOAD_FAILED(500, "文件上传失败"),
    FILE_NOT_FOUND(404, "文件未找到"),
    STORAGE_LIMIT_EXCEEDED(403, "存储空间不足"),
    RECYCLE_BIN_CLEAN_FAILED(500, "回收站清理失败"),
    FILE_ALREADY_EXISTS(409, "文件已存在"),
    INVALID_FILE_NAME(400, "文件名非法"),
    SHARE_LINK_EXPIRED(410, "分享链接已过期"),
    DOWNLOAD_FAILED(500, "文件下载失败"),
    OPERATION_NOT_SUPPORTED(405, "操作不被支持"),
    LIMIT_REQUESTED(406, "过多请求"),
    INVALID_CAPTCHA(400, "验证码错误");

    private final int code;
    private final String message;

    HttpCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public static HttpCode fromCode(int code) {
        for (HttpCode httpCode : HttpCode.values()) {
            if (httpCode.code == code) {
                return httpCode;
            }
        }
        throw new IllegalArgumentException("Unknown HTTP Code: " + code);
    }
}

