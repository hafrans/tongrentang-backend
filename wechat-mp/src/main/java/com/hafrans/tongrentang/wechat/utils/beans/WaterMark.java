package com.hafrans.tongrentang.wechat.utils.beans;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WaterMark {
	
	@JsonProperty("appid")
	private String appId;
	
	@JsonProperty("timestamp")
	private int timestamp;
	
	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean expired(Duration duration) {
		long currentTime = System.currentTimeMillis() / 1000;
		long previousTime = currentTime - duration.getSeconds();
		return previousTime <= timestamp;
	}

	@Override
	public String toString() {
		return "WaterMark [appId=" + appId + ", timestamp=" + timestamp + "]";
	}
	
	
}
