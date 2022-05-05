package com.virginatlantic.myflightdata;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyflightdataApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyflightdataApplication.class, args);
	}



	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {

		return new OpenAPI().info(new Info()
				.title("Flight Data API")
				.version(appVersion)
				.description(appDesciption)
				.termsOfService("http://flightdata.com/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));

	}

}
