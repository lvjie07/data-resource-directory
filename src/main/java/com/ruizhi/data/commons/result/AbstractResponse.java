package com.ruizhi.data.commons.result;

import java.io.Serializable;

/**
 * Created by lvjie on 2020/7/27
 */
public abstract class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 6465401533528889790L;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
