package com.ruizhi.data.commons.exception;

/**
 * Created by lvjie on 2020/7/27
 */
public class BizException extends BaseBusinessException {

    public BizException() {
    }

    public BizException(String errorCode) {
        super(errorCode);
    }

    public BizException(String message, String errorCode) {
        super(message, errorCode);
    }

    public BizException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }

    public BizException(Throwable cause, String errorCode, String message) {
        super(cause, errorCode, message);
    }
}
