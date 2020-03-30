package com.hafrans.tongrentang.user.domain;

import java.sql.Timestamp;

public class TestUserTimeStampVO {
	
	private String name;
	
	private Timestamp time;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "TestUserTimeStampVO [name=" + name + ", time=" + time + "]";
	}

	public TestUserTimeStampVO(String name, Timestamp time) {
		super();
		this.name = name;
		this.time = time;
	}
	
	public TestUserTimeStampVO() {
		
	}
	
	
	
}
