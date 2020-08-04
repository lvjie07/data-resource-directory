package com.ruizhi.data.constant;

/**
 * 确认情况
 * Created by lvjie on 2020/8/4
 */
public enum  ConfirmStatusCode {

    UN_CONFIRM("0","未确认"),
    CONFIRM("1","已确认");

    private String code;

    private String message;

    ConfirmStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (ConfirmStatusCode s : ConfirmStatusCode.values()) {
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
