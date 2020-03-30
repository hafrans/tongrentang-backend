package com.hafrans.tongrentang.wechat.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
	
	private String id;
	
	private String user_id;
	
	private String role_id;
	
}
