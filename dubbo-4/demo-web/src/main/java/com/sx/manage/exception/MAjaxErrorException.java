package com.sx.manage.exception;
/**
 * ajax请求异常
 * @author lenovo
 *
 */
public class MAjaxErrorException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	public MAjaxErrorException() {
		super();
	}

	public MAjaxErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MAjaxErrorException(String message) {
		super(message);
	}

	public MAjaxErrorException(Throwable cause) {
		super(cause);
	}
	
}
