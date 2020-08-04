package com.ruizhi.data.constant;

/**
 * 作业状态
 * Created by lvjie on 2020/7/31
 */
public enum TaskStatusCode {

    COMPLETE("1","完成"),
    UN_COMPLETE("2","未完成");

    private String code;

    private String message;

    TaskStatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (TaskStatusCode s : TaskStatusCode.values()) {
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
