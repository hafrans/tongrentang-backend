package com.hafrans.tongrentang.wechat.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hafrans.tongrentang.wechat.user.domain.entity.Permission;

@Mapper
public interface PermissionMapper {
	
	
	@Select("select id, perm_name as name, perm_descriptor as descriptor, create_at as createat from perms")
	public List<Permission> queryAllPermissions();
	
	@Select("select id, perm_name as name, perm_descriptor as descriptor, create_at as createat from perms where id = #{id}")
	public Permission queryById(long id);
	
}
