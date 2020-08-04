package com.ruizhi.data.commons.exception;

/**
 * Created by lvjie on 2020/7/27
 */
public class ProcessException extends BaseBusinessException {

    public ProcessException() {
    }

    public ProcessException(String errorCode) {
        super(errorCode);
    }

    public ProcessException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ProcessException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public ProcessException(Throwable cause, String errorCode, String message) {
        super(cause, errorCode, message);
    }
}
