package com.idohoo.user.protocol;


import com.idohoo.x.XMsg;

import java.util.Date;

/**
 * 微服务响应对象
 * Created by tanj on 14Apr2017.
 */
public class MSResponse<T extends MicroServiceProperty> {
    private int code;
    private String msg;
    private long timestamp;
    private T data;

    public MSResponse(int code, String msg, long timestamp, T data) {
        this.code = code;
        this.msg = msg;
        this.timestamp = timestamp;
        this.data = data;
    }

    public MSResponse() {
    }

    public MSResponse(XMsg x) {
        this.code = x.getCode();
        this.msg = x.getMessage();
    }

    public MSResponse(XMsg xMsg, T data) {
        this.code = xMsg.getCode();
        this.msg = xMsg.getMessage();
        this.data = data;
    }

    public static MSResponse getDefaultResponse() {
        return new MSResponse(XMsg.SUCCESS);
    }

    public static <T extends MicroServiceProperty> MSResponse getDefaultResponse(T data) {
        return new MSResponse(XMsg.SUCCESS).setData(data);
    }

    public static MSResponse getResponse(XMsg xMsg) {
        return new MSResponse(xMsg);
    }

    public static <T extends MicroServiceProperty> MSResponse getResponse(XMsg xMsg, T data) {
        return new MSResponse(xMsg).setData(data);
    }

    public int getCode() {
        return this.code;
    }

    public MSResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MSResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getTimestamp() {
        if (this.timestamp == 0L) {
            this.timestamp = (new Date()).getTime();
        }

        return this.timestamp;
    }

    public MSResponse setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public MSResponse setData(T data) {
        this.data = data;
        return this;
    }
}
