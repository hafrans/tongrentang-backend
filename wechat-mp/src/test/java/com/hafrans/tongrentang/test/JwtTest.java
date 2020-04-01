package com.hafrans.tongrentang.test;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hafrans.tongrentang.wechat.common.security.LoginUserType;
import com.hafrans.tongrentang.wechat.common.security.UserClaims;
import com.hafrans.tongrentang.wechat.common.security.realm.JWTRealm;

import net.bytebuddy.utility.RandomString;




public class JwtTest {
	
	@Test
	public void generateTest() {
		
		List<String> list = List.of("asd","wdas");
		
		UserClaims claims = new UserClaims(LoginUserType.WECHAT_MP.getType(), new RandomString(32).nextString(), 1, list, list);
		
		Algorithm alg = Algorithm.HMAC256(JWTRealm.JWT_SECRET);
		
		String token = claims.jwtUserClaimsBuild(JWT.create()).withExpiresAt(new Date(System.currentTimeMillis() + 3600000)).sign(alg);
		System.out.println(token);
	}
	
}
