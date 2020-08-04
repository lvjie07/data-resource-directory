package com.ruizhi.data.commons.exception;

import com.ruizhi.data.commons.result.AbstractResponse;
import com.ruizhi.data.constant.ResultCode;

/**
 * 异常处理工具类
 * Created by lvjie on 2020/7/27
 */
public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException(response,e);
        } catch (Exception ex) {
            response.setCode(ResultCode.SYSTEM_ERROR.getCode());
            response.setMsg(ResultCode.SYSTEM_ERROR.getMessage());
        }
    }
}
