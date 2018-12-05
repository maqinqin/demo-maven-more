package com.git.cloud.common.exception;

public class BizException extends Exception implements java.io.Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SysMessage
	 */
	protected SysMessage msg;

	/**
	 *
	 */
	public BizException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BizException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BizException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BizException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BizException(SysMessage msg) {
		super();
		this.msg = msg;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msg
	 * @param message
	 * @param cause
	 */
	public BizException(SysMessage msg, String message,Throwable cause){
		super(message,cause);
		this.msg = msg;
	}
	/**
	 * @param message
	 * @param cause
	 */
	public BizException(SysMessage msg, Throwable cause) {
		super(cause);
		this.msg = msg;
	}

	public SysMessage getSysMsg() {
	    return this.msg;
	}
}
