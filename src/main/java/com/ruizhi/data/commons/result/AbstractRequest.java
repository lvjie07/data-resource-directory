package com.ruizhi.data.commons.result;

import java.io.Serializable;

/**
 * Created by lvjie on 2020/7/27
 */
public abstract class AbstractRequest implements Serializable {

    private static final long serialVersionUID = 3867192377285886721L;

    /**
     * request验证检查抽象方法
     */
    public abstract void requestCheck();
}
