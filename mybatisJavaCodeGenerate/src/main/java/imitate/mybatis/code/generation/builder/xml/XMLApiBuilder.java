/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package imitate.mybatis.code.generation.builder.xml;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.builder.BaseBuilder;
import imitate.mybatis.code.generation.builder.BuilderException;
import imitate.mybatis.code.generation.field.CreateIApiFileConfig;
import imitate.mybatis.code.generation.parsing.XNode;
import imitate.mybatis.code.generation.parsing.XPathParser;
import imitate.mybatis.code.generation.session.Configuration;

/**
 * xml 映射生成器
 * 
 * @author Clinton Begin
 */
public class XMLApiBuilder<T> extends BaseBuilder {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/** 是否解析过了 也就是mybatis每次启动，只能解析一次 **/
	private boolean parsed;
	// path 解析
	private final XPathParser parser;

	//
	public XMLApiBuilder(Configuration configuration,Reader reader) {
		this(configuration,reader, null);
	}

	public XMLApiBuilder(Configuration configuration,Reader reader, Properties props) {
		this(configuration,new XPathParser(reader, true, props, new XMLConfigEntityResolver()), props);
	}

	public XMLApiBuilder(Configuration configuration,InputStream inputStream) {
		this(configuration,inputStream, null);
	}

	public XMLApiBuilder(Configuration configuration,InputStream inputStream, Properties props) {
		this(configuration,new XPathParser(inputStream, true, props, new XMLConfigEntityResolver()), props);
	}

	private XMLApiBuilder(Configuration configuration,XPathParser parser, Properties props) {
		super(configuration);
		ErrorContext.instance().resource("api Mapper Configuration");
		this.configuration.setVariables(props);
		this.parsed = false;
		this.parser = parser;
	}

	public CreateIApiFileConfig parse() {
		if (parsed) {
			throw new BuilderException("Each XMLConfigBuilder can only be used once.");
		}
		parsed = true;
		return parseConfiguration(parser.evalNode("/mapper"));
	}

	private CreateIApiFileConfig parseConfiguration(XNode root) {
		CreateIApiFileConfig apiFile = new CreateIApiFileConfig(this.configuration);
		try {
			// 解析mapper文件，替换文件内的表示
			String fileType = root.getStringAttribute("fileType");
			log.info("解析xml文件类型fileType={}", fileType);
			XNode fileBody = root.evalNode("bodyCode");
	
			XNode queryField = root.evalNode("queryField");
			XNode updateField = root.evalNode("updateField");
			XNode deleteField = root.evalNode("deleteField");
			XNode orderByField = root.evalNode("orderByField");
			//数据进入配置类
			apiFile.setFileBody(fileBody);
			apiFile.setQueryField(queryField);
			apiFile.setUpdateField(updateField);
			apiFile.setDeleteField(deleteField);
			apiFile.setOrderByField(orderByField);
			
			/*
			 * System.out.println(bodyStr.getStringBody()); GenericTokenParser parser = new
			 * GenericTokenParser("","", ApiXmlFileParser.parse(bodyStr.getStringBody(),
			 * variables));
			 */
		} catch (Exception e) {
			log.error(" api文件解析异常error={} ", e);
		}
		return apiFile;
	}
//	public T parse(T t) {
//		if (parsed) {
//			throw new BuilderException("Each XMLConfigBuilder can only be used once.");
//		}
//		parsed = true;
//		return parseConfiguration1(t,parser.evalNode("/mapper"));
//	}
//
//	private T parseConfiguration1(T t,XNode root) {
//		
//		T apiFile = new CreateIApiFileConfig(this.configuration);
//		try {
//			// 解析mapper文件，替换文件内的表示
//			String fileType = root.getStringAttribute("fileType");
//			log.info("解析xml文件类型fileType={}", fileType);
//			XNode fileBody = root.evalNode("bodyCode");
//			XNode queryField = root.evalNode("queryField");
//			XNode updateField = root.evalNode("updateField");
//			XNode deleteField = root.evalNode("deleteField");
//			XNode orderByField = root.evalNode("orderByField");
//			//数据进入配置类
//			apiFile.setFileBody(fileBody);
//			apiFile.setQueryField(queryField);
//			apiFile.setUpdateField(updateField);
//			apiFile.setDeleteField(deleteField);
//			apiFile.setOrderByField(orderByField);
//			/*
//			 * System.out.println(bodyStr.getStringBody()); GenericTokenParser parser = new
//			 * GenericTokenParser("","", ApiXmlFileParser.parse(bodyStr.getStringBody(),
//			 * variables));
//			 */
//		} catch (Exception e) {
//			log.error(" api文件解析异常error={} ", e);
//		}
//		return apiFile;
//	}
}
