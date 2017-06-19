package com.idohoo.exception;


import com.idohoo.msg.ExceptionMsg;

/**
 * 自定义异常。所有的Service方法都需声明抛出该异常，在ctrl捕捉并处理成自定义结果
 *
 * @author tanj
 */
public class GJJFException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ExceptionMsg exceptionMsg;
    private String message;

    /**
     * 默认构造方法
     *
     * @param exceptionMsg
     */
    public GJJFException(ExceptionMsg exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    /**
     * 自定义消息内容的构造方法
     *
     * @param exceptionMsg
     * @param msg
     */
    public GJJFException(ExceptionMsg exceptionMsg, String msg) {
        this.exceptionMsg = exceptionMsg;
        this.message = msg;
    }

    public int getCode() {
        return exceptionMsg.getCode();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public ExceptionMsg getExceptionMsg() {
        return this.exceptionMsg;
    }
}
