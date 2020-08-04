package com.ruizhi.data.commons.exception;

/**
 * Created by lvjie on 2020/7/27
 */
public class BaseBusinessException extends RuntimeException{

    protected String errorCode;

    protected String message;


    public BaseBusinessException() {
        super();
    }

    public BaseBusinessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }


    public BaseBusinessException(String message, String errorCode) {
        super();
        this.message = message;
        this.errorCode = errorCode;

    }

    public BaseBusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BaseBusinessException(Throwable cause, String errorCode, String message) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
