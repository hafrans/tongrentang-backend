package com.hafrans.tongrentang.wechat.common.security;


public enum LoginUserType {
	WECHAT_MP("WECHAT_MP"),
	WECHAT_WEB("WECHAT_WEB"),
	WEB("PLAIN_WEB"),
	OTHER("OTHER");
	
	private String type;
	
	private LoginUserType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static LoginUserType parse(String type) {
		switch (type) {
		case "WECHAT_MP":
			return LoginUserType.WECHAT_MP;
		case "WECHAT_WEB":
			return LoginUserType.WECHAT_WEB;
		case "PLAIN_WEB":
			return LoginUserType.WEB;
		default:
			return LoginUserType.OTHER;
		}
	}
}
