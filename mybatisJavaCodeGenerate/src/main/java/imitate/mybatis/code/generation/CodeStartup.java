package imitate.mybatis.code.generation;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import imitate.mybatis.code.generation.data.base.biz.ITableBiz;

@Configuration
@ComponentScan
@ImportResource({ "classpath:code.xml" })
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class CodeStartup {

	public static void main(String[] args) {//
		ConfigurableApplicationContext application = new SpringApplicationBuilder(CodeStartup.class)
				.web(WebApplicationType.NONE).run(args);
		ITableBiz tableBizImpl = (ITableBiz) application.getBean("tableBizImpl");
		tableBizImpl.createFiles();
	}

}
