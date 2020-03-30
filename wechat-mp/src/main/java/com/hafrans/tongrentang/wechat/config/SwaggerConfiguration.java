package com.hafrans.tongrentang.wechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	
	@Bean
	public Docket docket() {
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.pathMapping("/api-document")
			  .select()
			  .apis(RequestHandlerSelectors.basePackage("com.hafrans.tongrentang.wechat"))
			  .build()
			  .apiInfo(new ApiInfoBuilder()
					  .title("同仁堂微信小程序")
					  .description("同仁堂微信小程序对接文档")
					  .version("0.0.1")
					  .build()
					  );
		
		return docket;
	}
	
}
