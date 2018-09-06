package com.sx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2//http://127.0.0.1:8100/manager/swagger-ui.html
//@EnableSwagger2Doc // 开启 Swagger
@SpringBootApplication
@ImportResource({ "classpath:application_main.xml" })
public class DemoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}
}
