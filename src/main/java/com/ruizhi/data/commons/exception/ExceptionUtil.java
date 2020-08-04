package com.ruizhi.data.commons.exception;

import com.ruizhi.data.commons.result.AbstractResponse;

/**
 * Created by lvjie on 2020/7/27
 */
public class ExceptionUtil {

    public static AbstractResponse handlerException(AbstractResponse response, Exception e) throws Exception {
        Exception exception = null;
        if (!(e instanceof Exception)) {
            return null;
        }
        if (e instanceof ValidateException) {
            response.setCode(((ValidateException) e).getErrorCode());
            response.setMsg(e.getMessage());
        } else if (e instanceof ProcessException) {
            response.setCode(((ProcessException) e).getErrorCode());
            response.setMsg(e.getMessage());
        } else if (e instanceof BizException) {
            response.setCode(((BizException) e).getErrorCode());
            response.setMsg(e.getMessage());
        } else if (e instanceof Exception) {
            // 抛出去调用方法处理
            throw e;
        }
        return response;
    }
}

