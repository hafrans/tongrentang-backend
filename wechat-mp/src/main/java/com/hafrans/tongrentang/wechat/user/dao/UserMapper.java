package com.hafrans.tongrentang.wechat.user.dao;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.FetchType;

import com.hafrans.tongrentang.wechat.user.domain.entity.User;

@Mapper
public interface UserMapper {
	
	
	@ResultMap("user")
	@Select("select id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
		  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
		  + "from users")
	public User queryAll();
	
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
	@Select("select id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
		  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
		  + "from users where id = #{id}")
	public User queryByUserId(long id);
	
	
	
	
	@Results(id="user_lazy",value= {
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
			@Result(column="id",property="profile",one=@One(fetchType=FetchType.EAGER,select="com.hafrans.tongrentang.wechat.user.dao.UserProfileMapper.queryByUserProfileId" ))
			
	})
	@Select("select id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
		  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
		  + "from users where id = #{id}")
	public User queryByUserIdNotLazy(long id);
	
	
	
	
	@ResultMap("user")
	@Select("select id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
			  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
			  + "from users where openid = #{openId}")
	public User queryByOpenId(String openId);
	
	
	@ResultMap("user")
	@Select("select id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
			  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
			  + "from users where union_id = #{unionId}")
	public User queryByUnionId(String unionId);
	
	
	@ResultMap("user")
	@Select("select id, openid, union_id as unionid, session_key as sessionKey, is_wechat_user, username, "
			  + "credentials, create_at as createAt, update_at as updateAt, lock_until as lockUntil "
			  + "from users where username = #{username}")
	public User queryByUsername(String username);
	
	
	
	
	@Insert("insert into users (openid, union_id, session_key, is_wechat_user, username, credentials, create_at, update_at, lock_until) values"
		  + "(#{openId}, #{unionId}, #{sessionKey}, #{isWechatUser}, #{username}, #{credentials}, localtimestamp, localtimestamp, #{lockUntil})")
	public int create(User user);
	
	
	@Insert("insert into users (openid, union_id, session_key, is_wechat_user, username, credentials, create_at, update_at, lock_until) values"
			  + "(#{openId}, #{unionId}, #{sessionKey}, #{isWechatUser}, #{username}, #{credentials}, localtimestamp, localtimestamp, #{lockUntil})")
	@SelectKey(before=false, statement="select currval('users_id_seq') as id",keyProperty = "id", resultType = long.class)
	public long createAndGetKey(User user);
	
	
	@Update("update users set openid = #{openId}, union_id = #{unionId}, session_key = #{sessionKey}, is_wechat_user = #{isWechatUser},"
		  + "username = #{username}, credentials = #{credentials}, create_at = #{createAt}, update_at = #{updateAt}, lock_until = #{lockUntil} where id = #{id}")
	public int update(User user);
	
	
	@Update("update users set update_at = localtimestamp where id = #{id}")
	public int updateTimeById(long id);
	
	@Update("update users set session_key = #{sessionKey} where id = #{id}")
	public int updateSessionKeyById(@Param("id") long id, @Param("sessionKey") String sessionKey);
	
	
	@Delete("delete from users where id = #{id}")
	public int deleteById(long id);
	
	@Delete("delete from users where id = #{id}")
	public int delete(User user);
	
	
}
