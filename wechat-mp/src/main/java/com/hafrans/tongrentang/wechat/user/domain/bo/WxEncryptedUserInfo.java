package com.hafrans.tongrentang.wechat.user.domain.bo;

import com.hafrans.tongrentang.wechat.utils.beans.WaterMark;

public class WxEncryptedUserInfo {
	
	/*
	 *   Optional.of({
	 *                 "openId":"od1Dk5MDGmnQ_ampWKHBeIkL0RHs",
	 *                 "nickName":"Hafrans",
	 *                 "gender":1,
	 *                 "language":"zh_CN",
	 *                 "city":"Haidian",
	 *                 "province":"Beijing",
	 *                 "country":"China",
	 *                 "avatarUrl":"https://wx.qlogo.cn/",
	 *                 "watermark":{
	 *                 				"timestamp":1585157182,
	 *                 				"appid":"wx884bfb1e18e5912f"
	 *                 			   }
	 *               }) 
	 * 
	 */
	
	
	private String openId;
	
	private String nickName;
	
	private int gender;
	
	private String language;
	
	private String city;
	
	private String province;
	
	private String  country;
	
	private String avatarUrl;
	
	private WaterMark watermark;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUtl) {
		this.avatarUrl = avatarUtl;
	}

	public WaterMark getWatermark() {
		return watermark;
	}

	public void setWatermark(WaterMark watermark) {
		this.watermark = watermark;
	}

	@Override
	public String toString() {
		return "WxEncryptedUserInfo [openId=" + openId + ", nickName=" + nickName + ", gender=" + gender + ", language="
				+ language + ", city=" + city + ", province=" + province + ", country=" + country + ", avatarUtl="
				+ avatarUrl + ", watermark=" + watermark + "]";
	}
	
}
