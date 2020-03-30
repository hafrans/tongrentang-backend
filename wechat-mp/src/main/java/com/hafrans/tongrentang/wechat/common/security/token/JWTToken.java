package com.hafrans.tongrentang.wechat.common.security.token;

import org.apache.shiro.authc.HostAuthenticationToken;

import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;

public class JWTToken implements HostAuthenticationToken {

	private static final long serialVersionUID = -6720686612996052296L;

	private Object principal;

	private String credentials;

	private String host;

	public JWTToken() {

	}

	public JWTToken(SystemUserPrincipal principal) {
		this.principal = principal;
	}

	/**
	 * @return the credentials
	 */
	public String getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	/**
	 * @return the principal
	 */
	public Object getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

}
