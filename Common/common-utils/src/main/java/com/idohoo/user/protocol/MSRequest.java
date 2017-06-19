package com.idohoo.user.protocol;

import java.util.Date;

/**
 * 微服务请求对象
 * Created by tanj on 14Apr2017.
 */
public class MSRequest<T extends MicroServiceProperty> {
    private int platform;// 客户端平台标识
    private long timestamp;// 时间戳
    private T data;// 数据对象


    public MSRequest() {
    }

    public MSRequest(T data) {
        this.data = data;
    }

    public int getPlatform() {
        return platform;
    }

    public MSRequest<T> setPlatform(int platform) {
        this.platform = platform;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public MSRequest<T> setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public T getData() {
        return data;
    }

    public MSRequest<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T extends MicroServiceProperty> MSRequest createRequest(T data) {
        MSRequest<T> request = new MSRequest<T>(data);
        request.setTimestamp(new Date().getTime());
        return request;
    }
}
