package com.hafrans.tongrentang.wechat.user.domain.entity;

import java.sql.Timestamp;

public class Permission {
	
	private long id;
	
	private String name;
	
	private String descriptor;
	
	private Timestamp createAt;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the descriptor
	 */
	public String getDescriptor() {
		return descriptor;
	}

	/**
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", descriptor=" + descriptor + ", createAt=" + createAt
				+ "]";
	}
	
}
