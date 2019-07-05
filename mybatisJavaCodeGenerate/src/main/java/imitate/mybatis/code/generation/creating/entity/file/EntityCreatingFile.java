package imitate.mybatis.code.generation.creating.entity.file;

import java.util.List;

import imitate.mybatis.code.generation.creating.BaseCreatingFile;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.type.CreateFileType;

/**
 * po类文件生成类
 * 
 * @author Administrator
 *
 */
public abstract class EntityCreatingFile extends BaseCreatingFile {

	protected CreateEntityFileConfig fileConfig;

	public EntityCreatingFile(Configuration configuration) {
		super(configuration);
	}

	public CreateEntityFileConfig getFileConfig() {
		return fileConfig;
	}

	public void setFileConfig(CreateEntityFileConfig fileConfig) {
		this.fileConfig = fileConfig;
	}

	// /**
	// * 生成文件
	// *
	// * @param path
	// * @param fileName
	// * @param content
	// * @return
	// */
	// public void createFile(String packagePath, String entityName, String content)
	// {
	// String path = configuration.getRelativePath();
	// if (!path.endsWith(PATH_SEPARATOR)) {
	// path = path + PATH_SEPARATOR;
	// }
	// path = path + packagePath;
	// path = path.replace(".", PATH_SEPARATOR);
	// // 生成目录
	// File filePath = new File(path);
	// if (!filePath.exists()) {
	// filePath.mkdirs();
	// }
	// String fileName = path + PATH_SEPARATOR + entityName;
	// // 生成文件
	// this.createFile(fileName, content);
	// }

	public void createFiles() {
		List<CreateEntityFileConfig> fileList = this.configuration.getNeedCreateEntityFileTableList();
		fileList.stream().forEach(this::createFile);

	}

	// 文件生成
	public void createFile(CreateEntityFileConfig fileConfig) {
		this.fileConfig = fileConfig;
		// 清空以前的文件内容
		this.getContent().delete(0, this.getContent().length());
		// 生成文件体
		createContent(fileConfig);
		// 生成文件
		createFile();
	}

	public void createContent(CreateEntityFileConfig fileConfig) {
		List<JavaField> fieldList = fileConfig.getJavaFieldList();

		//
		// 文件头部 package，import 部分
		this.content.append(classHead());
		// 字段、属性定义、文件开通
		StringBuilder attribute = new StringBuilder();
		// 类说明， basePO部分 String tableComment, String entityName, String extendEntity
		attribute.append(classBegin());

		// get、set方法
		StringBuilder methodStr = new StringBuilder();
		// 类引入
		StringBuilder importStr = new StringBuilder();
		for (JavaField fieldVO : fieldList) {
			// 父类有，不需要生成get、set方法，属性引用
			if (this.configuration.getBaseClassExitsField().contains(fieldVO.getColumnName())) {
				continue;
			}
			// 是否是枚举字段,如果以后要写 import 枚举类时，才有用
			JavaField enumJavaField = this.configuration.getEnumFieldMap().get(fieldVO.getColumnName());
			if (null != enumJavaField) {
				importStr.append("import ").append(fieldVO.getClassPath()).append(";\n");
			}
			attribute.append(attribute(fieldVO));
			methodStr.append(getSetMethod(fieldVO));

		}
		// 拼接为返回str
		this.content.append(importStr).append(attribute).append(" \n\n");
		// 构造方法
		this.content.append(constractor());
		// // 拼接get，set方法
		this.content.append(methodStr)
				// 文件末位的追加
				.append(classEnd())
				// toString
				.append(toString())
				// 类结束
				.append("} //end class")
				// 类后面追加的，可能是说明之类的
				.append(classAfterAppend());

	}

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

	// 解析 生成文件
	public abstract void createFile();

	// 文件头部分
	public abstract String classHead();

	// 文件开始部分部分
	public abstract String classBegin();

	// 构造函数部分
	public abstract String constractor();

	// 字段信息转换为 属性声明信息
	public abstract String attribute(JavaField fieldVO);

	// 字段信息转换为 属性声明信息
	public abstract String getSetMethod(JavaField fieldVO);

	// 类末位部分
	public abstract String classEnd();

	// 构造函数部分
	public abstract String toString();

	// class类后面追加内容
	public abstract String classAfterAppend();

}
