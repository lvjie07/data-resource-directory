package com.ruizhi.data.commons.exception;


import com.ruizhi.data.commons.result.CommonResponse;
import com.ruizhi.data.constant.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author lvjie
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BizException.class)
    public CommonResponse handleException(BizException e) {
        // 打印异常信息
        log.error("### 异常信息:{} ###", e.getMessage());
        CommonResponse response = new CommonResponse();
        response.setCode(e.errorCode);
        response.setMsg(e.getMessage());
        return response;
    }

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public CommonResponse handleException(Exception e) {

        StringBuffer errorMsg = new StringBuffer();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    errorMsg.append(fieldError.getDefaultMessage()).append(",");
                    log.error("### 请求参数错误：{" + fieldError.getObjectName() + "},field{" + fieldError.getField() + "},errorMessage{" + fieldError.getDefaultMessage() + "}");
                });
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                log.error("### 请求参数错误: {}", bindException.getAllErrors());
            }
        }
        CommonResponse response = new CommonResponse();
        response.setCode(ResultCode.PARAM_IS_INVALID.getCode());
        response.setMsg(StringUtils.isNotEmpty(errorMsg.toString()) ? errorMsg.toString() : ResultCode.PARAM_IS_INVALID.getMessage());
        return response;
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse handleOtherException(Exception e) {
        //打印异常堆栈信息
        e.printStackTrace();
        // 打印异常信息
        log.error("### 不可知的异常:{} ###", e.getMessage());
        CommonResponse response = new CommonResponse();
        response.setCode(ResultCode.SYSTEM_ERROR.getCode());
        response.setMsg(ResultCode.SYSTEM_ERROR.getMessage());
        return response;
    }

}
