package com.hafrans.tongrentang.wechat.common.status.exception;

import com.hafrans.tongrentang.wechat.common.status.PlainStatus;

public class StatusException extends Exception {

	private static final long serialVersionUID = -9183985448533472339L;

	private int status = PlainStatus.STATUS_FAILED;
	
	public StatusException() {
		super();
	}
	
	public StatusException(String message) {
		super(message);
	}
	
	public StatusException(int status, String message) {
		super(message);
		this.status = status;
	}
	
	public StatusException(int status, String message, Throwable next) {
		super(message, next);
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
	
}
