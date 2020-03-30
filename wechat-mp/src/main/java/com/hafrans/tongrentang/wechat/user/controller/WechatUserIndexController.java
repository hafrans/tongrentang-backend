package com.hafrans.tongrentang.wechat.user.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.security.UserClaims;
import com.hafrans.tongrentang.wechat.common.security.realm.JWTRealm;
import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;
import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;
import com.hafrans.tongrentang.wechat.user.service.WechatUserService;

@RequestMapping("/api/wx/v1/user")
@RestController
public class WechatUserIndexController {

	@Autowired
	WechatUserService wechatUserService;
	
	@Autowired
	UserMapper userMapper;
		
	@RequestMapping("/")
	public ResponseEntity<Map<String,String>> index(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ping", "pong");
		map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name="js_code", required=true) String jsCode) {
		Code2SessionResponse resp = wechatUserService.login(jsCode);
		return resp.getSessionKey();
	}
	
	@PostMapping("/register")
	public Object login(@RequestBody Map<String, String> body ) {
		return body;
		
	}
	
	@PostMapping("/refresh")
	public String refresh() {
		
		return null;
	}
	
	
	
}
