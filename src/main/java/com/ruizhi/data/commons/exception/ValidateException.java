package com.ruizhi.data.commons.exception;

/**
 * Created by lvjie on 2020/7/27
 */
public class ValidateException extends BaseBusinessException {

    public ValidateException() {
    }

    public ValidateException(String errorCode) {
        super(errorCode);
    }

    public ValidateException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ValidateException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ValidateException(Throwable cause, String errorCode, String message) {
        super(cause, errorCode, message);
    }
}
