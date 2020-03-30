package com.hafrans.tongrentang.wechat.common.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class CommonIndexController {

	@RequestMapping("/login")
	public ResponseEntity<Map<String,Object>> login(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("errcode", 610000);
		map.put("errmsg","not:login");
		map.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
	} 
	
	@RequestMapping("/unauthorized")
	public ResponseEntity<Map<String,Object>> unauthorized(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("errcode", 710000);
		map.put("errmsg","not:authorized");
		map.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
	}
	
	@RequestMapping("/loginfailed")
	public ResponseEntity<Map<String,Object>> unauthorized(HttpServletRequest req){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("errcode", 610002);
		map.put("errmsg","login:failed");
		map.put("description", req.getAttribute("err"));
		map.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
	}
	
}
