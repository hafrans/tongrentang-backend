package com.hafrans.tongrentang.wechat.common.security.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.security.token.JWTToken;
import com.hafrans.tongrentang.wechat.user.dao.UserMapper;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;


public class JWTRealm extends AuthorizingRealm{
	
	@Autowired
	private UserMapper usermapper;
		
	
	public static final String JWT_SECRET = "1bdaibyufvebucdysvqweoubfwebovuwe";

	public JWTRealm() {
		super();
		this.setCredentialsMatcher(new CredentialsMatcher() {

			@Override
			public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
				
				/**
				 * Verify 工作都在doGetAuthenticationInfo内进行操作了，这个地方只需要返回
				 * true就行。
				 * 
				 */
				
				return true;
			}
			
		});
	}
	
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		
		return token instanceof JWTToken;
	}



	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		if (usermapper == null) {
			System.err.println("================user mapper is null");
		}else {
			User u = usermapper.queryByUserId(((SystemUserPrincipal)token.getPrincipal()).getId());
			((SystemUserPrincipal)token.getPrincipal()).setUser(u);
		}
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(), null, JWTRealm.class.getSimpleName());
		return info;
	}

}
