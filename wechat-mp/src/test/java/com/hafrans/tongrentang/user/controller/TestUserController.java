package com.hafrans.tongrentang.user.controller;

import java.sql.Timestamp;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hafrans.tongrentang.user.domain.TestUserTimeStampVO;

@RestController
@RequestMapping("/test/user")
public class TestUserController {
	
	@RequestMapping("/timestampformat")
	public Timestamp timestampjson(@RequestBody TestUserTimeStampVO timebo) throws Exception {
		
		if(! "test".contentEquals(timebo.getName())){
			throw new Exception("check name");
		}
		
		return timebo.getTime();
	}
	
	@RequestMapping("/timestamp")
	public Timestamp timestamp(@RequestParam("in") Timestamp time) {
		return time;
	}
	
}
