package com.hafrans.tongrentang.wechat.common.status;

public class PlainStatus {
	
	public final static int STATUS_OK             = 0;
	public final static int STATUS_FAILED         = 1;
	public final static int STATUS_PENDING        = 2;
	public final static int STTAUS_ACCESS_DENIED  = 3;
	public final static int STATUS_UNKNOWN_FAILED = 9;
	
	
	
	public final static int STATUS_LOGIN_SUCCESS               = 10000;
	public final static int STATUS_LOGIN_FAILED                = 10001;
	public final static int STATUS_LOGIN_FAILED_LOCKED         = 10002;
	public final static int STATUS_LOGIN_FAILED_INVALID_OPENID = 10003;
	public final static int STTAUS_LOGIN_NOT_REGISTERED        = 10004;
	
	public final static int STATUS_JWT_SUCCESS        = 10010;
	public final static int STATUS_JWT_EXPIRED        = 10011;
	public final static int STATUS_JWT_LOCKED         = 10012;
	public final static int STATUS_JWT_INVALID        = 10013;
	public final static int STATUS_JWT_FAILED         = 10014;
	public final static int STATUS_JWT_REFRESH_FAILED = 10015;
	
	
	
	
}
