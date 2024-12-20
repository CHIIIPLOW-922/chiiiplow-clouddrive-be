package com.chiiiplow.clouddrive.exception;

/**
 * 自定义异常
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, Throwable e){
        super(message, e);
        this.message = message;
    }

    public CustomException(Throwable e) {
        super(e);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
