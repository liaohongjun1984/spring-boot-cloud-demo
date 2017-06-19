package com.idohoo.user.protocol;


import com.idohoo.x.XMsg;

import java.util.Date;

/**
 * 微服务响应对象
 * Created by tanj on 14Apr2017.
 */
public class ApiResponseT<T extends Responsable> {
    private int code;
    private String msg;
    private long timestamp;
    private T data;

    private ApiResponseT(XMsg x) {
        this.code = x.getCode();
        this.msg = x.getMessage();
    }

    private ApiResponseT(XMsg xMsg, T data) {
        this.code = xMsg.getCode();
        this.msg = xMsg.getMessage();
        this.data = data;
    }

    public static ApiResponseT getDefaultResponse() {
        return new ApiResponseT(XMsg.SUCCESS);
    }

    public static <T extends Responsable> ApiResponseT getDefaultResponse(T data) {
        return new ApiResponseT(XMsg.SUCCESS).setData(data);
    }

    public static ApiResponseT getResponse(XMsg xMsg) {
        return new ApiResponseT(xMsg);
    }

    public static <T extends Responsable> ApiResponseT getResponse(XMsg xMsg, T data) {
        return new ApiResponseT(xMsg).setData(data);
    }

    public int getCode() {
        return this.code;
    }

    public ApiResponseT setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResponseT setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getTimestamp() {
        if (this.timestamp == 0L) {
            this.timestamp = (new Date()).getTime();
        }

        return this.timestamp;
    }

    public ApiResponseT setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ApiResponseT setData(T data) {
        this.data = data;
        return this;
    }
}
