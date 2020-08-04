package com.ruizhi.data.utils;

/**
 * Created by lvjie on 2020/7/28
 */
@FunctionalInterface
public interface ColaBeanUtilsCallBack<S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
