package com.ruizhi.data.commons.result;

import com.ruizhi.data.constant.ResultCode;

/**
 * Created by lvjie on 2020/7/27
 */
public class ResponseUtils<T> {

    private static final int SUCCESS_CODE = 200;

    private static final int ERROR_CODE = 500;

    private ResponseData responseData;

    public ResponseUtils() {
        responseData = new ResponseData<>();
        responseData.setSuccess(true);
        responseData.setMessage("success");
        responseData.setCode(SUCCESS_CODE);
    }

    /**
     * 返回成功数据
     * @param t
     * @return
     */
    public ResponseData<T> setData(T t) {
        this.responseData.setResult(t);
        this.responseData.setSuccess(true);
        responseData.setCode(SUCCESS_CODE);
        return this.responseData;
    }

    /**
     * 返回成功数据
     * @param t
     * @param msg
     * @return
     */
    public ResponseData<T> setData(T t, String msg) {
        this.responseData.setResult(t);
        this.responseData.setSuccess(true);
        this.responseData.setMessage(msg);
        responseData.setCode(SUCCESS_CODE);
        return this.responseData;
    }

    /**
     * 返回失败数据
     * @param msg
     * @return
     */
    public ResponseData<T> setErrorMsg(String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode(ERROR_CODE);
        return this.responseData;
    }

    /**
     * 返回失败数据
     * @param code
     * @param msg
     * @return
     */
    public ResponseData<T> setErrorMsg(Integer code, String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode(ERROR_CODE);
        return this.responseData;
    }

    /**
     * 成功的response
     *
     * @param response
     * @return
     */
    public static void resultSuccessResponse(AbstractResponse response) {
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMsg(ResultCode.SUCCESS.getMessage());
    }


}
