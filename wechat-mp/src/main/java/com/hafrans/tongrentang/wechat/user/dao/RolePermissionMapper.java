package com.hafrans.tongrentang.wechat.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hafrans.tongrentang.wechat.user.domain.entity.Permission;
import com.hafrans.tongrentang.wechat.user.domain.entity.RolePermission;

@Mapper
public interface RolePermissionMapper {
	
	@Select("select perms.id as id, perms.perm_name as name, perms.perm_descriptor as descriptor, perms.create_at as createat from perms left join role_perm rp on perms.id = rp.perm_id where rp.role_id = #{id}")
    public List<Permission> queryByRoleId(long rid);	
	
	@Insert("insert into role_perm (role_id, perm_id) values (#{roleId}, #{permId})")
	public int createWithRoleIdAndPermId(@Param("roleId") long rid, @Param("permId") long uId);
	
	
	@Insert("insert into role_perm (role_id, perm_id) values (#{roleId}, #{permId})")
	public int create(RolePermission rp);
	
	
	@Delete("delete from role_perm where role_id = #{roleId} and perm_id = #{permId}")
	public int delete(RolePermission rp);
	
	@Delete("delete from role_perm where id = #{id}")
	public int deleteById(long id);
	
}
