package com.hafrans.tongrentang.wechat.user.service.impl;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hafrans.tongrentang.wechat.config.WechatMPInfoConfig;
import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;
import com.hafrans.tongrentang.wechat.user.service.WechatUserService;
import com.hafrans.tongrentang.wechat.utils.WxMappingJackson2HttpMessageConverter;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class WechatUserServiceImpl implements WechatUserService {

	
	
	@Autowired
	WechatMPInfoConfig wechatConfig;
	
	
	@Override
	public Code2SessionResponse login(String jsCode) {
		
		if (jsCode == null || "".contains(jsCode)) {
			log.warn("login","js code is null");
			return null;
		}
		
		log.info("WechatUserServiceImpl@login","do login");
		
		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri(wechatConfig.getCode2SessionUri())
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(15))
				.additionalMessageConverters(new WxMappingJackson2HttpMessageConverter())
				.build();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", wechatConfig.getAppId());
		map.put("secret", wechatConfig.getSecret());
		map.put("jscode", jsCode);
		

		ResponseEntity<Code2SessionResponse> resp = restTemplate.getForEntity(wechatConfig.getCode2SessionUri(), 
																		      Code2SessionResponse.class,
																		      map);
		
		log.debug("code2session login" + resp.getBody());
		
		return resp.getBody();
	}

}
