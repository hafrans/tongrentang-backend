package com.hafrans.tongrentang.wechat.user.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.hafrans.tongrentang.wechat.user.domain.entity.Role;

@Mapper
public interface RoleMapper {

	
	@Results(id="role",value={
		@Result(id=true,column="id",javaType=long.class,property="id"),
		@Result(column="name", javaType=String.class, property="name"),
		@Result(column="descriptor", javaType=String.class,property="descriptor"),
		@Result(column="createat",javaType=Timestamp.class, property="createAt"),
		@Result(column="id",property="permissions",many=@Many(fetchType=FetchType.LAZY,select="com.hafrans.tongrentang.wechat.user.dao.RolePermissionMapper.queryByRoleId"))
	})
	@Select("select id , role_name as name, role_descriptor as descriptor, create_at as createat from roles")
	public List<Role> queryAll();
	
	
	@ResultMap("role")
	@Select("select id , role_name as name, role_descriptor as descriptor, create_at as createat from roles where id = #{id}")
	public Role queryOneById(long id);
	
	
	@Delete("delete from roles where id = ${id}")
	public int deleteById(long id);
	
	
	@Update("update roles set role_name = #{name}, create_at = #{createAt}, role_descriptor = #{descriptor} where id = #{id}")
	public int update(Role role);
	
	
}
