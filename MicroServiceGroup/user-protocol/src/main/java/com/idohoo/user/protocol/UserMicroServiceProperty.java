package com.idohoo.user.protocol;

/**
 * Created by tanj on 14Apr2017.
 */

import java.io.Serializable;

/**
 * 微服务实例信息
 */
public interface UserMicroServiceProperty extends MicroServiceProperty, Serializable {
    // 微服务实力名称
    String SERVICE_NAME = "user-ms";
    // 微服务实例版本
    String VERSION = "0.0.1";
}
