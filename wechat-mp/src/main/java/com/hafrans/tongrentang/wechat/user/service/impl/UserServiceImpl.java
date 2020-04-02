package com.hafrans.tongrentang.wechat.user.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hafrans.tongrentang.wechat.common.security.LoginUserType;
import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.security.UserClaims;
import com.hafrans.tongrentang.wechat.common.security.realm.JWTRealm;
import com.hafrans.tongrentang.wechat.common.status.PlainStatus;
import com.hafrans.tongrentang.wechat.common.status.exception.StatusException;
import com.hafrans.tongrentang.wechat.config.WechatMPInfoConfig;
import com.hafrans.tongrentang.wechat.user.dao.RoleMapper;
import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.dao.UserProfileMapper;
import com.hafrans.tongrentang.wechat.user.dao.UserRoleMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.Role;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;
import com.hafrans.tongrentang.wechat.user.domain.entity.UserProfile;
import com.hafrans.tongrentang.wechat.user.domain.vo.BasicUserInfo;
import com.hafrans.tongrentang.wechat.user.domain.vo.BasicUserInfoWithToken;
import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;
import com.hafrans.tongrentang.wechat.user.domain.vo.WechatMPRegister;
import com.hafrans.tongrentang.wechat.user.exception.UserNotFoundException;
import com.hafrans.tongrentang.wechat.user.service.UserService;
import com.hafrans.tongrentang.wechat.utils.WxDataUtils;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private static Algorithm alg = Algorithm.HMAC256(JWTRealm.JWT_SECRET);

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserProfileMapper userprofileMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserRoleMapper userroleMapper;

	@Value("${trtconfig.expire-time}")
	private Long expireTime;
	
	@Autowired
	private WechatMPInfoConfig mpInfoConfig;

	@Override
	public String generateToken(User user, LoginUserType type) throws UserNotFoundException {
		UserClaims uc = this.claimsGen(user, type);
		return this.tokenGen(uc);
	}

	@Override
	public String refreshToken(User user, UserClaims oldClaims, LoginUserType type) throws UserNotFoundException {
		return this.tokenGen(oldClaims);
	}

	private UserClaims claimsGen(User user, LoginUserType type) {

		UserClaims claims = new UserClaims();
		claims.setId(user.getId());
		claims.setSubject(new RandomString(32).nextString());

		List<Role> roles = user.getRoles();

		List<String> roleList = roles.stream().map(e -> {
			return e.getDescriptor();
		}).collect(Collectors.toList());

		List<String> permList = roles.stream().flatMap(e -> {
			return e.getPermissions().stream();
		}).map(e -> {
			return e.getDescriptor();
		}).collect(Collectors.toList());

		claims.setType(type.getType());
		claims.setPerms(permList);
		claims.setRoles(roleList);

		return claims;

	}

	private String tokenGen(UserClaims claims) {
		return claims.jwtUserClaimsBuild(JWT.create()).withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
				.sign(alg);

	}

	private User fetchUserByOpenId(String openId) throws UserNotFoundException, StatusException {

		User user = userMapper.queryByOpenId(openId);

		if (user == null) { // not registered.
			throw new StatusException(PlainStatus.STATUS_LOGIN_NOT_REGISTERED, "user not registered",
					new UserNotFoundException("user not found"));
		}

		// check user lock
		try {
			checkUserLocked(user);
		} catch (LockedAccountException e) {
			throw new StatusException(PlainStatus.STATUS_LOGIN_FAILED_LOCKED, e.getMessage());
		}

		return user;
	}

	/**
	 * check user lock
	 * 
	 * @param user
	 * @return
	 * @throws LockedAccountException
	 */
	private boolean checkUserLocked(User user) throws LockedAccountException {

		if (user.getLockUntil() != null) { // check user is locked or not

			Timestamp t = user.getLockUntil();
			if (t.getTime() - System.currentTimeMillis() > 0) {
				// TODO need log
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				throw new LockedAccountException("user is locked until:" + sdf.format(new Date(t.getTime())));
			}

		}
		return true;

	}

	@Override
	public String loginByCode2Session(Code2SessionResponse c2sp) throws UserNotFoundException, StatusException {
		User user = this.fetchUserByOpenId(c2sp.getOpenId());
		user.setSessionKey(c2sp.getSessionKey());
		// update sessionKey
		int resultCount = userMapper.updateSessionKeyById(user.getId(), c2sp.getSessionKey()); // use this method
																								// provided by mapper
		if (resultCount != 1) {
			log.error(resultCount + " record updated !");
			throw new StatusException(PlainStatus.STATUS_UNKNOWN_FAILED, "login session update failed", null);
		}
		return this.generateToken(user, LoginUserType.WECHAT_MP);
	}

	@Override
	public String refreshTokenByPrincipal(SystemUserPrincipal principal) throws StatusException, UserNotFoundException {

		User olduser = principal.getUser();
		User newuser = userMapper.queryByUserId(olduser.getId()); // fetch new user profile to check available

		if (newuser == null) { // user not exists.
			throw new StatusException(PlainStatus.STATUS_JWT_REFRESH_FAILED, "user not found",
					new UserNotFoundException("user id" + olduser.getId() + "not found"));
		}

		try {
			checkUserLocked(newuser);
		} catch (Exception e) {
			throw new StatusException(PlainStatus.STATUS_JWT_REFRESH_FAILED, "user is locked now", e);
		}
		// update user cliams
		UserClaims oldClaims = principal.getClaims();
		LoginUserType userType = LoginUserType.parse(oldClaims.getType());
		UserClaims newClaims = this.claimsGen(newuser, userType);
		newClaims.setSubject(oldClaims.getSubject());

		return this.refreshToken(newuser, newClaims, userType);
	}

	@Override
	@Transactional
	public BasicUserInfoWithToken registerViaWechatMP(Code2SessionResponse c2sp, WechatMPRegister regform) throws StatusException, UserNotFoundException {
		
		String openId = c2sp.getOpenId();
		String sessionKey = c2sp.getSessionKey();
		String unionId = c2sp.getUnionId();
		
		String sig = regform.getSignature();
		String rawData = regform.getRawData(); 
		
		try {
			if (!WxDataUtils.checkSignature(rawData+sessionKey, sig)) {
				throw new StatusException(PlainStatus.STATUS_REGISTER_BAD_SIGNATURE, "signature check failed");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BasicUserInfo info = null;
		ObjectMapper omapper = new ObjectMapper();
		try {
			 info = omapper.readValue(rawData, BasicUserInfo.class);
		} catch (JsonMappingException e) {
			throw new StatusException(PlainStatus.STATUS_REGISTER_BAD_SIGNATURE, "mapping failed",e);
		} catch (JsonProcessingException e) {
			throw new StatusException(PlainStatus.STATUS_REGISTER_BAD_SIGNATURE, "rawData processing failed",e);
		}
		
		User user = new User();
		user.setOpenId(openId);
		user.setSessionKey(sessionKey);
		user.setIsWechatUser(true);
		user.setLockUntil(null);
		user.setSessionKey(sessionKey);
		user.setUnionId(unionId);
		
		UserProfile profile = new UserProfile();
	
		profile.setAppid(mpInfoConfig.getAppId());
		profile.setAvatarUrl(info.getAvatarUrl());
		profile.setCity(info.getCity());
		profile.setProvince(info.getProvince());
		profile.setCountry(info.getCountry());
		profile.setGender(info.getGender());
		profile.setNickName(info.getNickName());
		profile.setLanguage(info.getLanguage());
		
		long count = 0;
		User tmpUser = userMapper.queryByOpenId(openId);
		if (tmpUser != null) {
			log.warn("重复注册");
			throw new StatusException(PlainStatus.STATUS_REGISTER_ALREADY_REGISTERED, "you have registered before");
		}
//		try {
//			count = userMapper.createAndGetKey(user);
//		}catch (DuplicateKeyException e) {
//			log.warn("重复注册");
//			throw new StatusException(PlainStatus.STATUS_REGISTER_ALREADY_REGISTERED, "you have registered before", e);
//		}
		count = userMapper.createAndGetKey(user);
		if (count != 1) {
			log.warn("用户创建失败");
			throw new StatusException(PlainStatus.STATUS_REGISTER_FAILED, "user register failed");
		}
		count = userprofileMapper.createAndGetKey(profile);
		if (count != 1) {
			log.warn("用户信息创建失败");
			throw new StatusException(PlainStatus.STATUS_REGISTER_FAILED, "user register failed");
		}
		
		// write roles
		Role role = roleMapper.queryOneByDescriptor("mpuser");
		count = userroleMapper.createDirectly(user.getId(), role.getId());
		if (count != 1) {
			log.warn("用户角色设定失败");
			throw new StatusException(PlainStatus.STATUS_REGISTER_FAILED, "user register failed");
		}
		
		// you should get a delegated user to create token!
		User delegatedUser = userMapper.queryByUserId(user.getId());
		
		BasicUserInfoWithToken buiwt = BasicUserInfoWithToken.populate(profile, this.generateToken(delegatedUser, LoginUserType.WECHAT_MP));
		
		return buiwt;
	}

}
