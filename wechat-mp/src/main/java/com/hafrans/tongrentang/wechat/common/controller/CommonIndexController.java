package com.hafrans.tongrentang.wechat.common.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hafrans.tongrentang.wechat.common.status.exception.StatusException;
import com.hafrans.tongrentang.wechat.common.vo.ResponseData;

import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/")
@RestController
public class CommonIndexController {

	@GetMapping("/login")
	@ApiIgnore
	public ResponseEntity<Map<String,Object>> login(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("errcode", 610000);
		map.put("errmsg","not:login");
		map.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
	} 
	
	@GetMapping("/unauthorized")
	@ApiIgnore
	public ResponseEntity<Map<String,Object>> unauthorized(){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("errcode", 710000);
		map.put("errmsg","not:authorized");
		map.put("timestamp",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/loginfailed")
	@ApiIgnore
	public ResponseData<Object> unauthorized(HttpServletRequest req){
		StatusException se = (StatusException) req.getAttribute("err");
		return ResponseData.Builder(se.getStatus(), se.getMessage(), Timestamp.from(Instant.now()), null);
	}
	
}
