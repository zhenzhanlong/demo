package imitate.mybatis.code.generation.field;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.type.Constants;
import imitate.mybatis.code.generation.type.FileNameEnumType;
import imitate.mybatis.code.generation.util.NameUtil;

/**
 * 生成文件类
 * 
 * @author Administrator
 *
 */
public class CreateEntityFileConfig {
	// 配置类
	private Configuration configuration;
	/**
	 * 数据库表信息
	 */
	private DataBaseTable table;
	/**
	 * java 字段信息
	 */
	private List<JavaField> javaFieldList;
	/**
	 * 文件内容
	 */
	private String content;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 文件名称
	 */
	private String fileName;

	private String mapperName;
	/** 获取处理后的数据库表名 **/
	private String tableNameHandle;

	public CreateEntityFileConfig() {
		super();
	}

	public CreateEntityFileConfig(DataBaseTable table, Configuration configuration) {
		super();
		this.table = table;
		this.configuration = configuration;
	}
	public CreateEntityFileConfig(String tableName, Configuration configuration) {
		super();
		this.configuration = configuration;
		this.tableNameHandle = NameUtil.subTableName(tableName, configuration.getDatabaseNamePrefix());
	}

	public String getTableNameHandle() {
		if (StringUtils.isBlank(tableNameHandle)) {
			tableNameHandle = NameUtil.subTableName(table.getTable_name(), configuration.getDatabaseNamePrefix());
		}
		return tableNameHandle;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getMapperName() {
		if (StringUtils.isBlank(mapperName) && null != this.table) {
			mapperName = NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
					FileNameEnumType.MAPPER_MAP_NAME);
		}
		return mapperName;
	}

	/********************* po *********************/
	public String getPoName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.PO_NAME);
	}

	public String getPoFileName() {
		return getPoName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getPoPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathPo());
		return str.toString();
	}

	public String getPOImport() {
		StringBuilder str = new StringBuilder();
		str.append(getPoPackage()).append(".").append(this.getPoName());
		return str.toString();
	}

	/********************* vo *********************/

	public String getVoName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.VO_NAME);
	}

	public String getVoFileName() {
		return getVoName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getVoPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathVo());
		return str.toString();
	}

	public String getVoImport() {
		StringBuilder str = new StringBuilder();
		str.append(getVoPackage()).append(".").append(this.getVoName());
		return str.toString();
	}

	/********************* form *********************/

	public String getFormName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.FORM_NAME);
	}

	public String getFormFileName() {
		return getFormName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getFormPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathForm());
		return str.toString();
	}

	public String getFormImport() {
		StringBuilder str = new StringBuilder();
		str.append(getFormPackage()).append(".").append(this.getFormName());
		return str.toString();
	}
	/** 路径前缀 **/
	private String getPackagePath() {
		StringBuilder str = new StringBuilder();
		str.append(this.configuration.getPackagePath());
		if (!str.toString().endsWith(".")) {
			str.append(".");
		}
		return str.toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}

	public List<JavaField> getJavaFieldList() {
		return javaFieldList;
	}

	public void setJavaFieldList(List<JavaField> javaFieldList) {
		this.javaFieldList = javaFieldList;
	}

	public DataBaseTable getTable() {
		return table;
	}

	public void setTable(DataBaseTable table) {
		this.table = table;
	}

}
