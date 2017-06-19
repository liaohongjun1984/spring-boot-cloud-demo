package com.idohoo.user.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idohoo.x.XMsg;

import java.util.Date;
import java.util.HashMap;

/**
 * 微服务响应对象
 * Created by tanj on 14Apr2017.
 */
public class ApiResponse {
    private int code;
    private String msg;
    private long timestamp;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(XMsg x) {
        this.code = x.getCode();
        this.msg = x.getMessage();
    }

    public ApiResponse(XMsg xMsg, Object data) {
        this.code = xMsg.getCode();
        this.msg = xMsg.getMessage();
        if (data == null)
            data = new HashMap();
        this.data = data;
    }

    public static ApiResponse getDefaultResponse() {
        return new ApiResponse(XMsg.SUCCESS);
    }

    public static ApiResponse getDefaultResponse(Object data) {
        return new ApiResponse(XMsg.SUCCESS).setData(data);
    }

    public static ApiResponse getResponse(XMsg xMsg) {
        return new ApiResponse(xMsg);
    }

    public static ApiResponse getResponse(XMsg xMsg, Object data) {
        return new ApiResponse(xMsg).setData(data);
    }

    public int getCode() {
        return this.code;
    }

    public ApiResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getTimestamp() {
        if (this.timestamp == 0L) {
            this.timestamp = (new Date()).getTime();
        }

        return this.timestamp;
    }

    public ApiResponse setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ApiResponse setData(Object data) {
        if (data == null)
            data = new HashMap();
        this.data = data;
        return this;
    }
}
