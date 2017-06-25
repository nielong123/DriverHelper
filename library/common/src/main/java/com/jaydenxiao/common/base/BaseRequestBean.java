package com.jaydenxiao.common.base;

import java.io.Serializable;

/**
 * Created by nl on 2017/4/10.
 */

public class BaseRequestBean implements Serializable {

    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

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

    private Object data;
    private String code;
    private String msg;

}
