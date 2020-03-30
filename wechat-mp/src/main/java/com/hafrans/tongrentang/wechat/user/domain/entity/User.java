package com.hafrans.tongrentang.wechat.user.domain.entity;

import java.sql.Timestamp;
import java.util.List;

public class User {

	private long id;
	
	private String openId;

	private String unionId;
	
	private String sessionKey;
	
	private Boolean isWechatUser;
	
	private String username;
	
	private String credentials;
	
	private Timestamp createAt;
	
	private Timestamp updateAt;
	
	private Timestamp lockUntil;
	
	private boolean isJwtLogin;
	
	private List<Role> roles;
	
	private UserProfile profile;

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
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @return the unionId
	 */
	public String getUnionId() {
		return unionId;
	}

	/**
	 * @param unionId the unionId to set
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * @param sessionKey the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the isWechatUser
	 */
	public Boolean getIsWechatUser() {
		return isWechatUser;
	}

	/**
	 * @param isWechatUser the isWechatUser to set
	 */
	public void setIsWechatUser(Boolean isWechatUser) {
		this.isWechatUser = isWechatUser;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the createAt
	 */
	public Timestamp getCreateAt() {
		return createAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the updateAt
	 */
	public Timestamp getUpdateAt() {
		return updateAt;
	}

	/**
	 * @param updateAt the updateAt to set
	 */
	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	/**
	 * @return the lockAt
	 */
	public Timestamp getLockUntil() {
		return lockUntil;
	}

	/**
	 * @param lockAt the lockAt to set
	 */
	public void setLockUntil(Timestamp lockAt) {
		this.lockUntil = lockAt;
	}

	/**
	 * @return the isJwtLogin
	 */
	public boolean isJwtLogin() {
		return isJwtLogin;
	}

	/**
	 * @param isJwtLogin the isJwtLogin to set
	 */
	public void setJwtLogin(boolean isJwtLogin) {
		this.isJwtLogin = isJwtLogin;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the profile
	 */
	public UserProfile getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}



	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", openId=" + openId + ", unionId=" + unionId + ", sessionKey=" + sessionKey
				+ ", isWechatUser=" + isWechatUser + ", username=" + username + ", credentials=" + credentials
				+ ", createAt=" + createAt + ", updateAt=" + updateAt + ", lockUntil=" + lockUntil + ", isJwtLogin="
				+ isJwtLogin + ", roles=" + roles + ", profile=" + profile + "]";
	}
	
	
	public User generateBareUser() {
		User user = new User();
		
		user.setCreateAt(getCreateAt());
		user.setCredentials(credentials);
		user.setId(id);
		user.setIsWechatUser(isWechatUser);
		user.setJwtLogin(isJwtLogin);
		user.setLockUntil(lockUntil);
		user.setOpenId(openId);
		user.setProfile(this.getProfile());
//		user.setRoles(roles);
		user.setSessionKey(sessionKey);
		user.setUnionId(unionId);
		user.setUpdateAt(updateAt);
		user.setUsername(username);
		return user;
	}
	
	
}
