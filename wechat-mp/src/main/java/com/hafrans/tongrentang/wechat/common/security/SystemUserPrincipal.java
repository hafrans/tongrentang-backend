package com.hafrans.tongrentang.wechat.common.security;

import org.springframework.beans.factory.annotation.Autowired;

import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class SystemUserPrincipal {
	
	
	
	private String subject;
	
	private long id;
	
	private UserClaims claims;
	
	private User user;
	
	public SystemUserPrincipal(UserClaims claims) {
		this.id = claims.getId();
		this.subject = claims.getSubject();
		this.claims = claims;
	}
	
	
	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the claims
	 */
	public UserClaims getClaims() {
		return claims;
	}

	/**
	 * @param claims the claims to set
	 */
	public void setClaims(UserClaims claims) {
		this.claims = claims;
	}




	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}




	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}




	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}




	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
}
