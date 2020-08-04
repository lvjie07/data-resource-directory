package com.ruizhi.data.constant;

/**
 * 数据库类型
 * Created by lvjie on 2020/7/31
 */
public enum DataBaseTypeCode {

    COMPLETE("1","oracle"),
    UN_COMPLETE("2","mysql");

    private String code;

    private String message;

    DataBaseTypeCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (DataBaseTypeCode s : DataBaseTypeCode.values()) {
            if (null == code)
                break;
            if (s.code.equals(code)) {
                return s.message;
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
