package com.hafrans.tongrentang.wechat.user.domain.vo;

import com.hafrans.tongrentang.wechat.user.domain.entity.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserInfoWithToken{
	private String avatarUrl;
	private String nickName;
	private int gender;
	private String language;
	private String country;
	private String city;
	private String province;
	private String token;
	
	public static BasicUserInfoWithToken populate(UserProfile profile, String token) {
		BasicUserInfoWithToken info = new BasicUserInfoWithToken();
		info.setAvatarUrl(profile.getAvatarUrl());
		info.setCity(profile.getCity());
		info.setProvince(profile.getProvince());
		info.setCountry(profile.getCountry());
		info.setGender(profile.getGender());
		info.setLanguage(profile.getLanguage());
		info.setNickName(profile.getNickName());
		info.setToken(token);
		return info;
	}
	
}
