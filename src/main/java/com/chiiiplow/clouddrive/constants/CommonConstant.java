package com.chiiiplow.clouddrive.constants;

/**
 * 公共常数
 *
 * @author yangzhixiong
 * @date 2024/12/09
 */
public interface CommonConstant {

    long ONE_SECOND = 1000L;

    long ONE_MINUTE = 60 * ONE_SECOND;

    long ONE_HOUR = 60 * ONE_MINUTE;

    long ONE_DAY = 24 * ONE_HOUR;

    long ONE_GB = 1L * 1024 * 1024 * 1024;

    String USERNAME_PATTERN = "^(?:[a-zA-Z]+|\\\\d+|[a-zA-Z\\\\d]+)$";

    String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,25}$";
}
