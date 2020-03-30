package com.hafrans.tongrentang.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.hafrans.tongrentang.wechat.Application;
import com.hafrans.tongrentang.wechat.user.domain.bo.WxEncryptedUserInfo;
import com.hafrans.tongrentang.wechat.utils.WxDataUtils;

@AutoConfigureMockMvc
@SpringBootTest(classes= {Application.class})
@ExtendWith(SpringExtension.class)
public class WxCipherTest {
	
	@Test
	public void CipherTextDecrypt() {
		
		Optional<String> a = WxDataUtils.decryptData("NL6qa2NnT2d4P6NwbahRNAsaMySyDIyBdLzmZMSerlBU7X5pMOTiFdBhON+fY/jdfJZCDK1rLKLNeFjW3THCKE1qIaAJyguULqrjLt9ZZqCO8AIr1Y/AyLsw3H+ggtSB4fmrpWPnDXGsueMPqADuL3mDYrHYmoyQhv7e1BcPPV/aI75bfSf6pEV5t3XdUYF6JxCQhHtZWWjQ9xcmayi6AOvZ4DHI/FwIXXFxepFF7Pu3Ex1G3k7Kk5kJM6b1CxIojTRwC3mOYaTO1doFqdeBp6207177YRqTfvGJzYMEVaVeAA/4FLVB/1u9OXl3LSPxguQuksZB96CHyPyxqDA7GB2ny8mAkxA1L8Pgz77en9E263mCuPRnPAF1W6yXeVTiQKPkPU6ZGWD5DMmZoVkIUYp1zxv/g5/2RYvpePqmRCVZL9OMY2Jffgwv2mrxZMDL6n4HJT2ZiKd7uU9C9MzfIlH6Gl62W7hl4E9SqLIAPOw=", 
				                "LYxMODXhik6LNo5GTdjPMw==", 
				                "8v7VVS7mFUg3FELwzdyBYQ==");
		
		if(a.isPresent()) {
			System.out.println(a);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			WxEncryptedUserInfo info = mapper.readValue(a.get(), WxEncryptedUserInfo.class);
			System.out.println(info);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
