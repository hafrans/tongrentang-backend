package com.hafrans.tongrentang.wechat.common.security;

import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserClaims {
	
	private String type;
	
	private String subject;
	
	private long id;
	
	private List<String> perms;
	
	private List<String> roles;
	
	
	public UserClaims (DecodedJWT decodedJWT) {
		List<String> auds = decodedJWT.getAudience();
		if(auds != null && auds.size() > 0) {
			this.type = auds.get(0);
		}
		this.id = decodedJWT.getClaim("id").asLong();
		this.perms = decodedJWT.getClaim("perms").asList(String.class);
		this.roles = decodedJWT.getClaim("roles").asList(String.class);
		this.subject = decodedJWT.getSubject();
	} 
	
	public JWTCreator.Builder jwtUserClaimsBuild(JWTCreator.Builder builder) {
		
		builder.withIssuer("com.hafrans.tongrentang.wechat");
		builder.withIssuedAt(new Date());
		builder.withSubject(this.subject);
		builder.withAudience(type);
		builder.withClaim("id", id);
		if(perms == null || perms.size() == 0) {
			builder.withArrayClaim("perms", new String[] {});
		}else {
			builder.withArrayClaim("perms", perms.toArray(new String[perms.size()]));
		}
		if(roles == null || roles.size() == 0) {
			builder.withArrayClaim("roles", new String[] {});
		}else {
			builder.withArrayClaim("roles", roles.toArray(new String[roles.size()]));
		}
		
		return builder;
	}
	
}
