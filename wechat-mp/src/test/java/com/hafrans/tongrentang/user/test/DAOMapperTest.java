package com.hafrans.tongrentang.user.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hafrans.tongrentang.TestApplication;
import com.hafrans.tongrentang.wechat.Application;
import com.hafrans.tongrentang.wechat.user.dao.PermissionMapper;
import com.hafrans.tongrentang.wechat.user.dao.RoleMapper;
import com.hafrans.tongrentang.wechat.user.dao.RolePermissionMapper;
import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.dao.UserProfileMapper;
import com.hafrans.tongrentang.wechat.user.dao.UserRoleMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.Permission;
import com.hafrans.tongrentang.wechat.user.domain.entity.Role;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;
import com.hafrans.tongrentang.wechat.user.domain.entity.UserProfile;

@SpringBootTest(classes = {Application.class, TestApplication.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class DAOMapperTest {
	
	
	@Autowired
	private PermissionMapper pmapper;
	
	@Autowired
	private RoleMapper rmapper;
	
	@Autowired 
	private RolePermissionMapper rpmapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@Test 
	public void roleMapperQueryTest() {
		
		List<Role> roles = rmapper.queryAll();
		System.out.println(roles);
		assertThat(roles.size()).isGreaterThan(0);
		
		Role role = rmapper.queryOneById(1);
		System.out.println(role);
		assertThat(role.getId()).isEqualTo(1);
		
	}
	
	@Test
	public void PermissionMapperQueryTest() {
		List<Permission> perms = pmapper.queryAllPermissions();
		System.out.println(Arrays.toString(perms.toArray()));
		assertThat(perms.size()).isGreaterThan(1);
	}
	
	@Test
	public void queryPermissionByRoleIdTest() {
		List<Permission> perms = rpmapper.queryByRoleId(1);
		System.out.println(perms);
		assertThat(perms.size()).isGreaterThan(1);
	}
	
	@Test
	public void queryUserTest() {
		User user = userMapper.queryByUserId(1);
		System.out.println(user);
		assertThat(user.getProfile().getGender()).isEqualTo(1);
		System.out.println(user.getRoles().get(0).getPermissions().get(0).getName());
	}
	
	@Test 
	public void updateUserProfileTest() {
		String i = String.valueOf(System.currentTimeMillis());
		UserProfile profile = userProfileMapper.queryByUserProfileId(1);
		profile.setAppid(i);
		int result = userProfileMapper.update(profile);
		assertThat(result).isEqualTo(1);
		UserProfile profile2 = userProfileMapper.queryByUserProfileId(1);
		assertThat(profile2.getAppid()).isEqualTo(i);
		
	}
	
	// @Test passed!
	public void createAndUpdateAndDeleteUserTest() {
		
		String tag = String.valueOf(System.currentTimeMillis() - 100);
		
		User newuser = new User();
		newuser.setOpenId(tag);
		newuser.setUnionId(tag);
		newuser.setSessionKey(tag);
		newuser.setIsWechatUser(true);
		long i = userMapper.createAndGetKey(newuser);
		assertThat(i).isEqualTo(1);
		assertThat(newuser.getId()).isGreaterThan(1);
		System.out.println("user-id"+newuser.getId());
		
		User guser = userMapper.queryByUserId(newuser.getId());
		
		assertThat(guser.getOpenId()).isEqualTo(tag);
	
		guser.setOpenId(tag+"asdas");
		int i3 = userMapper.update(guser);
		assertThat(i3).isEqualTo(1);
		
		User uuuser = userMapper.queryByUserIdNotLazy(guser.getId());
		
		assertThat(uuuser.getOpenId()).isEqualTo(tag+"asdas");
		
		int i2 = userMapper.delete(guser);
		
		assertThat(i2).isEqualTo(1);
		
	}
	
	@Test
	public void queryUserByRoleTest() {
		List<User> users = userRoleMapper.queryAllUserByRoleId(1);
		System.out.println(users);
		assertThat(users.size()).isGreaterThan(1);
		
	}
	
	@Test
	public void userRoleMapper() {
		
	}
	
	
	
}
