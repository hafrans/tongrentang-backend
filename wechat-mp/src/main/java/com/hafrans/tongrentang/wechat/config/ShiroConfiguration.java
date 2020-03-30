package com.hafrans.tongrentang.wechat.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import com.hafrans.tongrentang.wechat.common.security.filter.JWTFilter;
import com.hafrans.tongrentang.wechat.common.security.realm.JWTRealm;
import com.hafrans.tongrentang.wechat.common.security.realm.UsernamePasswordRealm;
import com.hafrans.tongrentang.wechat.common.security.service.CachedSubjectService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ShiroConfiguration {

	public final static int SHIRO_CREDIENTIAL_ITERATION = 50;
	public final static boolean SHIRO_CREDIDENTIAL_SALTENABLED = false;

	@Bean 
	public JWTFilter jwtFilter() {
		return new JWTFilter();
	}
	
	@Bean
	public JWTRealm jwtrealm() {
		return new JWTRealm();
	}
	
	@Bean
	public UsernamePasswordRealm usernamepasswordRealm() {
		return new UsernamePasswordRealm();
	}
	
	
	@Bean
	public CachedSubjectService cachedSubjectService() {
		return new CachedSubjectService();
	}
	
	/**
	 *     禁用 session
	 * @return
	 */
	@Bean
	protected SessionStorageEvaluator sessionStorageEvaluator() {
		DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
		sessionStorageEvaluator.setSessionStorageEnabled(false);
		return sessionStorageEvaluator;
	}
	
	@Bean
	public SecurityManager securityManager(JWTRealm jwtRealm, UsernamePasswordRealm uRealm) {
		List<Realm> realms = List.of(jwtRealm,uRealm);
		
		DefaultWebSecurityManager dsm = new DefaultWebSecurityManager();
		dsm.setRealms(realms);

		return dsm;
	}

		
	/**
	 *      默认credentials matcher ，可以忽略掉
	 * @return CredentialsMatcher
	 */
	@Bean
	public CredentialsMatcher credentialsMatcher() {

		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("md5");
		matcher.setHashIterations(SHIRO_CREDIENTIAL_ITERATION);

		return matcher;

	}

	/**
	   *  设置 shiro的filter
	 * 
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager sm) {

		ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
		sffb.setSecurityManager(sm);
		
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/api/wx/**", "testc");
		filterChainDefinitionMap.put("/**", "anon");
		
		
		sffb.setFilterChainDefinitionMap(filterChainDefinitionMap);
		sffb.setLoginUrl("/login");
		sffb.setUnauthorizedUrl("/unauthorized");
		
		
		HashMap<String, Filter> filters = new HashMap<String, Filter>();
//		filters.put("testc", jwtFilter());
		filters.put("testc", new JWTFilter());
		sffb.setFilters(filters);

		return sffb;
	}

	

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * *
	 * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	 * *
	 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
	 * * @return
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager sm) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(sm);
		return authorizationAttributeSourceAdvisor;
	}

	

}
