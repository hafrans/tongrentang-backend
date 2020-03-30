package com.hafrans.tongrentang.wechat.user.service;

import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;

public interface WechatUserService {

	/**
	 *      微信小程序登录
	 * @param jsCode wx.login 获取的Token
	 * @return Code2SessionResponse 
	 */
	public Code2SessionResponse login(String jsCode);
	
	
	
}
