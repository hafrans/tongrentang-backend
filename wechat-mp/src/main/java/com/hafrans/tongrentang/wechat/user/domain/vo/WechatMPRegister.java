package com.hafrans.tongrentang.wechat.user.domain.vo;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WechatMPRegister {

	@NotNull
	private String iv;
	
	@NotNull
	private String encryptedData;
	
	@NotNull
	private String rawData;
	
	@NotNull
	private String signature;
	
	@NotNull
	private String code;
	
	
}
