package com.hafrans.tongrentang.wechat.common.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hafrans.tongrentang.wechat.common.security.SystemUserPrincipal;
import com.hafrans.tongrentang.wechat.common.security.UserClaims;
import com.hafrans.tongrentang.wechat.common.security.realm.JWTRealm;
import com.hafrans.tongrentang.wechat.common.security.service.CachedSubjectService;
import com.hafrans.tongrentang.wechat.common.security.token.JWTToken;
import com.hafrans.tongrentang.wechat.utils.beans.SpringContextUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;


@Slf4j
public class JWTFilter extends AuthenticatingFilter{

	private static final Algorithm alg = Algorithm.HMAC256(JWTRealm.JWT_SECRET);
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#executeLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.executeLogin(request, response);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#createToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#onLoginFailure(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationException, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// TODO Auto-generated method stub
		return super.onLoginFailure(token, e, request, response);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		
		String token = "";
		
		// get token from headers
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsr = (HttpServletRequest) request;
			token = hsr.getHeader("token");
			if (token == null || "".equals(token)) {
				log.warn("no token provided ");
				request.setAttribute("err", "no token provided ");
				return false;
			}
			token = token.strip();
		}
		
		// prepare jwt
		
		// check jwt token whether it is valid.
		JWTVerifier verifier = JWT.require(alg).build();
		
		try {
			DecodedJWT jwt = verifier.verify(token);
			UserClaims claims = new UserClaims(jwt);
			Subject subject = CachedSubjectService.service.getSubject(request, response, claims, getSubject(request, response));
			ThreadContext.bind(subject);
			
			if (!subject.isAuthenticated()) {
				// try auto-login
				
				SystemUserPrincipal principal = new SystemUserPrincipal(claims);
				
				JWTToken jtoken = new JWTToken(principal);
				
				try {
					subject.login(jtoken);
					
					 CachedSubjectService.service.refresh(claims);
					 subject = CachedSubjectService.service.getSubject(request, response, claims, subject);
					return true;
				}catch (ExpiredCredentialsException e) {
					log.warn("JWT login failed. " + e.getMessage());
					request.setAttribute("err", "JWT login failed. " + e.getMessage());
					return false;
				}
				catch(Exception e) {
					log.warn("JWT login failed. " + e.getMessage());
					request.setAttribute("err", "JWT login failed. " + e.getMessage());
					return false;
				}
				
			}else {

				return true;
			}
			
		}catch (Exception e) {
			request.setAttribute("err", "JWT verifier checked one invalid token " + e.getMessage());
			log.warn("JWT verifier checked one invalid token " + e.getMessage());
			return false;
		}
	}

	/* 认证失败后的转发到登录的位置
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		request.getRequestDispatcher("/loginfailed").forward(request, response);
		return false;
	}
	
	
	
	@SuppressWarnings("unused")
	private Subject getSubjectWithId(ServletRequest req, ServletResponse resp, UserClaims userClaims) {
		
		CacheManager manager  = SpringContextUtils.getContext().getBean(CacheManager.class);
		System.out.println(manager);
		
		log.info("缓存未命中！");
		return this.getSubject(req, resp);
	}

	
}
