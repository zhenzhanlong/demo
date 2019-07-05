package imitate.mybatis.code.generation.creating.entity.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.builder.xml.XMLApiBuilder;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.CreateIApiFileConfig;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.io.Resources;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.type.CreateFileType;
import imitate.mybatis.code.generation.util.FieldUtil;

/**
 * service层 api生成类
 * 
 * @author Administrator
 *
 * 
 */
public class POByXmlFileCreating extends EntityByXmlCreatingFile {

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

	public POByXmlFileCreating(Configuration configuration) {
		super(configuration);
	}

	public POByXmlFileCreating(CreateIApiFileConfig apiFileConfig) {
		super(apiFileConfig.getConfiguration());
		this.apiFileConfig = apiFileConfig;
	}

	// 文件生成
	@Override
	public void createFile() {
		// 生成文件
		super.createFile(this.apiFileConfig.getPoPackage(), this.apiFileConfig.getPoFileName(), content.toString());
	}

	@Override
	public void analysisProperties() {
		// 获取实体配置
		CreateEntityFileConfig fileConfig = this.getConfiguration().getNeedCreateEntityFileTableMap()
				.get(this.apiFileConfig.getTable().getTable_name());
		// 获取字段
		List<JavaField> fieldList = fileConfig.getJavaFieldList();

		super.analysisProperties();
		// 方法名称的配置关系加入集合
		pro.put("serialVersionUID", String.valueOf(System.currentTimeMillis()));

		pro.put("attributeFields", FieldUtil.attribute(fieldList, CreateFileType.PO));

		pro.put("constractor", constractorContent(fileConfig.getPoName(), fieldList.get(0)));

		pro.put("getSetMethod", FieldUtil.getSetMethod(fieldList, CreateFileType.PO));
	}
	// 解析xml配置
	@Override
	public void analysisImplFile() {
		XMLApiBuilder apiBuilder = null;
		try (Reader reader = Resources
				.getResourceAsReader("imitate/mybatis/code/generation/mapping/xml/entity/POEntity.xml")) {
			apiBuilder = new XMLApiBuilder(this.configuration, reader);
			this.apiFileConfig = apiBuilder.parse();
			log.debug("解析 po 层xml完成");
		} catch (IOException e) {
			log.error("解析、生成po文件异常error={}", e);
		}
	}
}
