package com.hafrans.tongrentang.wechat.user.domain.bo;

import com.hafrans.tongrentang.wechat.utils.beans.WaterMark;

public class WxEncryptedPhoneNumber {
	
	
	// 用户绑定的手机号
	private String phoneNumber;
	
	// 没有区号的手机号
	private String purePhoneNumber;
	
	// 区号
	private String countryCode;
	
	// 水印
	private WaterMark watermark;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPurePhoneNumber() {
		return purePhoneNumber;
	}

	public void setPurePhoneNumber(String purePhoneNumber) {
		this.purePhoneNumber = purePhoneNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public WaterMark getWatermark() {
		return watermark;
	}

	public void setWatermark(WaterMark watermark) {
		this.watermark = watermark;
	}

	@Override
	public String toString() {
		return "WxEncryptedPhoneNumber [phoneNumber=" + phoneNumber + ", purePhoneNumber=" + purePhoneNumber
				+ ", countryCode=" + countryCode + ", watermark=" + watermark + "]";
	}
	
}
