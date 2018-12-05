package com.git.cloud.common.exception;

public class RollbackableBizException extends BizException{

	/**
	 * 异常版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 一般的异常构造，由于不包含异常代码，不建议使用
	 */
	public RollbackableBizException() {
		super();
	}

	public RollbackableBizException(String message) {
		super(message);
	}
	/**
	 * @param message
	 * @param cause
	 */
	public RollbackableBizException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @param message
	 * @param cause
	 */
	public RollbackableBizException(SysMessage msg, String message,Throwable cause) {
		super(msg,message, cause);
	}

	/**
	 * @param msg
	 */
	public RollbackableBizException(SysMessage msg) {
		super(msg);
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public RollbackableBizException(SysMessage msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param cause
	 */
	public RollbackableBizException(Throwable cause) {
		super(cause);
	}
}
