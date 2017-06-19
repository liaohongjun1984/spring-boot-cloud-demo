package com.idohoo.msg;

/**
 * Created by tanj on 15Apr2017.
 */
public enum ExceptionMsg {

    /**
     * 1**: hold on
     * @see org.springframework.http.HttpStatus.CONTINUE
     */


    /**
     * 2**: Here you go
     *
     * @see org.springframework.http.HttpStatus.OK
     */
    SUCCESS(0, "Success"), // 成功
    // OK(200, "OK"), // 成功

    /**
     * 3**: Go away
     *
     * @see org.springframework.http.HttpStatus.MULTIPLE_CHOICES
     */
    OPERATION_FORBIDDEN(-3000, "Operation Forbidden"), // 操作被禁止
    AUTH_FAILED(-3001, "Authorization Error"), // 验证不通过
    AUTH_REQUIRED(-3002, "Authorization Required"), // 必须验证
    INVALID_TOKEN(-3003, "Token Invalid"),
    INVALID_SESSION_ID(-3004, "Session ID Invalid"),

    /**
     * 4**: you fucked up
     *
     * @see org.springframework.http.HttpStatus.NOT_FOUND
     */
    PAGE_NOT_FOUND(-4000, "Page NOT Found"), // 找不到页面
    PARAM_ERROR(-4001, "Param error."), // 参数错误
    INVALID_VALUE(-4002, "Value Invalid"), // 参数值非法
    INCORRECT_VERIFY_CODE(-4003, "Incorrect Verify Code"), // 验证码错误
    INCORRECT_PASSWORD(-4004, "Incorrect password"), // 密码错误
    DATA_NOT_EXIST(-4005, "Data NOT exist"), // 数据不存在
    FILE_TYPE_NOT_ACCEPTED(-4006, "Unacceptable File Type"), // 不被接受的文件类型
    FILE_TOO_SMALL(-4007, "File Too Small"), // 所上传文件太小
    FILE_TOO_LARGE(-4008, "File Too Large"), // 所上传文件太大

    /**
     * 5**: I fucked up
     *
     * @see org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
     */
    INTERNAL_ERROR(-5000, "Internal error"), // 服务端内部错误
    INCORRECT_CONFIG(-5001, "Incorrect config"),
    NETWORK_ERROR(-5002, "Network error"), // 网络错误
    REQUEST_TIMEOUT(-5003, "Request Timeout"),// 请求超时
    SOCKET_TIMEOUT(-5004, "Socket Timeout"), // 服务端超时
    FILE_UPLOAD_ERROR(-5005, "File upload Error"), // 文件上传失败
    DATABASE_ERROR(-5006, "Database process error"), // 数据库操作错误
    DUPLICATE_DATA(-5007, "Duplicate data"), // 数据重复
    INVALID_DATA_FORMAT(-5008, "Data format error"), // 数据格式错误

    /**
     * 6**: custom errors
     */
    THIRD_PARTY_PROCESS_ERROR(-6001, "3rd Party Process Error"), // 第三方系统处理失败
    SMS_PROCESS_ERROR(-6002, "SMS send error"), // 短信处理失败
    EMAIL_PROCESS_ERROR(-6003, "Email post error"), // 邮件处理失败
    ;

    private int code;// 结果码
    private String message;// 结果描述

    // constructor
    ExceptionMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public ExceptionMsg setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * @param code
     * @return
     */
    public static ExceptionMsg valueOf(int code) {
        ExceptionMsg[] colors = ExceptionMsg.values();
        for (ExceptionMsg c : colors) {
            if (c.getCode() == code)
                return c;
        }
        return INTERNAL_ERROR;
    }
}
