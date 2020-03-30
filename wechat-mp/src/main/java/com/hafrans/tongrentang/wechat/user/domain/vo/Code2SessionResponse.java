package com.hafrans.tongrentang.wechat.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code2SessionResponse {
	
	// 系统繁忙，此时请开发者稍候再试
	public final static int ERRCODE_BUSY = -1;
	
	// 请求成功
	public final static int ERRCODE_OK = 0;
	
	// code 无效
	public final static int ERRCODE_INVALID_CODE = 40029;
	
	// 频率限制，每个用户每分钟100次
	public final static int ERRCODE_BEYOND_THRESHOLD = 45011;
	
	@JsonProperty("openid")
	private String openId;
	
	@JsonProperty("session_key")
	private String sessionKey;
	
	@JsonProperty("unionid")
	private String unionId;
	
	@JsonProperty("errcode")
	private int errcode;
	
	@JsonProperty("errmsg")
	private String errmsg;

	
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Override
	public String toString() {
		return "Code2SessionResponse [openId=" + openId + ", sessionKey=" + sessionKey + ", unionId=" + unionId
				+ ", errcode=" + errcode + ", errmsg=" + errmsg + "]";
	}
	
	
}
