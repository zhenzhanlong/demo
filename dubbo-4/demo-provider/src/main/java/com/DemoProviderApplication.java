package com;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.dubbo.container.Main;

/*@SpringBootApplication*/
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
@ImportResource({ "classpath:application_main.xml" })
public class DemoProviderApplication {

	public static void main(String[] args) {
		//web端启动方式
		//SpringApplication.run(DemoProviderApplication.class,args);
		
		//provider的启动方式
		new SpringApplicationBuilder(DemoProviderApplication.class).web(WebApplicationType.NONE).run(args);
		Main.main(args);
	}
}
