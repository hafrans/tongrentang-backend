package com.hafrans.tongrentang.wechat.common.security.service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;

import com.hafrans.tongrentang.wechat.common.security.UserClaims;

public class CachedSubjectService {
  
	public static CachedSubjectService service;
	
	@Autowired
	@Lazy
	public void setService(CachedSubjectService service) {
		CachedSubjectService.service = service;
	}
	
	@Cacheable(value="subject-cache-",key="#claims.id")
	public Subject getSubject(ServletRequest request, ServletResponse response, UserClaims claims, Subject subject) {
		System.out.println("<<<<<<<<<<<<<<<<<< not hited");
		return subject;
	}
	
	@CacheEvict(value="subject-cache-",key="#claims.id")
	public void refresh(UserClaims claims) {
		
	}
	
}
