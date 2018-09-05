package com.demo.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * BaseVO 管理端代码号段:10000~19999 个人中心号段:20000~29999 公司端号段:30000~39999
 * MCIP号段，40000~49999 短信中心：50000~59999 支付中心:60000~69999
 */
@SuppressWarnings("rawtypes")
public class BaseVO<T> implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1892029806696959264L;
	/**
	 * 成功标识,默认true
	 */
	protected boolean success = true;
	/**
	 * 消息代码
	 */
	protected String msgcode;
	/**
	 * 消息内容
	 */
	protected String msg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(String msgcode) {
		this.msgcode = msgcode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * 创建消息
	 * 
	 * @param boo
	 *            成功标识
	 * @param code
	 *            消息代码
	 * @param message
	 *            消息内容
	 * @return BaseVO
	 */
	@SuppressWarnings({ "unchecked" })
	public static BaseVO createMsg(boolean boo, String msgcode, String message) {
		BaseVO vo = new BaseVO();
		vo.success = boo;
		vo.msg = message;
		vo.msgcode = msgcode;
		return vo;
	}

	/**
	 * 通过key值创建消息
	 * 
	 * @param Key
	 *            属性文件key
	 * @param boo
	 * @return BaseVO
	 */
	@SuppressWarnings("unchecked")
	public static BaseVO createMsgBykey(String key, boolean boo) {
		BaseVO vo = new BaseVO();
		vo.success = boo;
		vo.msgcode = key;
		vo.msg = key;
		return vo;
	}

	/**
	 * 通过key值创建错误的消息
	 * 
	 * @param Key
	 *            属性文件key
	 * @return BaseVO
	 */
	public static BaseVO createErrorMsgBykey(String Key) {
		return createMsgBykey(Key, false);
	}

	/**
	 * 通过key值创建成功消息
	 * 
	 * @param Key
	 *            属性文件key
	 * @return BaseVO
	 */
	public static BaseVO createSuccessMsgBykey(String Key) {
		return createMsgBykey(Key, true);
	}

	/**
	 * 创建mescode为空的成功消息
	 * 
	 * @param message
	 *            消息内容
	 * @return
	 */
	public static BaseVO createSuccessMsg(String message) {
		return createMsg(true, null, message);
	}

	/**
	 * 创建mescode为空的错误消息
	 * 
	 * @param message
	 *            消息内容
	 * @return
	 */
	public static BaseVO createErrorMsg(String message) {
		return createMsg(false, null, message);
	}

	/**
	 * 泛型的方式去创建消息
	 * 
	 * @param boo
	 * @param msgcode
	 *            消息代号
	 * @param message
	 *            消息内容
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T createMsgT(boolean boo, String msgcode, String message) {
		this.success = boo;
		this.msgcode = msgcode;
		this.msg = message;
		return (T) this;
	}

	/**
	 * 通过key值创建消息
	 * 
	 * @param key
	 * @param boo
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T createMsgBykeyT(String key, boolean boo) {
		this.success = boo;
		this.msgcode =key;
		this.msg = key;
		return ((T) this);
	}

	/**
	 * 通过key值创建错误的消息
	 * 
	 * @param key
	 * @return T
	 */
	public T createErrorMsgBykeyT(String key) {
		return createMsgBykeyT(key, false);
	}

	/**
	 * 通过key值创建成功的消息
	 * 
	 * @param key
	 * @return
	 */
	public T createSuccessMsgBykeyT(String key) {
		return createMsgBykeyT(key, true);
	}
	
	/**
	 * 创建mescode为空的错误消息
	 * 
	 * @param message
	 *            消息内容
	 * @return
	 */
	public  T createErrorMsgT(String message) {
		return createMsgT(false, null, message);
	}
	/**
	 * 创建mescode为空的成功消息
	 * 
	 * @param message
	 *            消息内容
	 * @return
	 */
	public  T createSuccessMsgT(String message) {
		return createMsgT(true, null, message);
	}

	/**
	 * 构造函数
	 * 
	 * @param success
	 * @param msgcode
	 * @param msg
	 */
	public BaseVO(boolean success, String msgcode, String msg) {
		this.success = success;
		if (StringUtils.isNotBlank(msgcode)) {
			this.msgcode = msgcode;
			this.msg = msgcode;
		}
		if (StringUtils.isNotBlank(msg)) {
			this.msg = msg;
		}

	}

	public BaseVO() {

	}
}
