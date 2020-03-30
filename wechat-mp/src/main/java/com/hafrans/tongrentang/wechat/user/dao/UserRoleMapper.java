package com.hafrans.tongrentang.wechat.user.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import com.hafrans.tongrentang.wechat.user.domain.entity.Role;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;
import com.hafrans.tongrentang.wechat.user.domain.entity.UserRole;

@Mapper
public interface UserRoleMapper {
	
	
	@Results(id="role",value={
			@Result(id=true,column="id",javaType=long.class,property="id"),
			@Result(column="name", javaType=String.class, property="name"),
			@Result(column="descriptor", javaType=String.class,property="descriptor"),
			@Result(column="createat",javaType=Timestamp.class, property="createAt"),
			@Result(column="id",property="permissions",many=@Many(fetchType=FetchType.LAZY,select="com.hafrans.tongrentang.wechat.user.dao.RolePermissionMapper.queryByRoleId"))
		})
	@Select("select roles.id as id , role_name as name, role_descriptor as descriptor, create_at as createat from user_role left join roles on user_role.role_id= roles.id where user_role.user_id = #{id} ")
	public List<Role> queryAllRoleByUserId(long id);
	
	
	@Results(id="user",value= {
			@Result(column="id", property="id",javaType=Long.class),
			@Result(column="openid", property="openId",javaType=String.class),
			@Result(column="unionid", property="unionId",javaType=String.class),
			@Result(column="sessionkey", property="sessionKey",javaType=String.class),
			@Result(column="is_wechat_user", property="isWechatUser"),
			@Result(column="username", property="username",javaType=String.class),
			@Result(column="credentials", property="credentials",javaType=String.class),
			@Result(column="createat", property="createAt",javaType=Timestamp.class),
			@Result(column="updateat", property="updateAt",javaType=Timestamp.class),
			@Result(column="lockuntil", property="lockUntil",javaType=Timestamp.class),
			@Result(column="id",property="roles",many=@Many(fetchType=FetchType.LAZY,select="com.hafrans.tongrentang.wechat.user.dao.UserRoleMapper.queryAllRoleByUserId")),
			@Result(column="id",property="profile",one=@One(fetchType=FetchType.LAZY,select="com.hafrans.tongrentang.wechat.user.dao.UserProfileMapper.queryByUserProfileId" ))
			
	})
	@Select("select users.id as id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
		  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
		  + "from users left join user_role on users.id = user_role.user_id where user_role.role_id = #{id}")
	public List<User> queryAllUserByRoleId(long id);
	
	
	@Insert("insert into user_role (user_id, role_id) values (#{userId}, #{roleId})")
	public int createWithUserIdandRoleId(@Param("userId") long userId, @Param("roleId") long roleId);
	
	
	@Insert("insert into user_role (user_id, role_id) values (#{userId}, #{roleId})")
	public int create(UserRole role);
	
	@Delete("delete from user_role where id = #{id}")
	public int deleteById(long id);
	
	@Delete("delete from user_role where user_id = #{userId} and role_id = #{roleId}")
	public int deleteWithUserIdandRoleId(@Param("userId") long userId, @Param("roleId") long roleId);
	
	@Delete("delete from user_role where user_id = #{userId} and role_id = #{roleId}")
	public int delete(UserRole ur);
	
}
