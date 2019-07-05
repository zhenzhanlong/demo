package imitate.mybatis.code.generation.creating.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.builder.xml.XMLApiBuilder;
import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.CreateIApiFileConfig;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.io.Resources;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.util.FieldUtil;

/**
 * xml 文件生成 生成类
 * 
 * @author Administrator
 *
 * 
 */
public class XmlFileCreating extends XmlCreatingFile {

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

	public XmlFileCreating(Configuration configuration) {
		super(configuration);
	}

	public XmlFileCreating(CreateIApiFileConfig apiFileConfig) {
		super(apiFileConfig.getConfiguration());
		this.apiFileConfig = apiFileConfig;
	}

	// 文件生成
	@Override
	public void createFile() {
		// 生成文件
		super.createFile(this.apiFileConfig.getMapperPackage(), this.apiFileConfig.getMapperFileName(),
				content.toString());
	}

	@Override
	public void analysisProperties() {
		super.analysisProperties();
		// 方法名称的配置关系加入集合
		pro.putAll(this.configuration.getDaoMethod());
		///解析 mapper自己需要的属性
		analysisMapperProperties();
	}

	// 解析xml配置
	@Override
	public void analysisImplFile() {
		XMLApiBuilder apiBuilder = null;
		try (Reader reader = Resources.getResourceAsReader("imitate/mybatis/code/generation/mapping/xml/Mapper.xml")) {
			apiBuilder = new XMLApiBuilder(this.configuration, reader);
			this.apiFileConfig = apiBuilder.parse();
			log.debug("解析serviceImpl 层xml完成");
		} catch (IOException e) {
			log.error("解析、生成api文件异常error={}", e);
		}
	}

	public void analysisMapperProperties() {
		DataBaseTable table = this.apiFileConfig.getTable();
		CreateEntityFileConfig fileConfig = this.configuration.getNeedCreateEntityFileTableMap()
				.get(table.getTable_name());
		List<JavaField> fieldList = fileConfig.getJavaFieldList();
		//poMap，mapperName自动生成
		// mapper的类名
				String mapperName = this.apiFileConfig.getMapperName();
		// select 方法字段
		pro.put("queryFields", FieldUtil.queryFields(fieldList));
		// 通用 where Sql名称
		pro.put("daoWhereSql", mapperName + "WhereAndSql");
		// 通用selectSql名称
		pro.put("daoSelectSql", mapperName + "SelectSql");
		// where条件字段
		pro.put("daoWhereField", FieldUtil.daoWhereField(fieldList));
		///resultMap 的java字段与数据库字段对应关系
		pro.put("resumtMapResults", FieldUtil.resumtMapResults(fieldList));
		// update 方法字段
		pro.put("updateFields", FieldUtil.updateFields(fieldList));

	}

}
