package com.chiiiplow.clouddrive.util;

import com.chiiiplow.clouddrive.enums.HttpCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回类
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Data
public class R<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public static final int SUCCESS = 200;
    public static final String SUCCESS_MSG = "请求成功！";

    public static final int FAIL = 400;
    public static final String FAIL_MSG = "请求失败！";


    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> ok(int code, T data, String msg) {
        return restResult(data, code, msg);
    }

    public static <T> R<T> http(HttpCode httpCodeEnum, T data) {
        return restResult(data, httpCodeEnum.getCode(), httpCodeEnum.getMessage());
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, FAIL_MSG);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, FAIL_MSG);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> fail(int code, T data, String msg) {
        return restResult(data, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }


}
