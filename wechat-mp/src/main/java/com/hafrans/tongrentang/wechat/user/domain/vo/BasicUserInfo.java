package com.hafrans.tongrentang.wechat.user.domain.vo;

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
}
