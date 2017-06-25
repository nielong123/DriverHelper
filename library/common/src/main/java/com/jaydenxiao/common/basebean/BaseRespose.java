package com.jaydenxiao.common.basebean;

import com.jaydenxiao.common.commonutils.LogUtils;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by xsf
 * on 2016.09.9:47
 */
public class BaseRespose<T> implements Serializable {

    public boolean isSuccess;
    public String msg;
    public T data;
    public String code;

    public boolean success() {
        LogUtils.logd("success = " + isSuccess);
        return isSuccess;
    }

    @Override
    public String toString() {
        return "MyBaseRespose{" +
                "isSuccess=" + isSuccess +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                '}';
    }
}
