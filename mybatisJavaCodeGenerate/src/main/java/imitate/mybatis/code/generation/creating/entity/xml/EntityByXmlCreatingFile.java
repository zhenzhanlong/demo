package imitate.mybatis.code.generation.creating.entity.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.creating.BaseCreatingFile;
import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.CreateIApiFileConfig;
import imitate.mybatis.code.generation.field.FieldMethod;
import imitate.mybatis.code.generation.field.FieldOrderBy;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.field.PropertiesVO;
import imitate.mybatis.code.generation.parsing.ApiXmlFileParser;
import imitate.mybatis.code.generation.parsing.XNode;
import imitate.mybatis.code.generation.reflection.DefaultReflectorFactory;
import imitate.mybatis.code.generation.reflection.MetaClass;
import imitate.mybatis.code.generation.reflection.invoker.Invoker;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.type.CreateFileType;
import imitate.mybatis.code.generation.util.NameUtil;

/**
 * impl 类文件生成类
 * 
 * @author Administrator
 *
 */
public abstract class EntityByXmlCreatingFile extends BaseCreatingFile {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	// 不能用  ServiceImplFileCreatingy， 因为  XMLApiBuilder 解析返回的是固定的，除非
	//在写一个 XMLImplBuilder
	protected CreateIApiFileConfig apiFileConfig;

	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	public CreateIApiFileConfig getApiFileConfig() {
		return apiFileConfig;
	}

	public void setApiFileConfig(CreateIApiFileConfig apiFileConfig) {
		this.apiFileConfig = apiFileConfig;
	}

	public EntityByXmlCreatingFile(Configuration configuration) {
		super(configuration);
	}

	public void createEntityFileProcess() {
		// 第一步解析。要生成的文件的xml的配置
		analysisImplFile();
		// 第二步 循环生成内容、文件
		List<DataBaseTable> fileList = this.configuration.getNeedCreateFileTableList();
		fileList.stream().forEach(this::createContent);
		//

		//
	}

	// 文件生成
	public void createContent(DataBaseTable table) {
		this.apiFileConfig.setTable(table);

		// 解析 properties 文件，IDao,IService,方法名、报名等都需要自己解析才能使用
		analysisProperties();
		// 删除上一个文件的内容
		content.delete(0, content.length());
		// 生成文件主题
		// StringBuilder content = new StringBuilder();
		String bodyCodeContent = createContentBodyCode();
		content.append(bodyCodeContent);
		// 生成文件
		createFile();
	}

	public String createContentBodyCode() {
		XNode fileBody = this.apiFileConfig.getFileBody();
		return ApiXmlFileParser.parse(fileBody.getStringBody(), pro);
	}

	// 解析 properties 参数
	public void analysisProperties() {
		createProperties(this.apiFileConfig);
	}

	public void createProperties(Object paramObj) {
		// XNode fileBody = this.apiFileConfig.getFileBody();
		// 清空所有配置
		pro.clear();
		// result 配置进入配置
		Map<String, PropertiesVO> proMap = this.configuration.getResultMap();
		proMap.keySet().stream().forEach(proKey -> {
			pro.put(proMap.get(proKey).getName(), proMap.get(proKey).getValue());
		});

		// 所有参数进入 Properties，解析xml文件使用
		MetaClass metaClass = MetaClass.forClass(paramObj.getClass(), new DefaultReflectorFactory());
		String[] getterNames = metaClass.getGetterNames();
		Invoker method = null;
		for (String getterName : getterNames) {
			log.debug("getterName={}", getterName);
			method = metaClass.getGetInvoker(getterName);
			try {
				Object obj = method.invoke(paramObj, NO_ARGUMENTS);
				if (null != obj) {
					pro.put(getterName, obj.toString());
				}
			} catch (IllegalAccessException e) {
				log.error("权限异常e={}", e);
			} catch (InvocationTargetException e) {
				log.error("反射调用异常e={}", e);
			}
		}
		System.out.println("1");
	}

	// 解析xml配置
	public abstract void analysisImplFile();

	// 解析 生成文件
	public abstract void createFile();
	/** entity 类开始部分 **/
	public static String classBeginContent(String tableComment, String entityName, String extendEntity) {
		StringBuilder attribute = new StringBuilder();
		attribute.append("/**").append(tableComment).append("**/ \n ")
				.append(" @ApiModel(description = \"").append(tableComment).append("\")")
				.append("public class ").append(entityName)
				.append(" extends ").append(extendEntity).append("  implements Serializable { \n")
				.append("	 /** 序列化 */\n").append("	 private static final long serialVersionUID = ")
				.append(System.currentTimeMillis()).append("L;\n");
		return attribute.toString();
	}

	/** 构造方法 **/// CreateFileConfig fileConfig
	public String constractorContent(String entityName, JavaField field) {
		StringBuilder attribute = new StringBuilder();
		/** 构造方法 **/
		attribute.append("	public ").append(entityName).append("(){ \n").append("		super() ; \n").append("	} \n");
		/** 构造方法 **/
		attribute.append("	public ").append(entityName).append("(").append(field.getJavaType()).append("  ")
				.append(field.getJavaName()).append(" ){\n").append("		super() ; \n").append("		this.")
				.append(field.getJavaName().toLowerCase()).append(" = ").append(field.getJavaName().toLowerCase())
				.append(" ;\n").append("	} \n");
		return attribute.toString();
	}

	/** 文件头部 **/
	public static String headContent(String pachagePath, CreateFileType fileType) {
		StringBuilder importStr = new StringBuilder();
		importStr.append("package ").append(pachagePath).append("; \n").append("import java.io.Serializable; \n")
				.append("import com.sx.common.util.json.JackJsonUtil; \n")
				.append("import com.fasterxml.jackson.annotation.JsonInclude; \n ");
		if (CreateFileType.FORM.equals(fileType) || CreateFileType.VO.equals(fileType)) {
			importStr.append("import com.sx.common.util.BeanUtils; \n")
					.append("import io.swagger.annotations.ApiModel; \n ")
					.append("import io.swagger.annotations.ApiModelProperty;\n");
		}
		if (CreateFileType.FORM.equals(fileType)) {
			importStr.append("import org.apache.commons.lang3.StringUtils; \n");
		}

		return importStr.toString();
	}

	/** toString 方法 **/
	public String toStringContent(String entityName) {
		StringBuilder str = new StringBuilder();
		str.append("@Override \n ").append("public String toString() { \n ").append("return \"").append(entityName)
				.append(":\"+JackJsonUtil.objToJson(this); \n ").append(" } \n ");
		return str.toString();
	}

	



}
