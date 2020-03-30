package com.hafrans.tongrentang.user.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.yaml.snakeyaml.util.UriEncoder;

import com.hafrans.tongrentang.TestApplication;
import com.hafrans.tongrentang.wechat.Application;
import com.hafrans.tongrentang.wechat.user.domain.entity.User;

@SpringBootTest(classes= {Application.class,TestApplication.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class UserTest {

	@Autowired
	private WebApplicationContext application;
	
	private MockMvc mock;
	
	
	@BeforeAll
	public void setUp() {
		
		this.mock = MockMvcBuilders.webAppContextSetup(application).build();
	}
	
	@Test
	public void UserTimestampFormattingJsonTest() throws Exception {
	
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/test/user/timestampformat")
																	  .contentType(MediaType.APPLICATION_JSON)
				                                                      .content("{\"name\":\"test\",\"time\":\"2020-02-01 12:34:56\"}");
		
		MvcResult result = this.mock.perform(builder).andReturn();
		
		MockHttpServletResponse resp = result.getResponse();
		
		assertThat(200).isEqualTo(resp.getStatus());
		assertThat("\"2020-02-01 12:34:56\"").isEqualTo(resp.getContentAsString());
		
	}
	
	@Test
	public void UserTimestampFormattingTest() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/test/user/timestamp")
				  .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                  .content(UriEncoder.encode("in=2020-02-01 12:34:56"));

		MvcResult result = this.mock.perform(builder).andReturn();
		
		MockHttpServletResponse resp = result.getResponse();
		
		
		assertThat(200).isEqualTo(resp.getStatus());
		assertThat("\"2020-02-01 12:34:56\"").isEqualTo(resp.getContentAsString());

	}
	
	@Test
	public void ShiroLoginTest() {
		
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()) {
			System.out.println("已经登录成功");
		}else {
			UsernamePasswordToken token = new UsernamePasswordToken();
		}
		
		
	}
	
	
	
	
	
	
	
	
}
