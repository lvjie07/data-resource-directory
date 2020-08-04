package com.ruizhi.data.constant;

/**
 * Created by lvjie on 2020/8/3
 */
public enum ClassifyStatusCode {

    COMPLETE("1","已分类"),
    UN_COMPLETE("2","未分类");

    private String code;

    private String message;

    ClassifyStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (ClassifyStatusCode s : ClassifyStatusCode.values()) {
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
