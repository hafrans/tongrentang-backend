package com.hafrans.tongrentang.wechat.user.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hafrans.tongrentang.wechat.common.security.LoginUserType;
import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.security.UserClaims;
import com.hafrans.tongrentang.wechat.common.security.realm.JWTRealm;
import com.hafrans.tongrentang.wechat.common.status.PlainStatus;
import com.hafrans.tongrentang.wechat.common.status.exception.StatusException;
import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.Role;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;
import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;
import com.hafrans.tongrentang.wechat.user.exception.UserNotFoundException;
import com.hafrans.tongrentang.wechat.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private static Algorithm alg = Algorithm.HMAC256(JWTRealm.JWT_SECRET);

	@Autowired
	private UserMapper userMapper;

	@Value("${trtconfig.expire-time}")
	private Long expireTime;

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
			throw new StatusException(PlainStatus.STTAUS_LOGIN_NOT_REGISTERED, "user not registered",
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

}
