package com.hafrans.tongrentang.wechat.user.exception;

public class UserNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8379949377817817144L;
	
	public UserNotFoundException() {
		
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
