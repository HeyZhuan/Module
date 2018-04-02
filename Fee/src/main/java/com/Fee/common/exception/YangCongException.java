package com.Fee.common.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 洋葱安全码异常类
 * @author 
 * @date 
 */
public class YangCongException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public YangCongException() {
		super();
	}

	public YangCongException(String message, Throwable cause) {
		super(message, cause);
	}

	public YangCongException(String message) {
		super(message);
	}

	public YangCongException(Throwable cause) {
		super(cause);
	}
}