package com.hafrans.tongrentang.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix="wechat.mp")
public class WechatMPInfoConfig {
	
	
	private String appId;
	
	private String secret;
	
	private String grantType;
	
	private String code2SessionUri;
	
	
	
	
	

	public String getCode2SessionUri() {
		return code2SessionUri;
	}

	public void setCode2SessionUri(String code2SessionUri) {
		this.code2SessionUri = code2SessionUri;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

}
