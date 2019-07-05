package imitate.mybatis.code.generation.creating.controller;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.builder.xml.XMLApiBuilder;
import imitate.mybatis.code.generation.field.CreateIApiFileConfig;
import imitate.mybatis.code.generation.io.Resources;
import imitate.mybatis.code.generation.session.Configuration;

/**
 * web端 service层 生成类
 * 
 * @author Administrator
 *
 * 
 */
public class ControllerFileCreating extends ServletCreatingFile {

	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Properties getPro() {
		return pro;
	}

	public ControllerFileCreating(Configuration configuration) {
		super(configuration);
	}

	public ControllerFileCreating(CreateIApiFileConfig apiFileConfig) {
		super(apiFileConfig.getConfiguration());
		this.apiFileConfig = apiFileConfig;
	}

	// 文件生成
	@Override
	public void createFile() {
		// 生成文件
		super.createFile(this.apiFileConfig.getControllerPackage(), this.apiFileConfig.getControllerFileName(),
				content.toString());
	}

	@Override
	public void analysisProperties() {
		super.analysisProperties();
		// 方法名称的配置关系加入集合
		pro.putAll(this.configuration.getWebServiceMethod());
	}

	// 解析xml配置
	@Override
	public void analysisServletFile() {
		XMLApiBuilder apiBuilder = null;
		try (Reader reader = Resources
				.getResourceAsReader("imitate/mybatis/code/generation/mapping/xml/controller.xml")) {
			apiBuilder = new XMLApiBuilder(this.configuration,reader);
			this.apiFileConfig = apiBuilder.parse();
			log.debug("解析web端service Impl 层xml完成");
		} catch (IOException e) {
			log.error("解析、生成api文件异常error={}", e);
		}
	}
}
