package com.idohoo.exception;

/**
 * 业务异常，不会造成事务回滚的
 * @author henser
 *
 */
public class RemoteServiceException extends Exception {

	private static final long serialVersionUID = -1912705045325386160L;
	/**
	 * 错误码
	 */
	private int code;

    public RemoteServiceException(String message) {
		super(message);
		this.code = -1;
	}
	
	public RemoteServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
	
	public RemoteServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public RemoteServiceException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

	public RemoteServiceException(Throwable throwable) {
		super(throwable);
	}

    public int getCode()
    {
        return code;
    }

}
