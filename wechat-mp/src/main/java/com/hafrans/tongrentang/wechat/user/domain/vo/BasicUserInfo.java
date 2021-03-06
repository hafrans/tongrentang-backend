package com.hafrans.tongrentang.wechat.user.domain.vo;

import com.hafrans.tongrentang.wechat.user.domain.entity.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserInfo {
	
	private String avatarUrl;
	private String nickName;
	private int gender;
	private String language;
	private String country;
	private String city;
	private String province;
	
	
	public static BasicUserInfo populate(UserProfile profile) {
		BasicUserInfo info = new BasicUserInfo();
		info.setAvatarUrl(profile.getAvatarUrl());
		info.setCity(profile.getCity());
		info.setProvince(profile.getProvince());
		info.setCountry(profile.getCountry());
		info.setGender(profile.getGender());
		info.setLanguage(profile.getLanguage());
		info.setNickName(profile.getNickName());
		
		return info;
	}
	
}
