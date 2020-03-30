package com.hafrans.tongrentang.wechat.utils;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.common.base.Optional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WxDataUtils {
	
	public final static String WX_MP_ALGORITHM = "AES/CBC/PKCS7Padding";
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static Optional<String> decryptData(String encryptedData, String iv, String sessionKey){
		
		if ("".equals(encryptedData) || "".equals(iv) || "".equals(sessionKey)) {
			log.warn("WxDataUtils@decryptData IllegalArgument");
			throw new IllegalArgumentException("invalid arguements");
		}
		
		byte[] _key = Base64.decode(sessionKey);
		byte[] _iv = Base64.decode(iv);
		byte[] _encryptedData = Base64.decode(encryptedData);
		byte[] result = null;
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(_key, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(_iv);
		Cipher cipher = null;
		try {
			
			cipher = Cipher.getInstance(WX_MP_ALGORITHM,"BC");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			result = cipher.doFinal(_encryptedData);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return Optional.absent();
		}
		
		return Optional.fromNullable(new String(result));
	}
	
	
}
