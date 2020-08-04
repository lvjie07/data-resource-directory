package com.ruizhi.data.constant;

/**
 * Created by lvjie on 2020/7/27
 */
public enum ResultCode {

    SUCCESS                                 ("000000","成功"),
    REQUISITE_PARAMETER_NOT_EXIST        ("001001","必需的参数不能为空"),
    SYSTEM_ERROR                           ("002001", "系统异常"),
    SYSTEM_TIMEOUT                         ("002002", "系统超时"),
    DB_EXCEPTION                           ("003001", "数据库异常"),
    DB_SAVE_EXCEPTION                     ("003002","数据保存异常"),
    DB_UPDATE_EXCEPTION                   ("003003","数据更新异常"),
    DB_DELETE_EXCEPTION                   ("003004","数据删除异常"),
    DB_DATA_NOTEXIST                       ("003005","数据不存在"),
    USER_CAN_NOT_LOGIN                     ("004001","用户被禁止登录"),
    USER_USERNAME_ERROR                    ("004002","登录名或密码错误"),
    USER_NOT_LOGIN                          ("004003","用户没登录"),
    PERMISSION_TOKEN_EXPIRED               ("004004","token过期"),
    PERMISSION_TOKEN_INVALID               ("004005","token无效"),
    PERMISSION_SIGNATURE_ERROR             ("004006","签名失败"),
    PARAM_IS_INVALID                         ("004007","参数错误");


    private String code;

    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(String code) {
        for (ResultCode s : ResultCode.values()) {
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
