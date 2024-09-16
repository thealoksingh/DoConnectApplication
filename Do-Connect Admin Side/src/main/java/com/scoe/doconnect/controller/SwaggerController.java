package com.scoe.doconnect.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // project settings
@EnableSwagger2 // activate swagger file in the background
public class SwaggerController {

	@Bean // from the start of project running to the end
	public Docket libraryApi() // pluggable

	{

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).groupName("DoConnect Admin API").select()
				.apis(RequestHandlerSelectors.basePackage("com.scoe.doconnect.controller")) // whose methods do you want to
																						// add
				.build().securitySchemes(Arrays.asList(apiKey())).securityContexts(Arrays.asList(securityContext()))
				.apiInfo(apiInfo()).pathMapping("/").useDefaultResponseMessages(false)
				.directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class);

	}

	private ApiInfo apiInfo() { // used this object to store the meta data

		return new ApiInfoBuilder().title("DoConnect Admin API").description("DoConnect testing controller APIs")
				.license("Company License").licenseUrl("http://www.yahoo.in")
				.termsOfServiceUrl("http://www.google.co.in").build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		var authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
}
