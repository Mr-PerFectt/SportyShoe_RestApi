package com.mrperfect.shoestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class SportyShoeRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportyShoeRestApiApplication.class, args);
	}
	
	
//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.mrperfect.shoestore"))
//				.build();
//	}

}
