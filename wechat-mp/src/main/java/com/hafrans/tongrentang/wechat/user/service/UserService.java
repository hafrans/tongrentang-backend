package com.hafrans.tongrentang.wechat.user.service;

import com.hafrans.tongrentang.wechat.common.security.LoginUserType;
import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.security.UserClaims;
import com.hafrans.tongrentang.wechat.common.status.exception.StatusException;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;
import com.hafrans.tongrentang.wechat.user.domain.vo.Code2SessionResponse;
import com.hafrans.tongrentang.wechat.user.exception.UserNotFoundException;

public interface UserService {
	
	/**
	 * generate jwt token 
	 * @param openId
	 * @param type userLoginType
	 * @return token string
	 * @throws UserNotFoundException 
	 */
	public String generateToken(User user, LoginUserType type) throws UserNotFoundException;
	
	
	/**
	 * refresh jwt token 
	 * @param openId
	 * @param type userLoginType
	 * @return token string
	 * @throws UserNotFoundException 
	 */
	public String refreshToken(User user, UserClaims oldClaims, LoginUserType type) throws UserNotFoundException;
	
	
	
	/**
	 * login by code2session interface (openId)
	 * @param openId
	 * @return token string
	 */
	public String loginByCode2Session(Code2SessionResponse c2sp) throws UserNotFoundException, StatusException;
	
	
	/**
	 * 
	 * refresh token by system user principal
	 * 
	 * @param principal
	 * @return new token string.
	 * @throws StatusException 
	 * @throws UserNotFoundException 
	 */
	public String refreshTokenByPrincipal(SystemUserPrincipal principal) throws StatusException, UserNotFoundException;
	

}
