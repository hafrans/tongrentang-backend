package com.hafrans.tongrentang.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hafrans.tongrentang.wechat.Application;
import com.hafrans.tongrentang.wechat.common.formattingconversion.TimestampFormatter;

@AutoConfigureMockMvc
@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class FormatterConversionServiceTest {
	
	
	@Autowired
	private WebApplicationContext applicationContext;
	
	@SuppressWarnings("unused")
	private MockMvc mock;
	
	
	@BeforeAll
	
	public void setUp() {
		
		this.mock = MockMvcBuilders.webAppContextSetup(applicationContext).build();
		
	}
	
	
	@Test
	public void timestampFormatterTest() throws ParseException {
		
		TimestampFormatter formatter = new TimestampFormatter();
		
		String time = "2020-02-02 12:34:56";
		
		Timestamp timestamp = formatter.parse(time, null);
		
		String result = formatter.print(timestamp, null);
		
		assertThat(result).isEqualTo(time);
		
	}

}
