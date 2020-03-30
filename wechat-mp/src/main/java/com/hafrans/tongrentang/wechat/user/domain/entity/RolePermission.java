package com.hafrans.tongrentang.wechat.user.domain.entity;

public class RolePermission {
	
	private long id;
	
	private long roleId;
	
	private long permId;

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
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the permId
	 */
	public long getPermId() {
		return permId;
	}

	/**
	 * @param permId the permId to set
	 */
	public void setPermId(long permId) {
		this.permId = permId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RolePermission [id=" + id + ", roleId=" + roleId + ", permId=" + permId + ", getId()=" + getId()
				+ ", getRoleId()=" + getRoleId() + ", getPermId()=" + getPermId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
