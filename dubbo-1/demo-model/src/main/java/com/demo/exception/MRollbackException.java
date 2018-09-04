package com.demo.exception;
/**
 * ajax请求异常
 * @author lenovo
 *
 */
public class MRollbackException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	public MRollbackException() {
		super();
	}

	public MRollbackException(String message, Throwable cause) {
		super(message, cause);
	}

	public MRollbackException(String message) {
		super(message);
	}

	public MRollbackException(Throwable cause) {
		super(cause);
	}
	
}
