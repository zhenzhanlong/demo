package imitate.mybatis.code.generation.field;

import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.parsing.XNode;
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
public class CreateIApiFileConfig {
	// 配置类
	protected Configuration configuration;
	/**
	 * 数据库表信息
	 */
	protected DataBaseTable table;
	/**
	 * 文件内容
	 */
	protected XNode fileBody;
	/**
	 * 有自己query方法
	 */
	protected XNode queryField;
	/**
	 * 有自己update方法
	 */
	protected XNode updateField;
	/**
	 * 有自己delete方法
	 */
	protected XNode deleteField;
	/**
	 * 有自己orderBy方法
	 */
	protected XNode orderByField;
	/**
	 * 文件路径
	 */
	protected String filePath;
	/**
	 * 文件名称
	 */
	protected String fileName;

	/** 获取处理后的数据库表名 **/
	protected String tableNameHandle;

	/** 表说明 **/
	protected String table_comment;

	public CreateIApiFileConfig() {
		super();
	}
	public CreateIApiFileConfig(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public CreateIApiFileConfig(XNode fileBody, Configuration configuration) {
		super();
		this.fileBody = fileBody;
		this.configuration = configuration;
	}

	public String getTable_comment() {
		if (null != table) {
			table_comment = table.getTable_comment();
		}
		return table_comment;
	}

	public void setTable_comment(String table_comment) {
		this.table_comment = table_comment;
	}

	public String getTableNameHandle() {
		if (null != table) {
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

	public String getPoImport() {
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

	/************** dao ********************/
	public String getDaoName() {
		return NameUtil.createClassName(this.configuration.getApiPrefix(), getTableNameHandle(),
				FileNameEnumType.DAO_API_NAME);
	}

	public String getDaoFileName() {
		return getDaoName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getDaoPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathDaoApi());
		return str.toString();
	}

	public String getDaoImport() {
		StringBuilder str = new StringBuilder();
		str.append(getDaoPackage()).append(".").append(this.getDaoName());
		return str.toString();
	}

	public String getDaoParam() {
		String daoParam = NameUtil.createClassName("", getTableNameHandle(), FileNameEnumType.DAO_API_NAME);
		return NameUtil.firstCharacterToLower(daoParam);
	}

	/************************** service *********************/
	public String getServiceName() {
		return NameUtil.createClassName(this.configuration.getApiPrefix(), getTableNameHandle(),
				FileNameEnumType.SERVICE_API_NAME);
	}

	public String getServiceFileName() {
		return getServiceName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getServicePackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathService());
		return str.toString();
	}

	public String getServiceImport() {
		StringBuilder str = new StringBuilder();
		str.append(getServicePackage()).append(".").append(this.getServiceName());
		return str.toString();
	}

	/************************** service impl *********************/

	public String getServiceImplName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.SERVICE_NAME);
	}

	public String getServiceImplFileName() {
		return getServiceImplName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getServiceImplPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathServiceImpl());
		return str.toString();
	}

	public String getServiceImplImport() {
		StringBuilder str = new StringBuilder();
		str.append(getServiceImplPackage()).append(".").append(this.getServiceName());
		return str.toString();
	}

	public String getServiceImplParam() {
		return NameUtil.firstCharacterToLower(getServiceImplName());
	}

	/************************** biz *********************/
	public String getBizName() {
		return NameUtil.createClassName(this.configuration.getApiPrefix(), getTableNameHandle(),
				FileNameEnumType.BIZ_API_NAME);
	}

	public String getBizFileName() {
		return getBizName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getBizPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathBiz());
		return str.toString();
	}

	public String getBizImport() {
		StringBuilder str = new StringBuilder();
		str.append(getBizPackage()).append(".").append(this.getBizName());
		return str.toString();
	}

	/************************** biz impl *********************/
	public String getBizImplName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.BIZ_NAME);
	}

	public String getBizImplFileName() {
		return getBizImplName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getBizImplPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathBizImpl());
		return str.toString();
	}

	public String getBizImplImport() {
		StringBuilder str = new StringBuilder();
		str.append(getBizImplPackage()).append(".").append(this.getBizName());
		return str.toString();
	}

	public String getBizImplParam() {
		return NameUtil.firstCharacterToLower(getBizImplName());
	}

	/************************** web service *********************/

	public String getWebServiceName() {
		return NameUtil.createClassName(this.configuration.getApiPrefix(), getTableNameHandle(),
				FileNameEnumType.WEB_SERVICE_API_NAME);
	}

	public String gettWebServiceFileName() {
		return getWebServiceName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getWebServicePackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathWebService());
		return str.toString();
	}

	public String getWebServiceImport() {
		StringBuilder str = new StringBuilder();
		str.append(getWebServicePackage()).append(".").append(this.getWebServiceName());
		return str.toString();
	}

	/************************** web service impl *********************/

	public String getWebServiceImplName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.WEB_SERVICE_NAME);
	}

	public String getWebServiceImplFileName() {
		return getWebServiceImplName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getWebServiceImplPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathWebServiceImpl());
		return str.toString();
	}

	public String getWebServiceImplImport() {
		StringBuilder str = new StringBuilder();
		str.append(getWebServiceImplPackage()).append(".").append(this.getWebServiceImplName());
		return str.toString();
	}

	public String getWebServiceImplParam() {
		return NameUtil.firstCharacterToLower(getWebServiceImplName());
	}

	/************************** controller *********************/

	public String getControllerName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.CONTROLLER_NAME);
	}

	public String getControllerFileName() {
		return getControllerName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getControllerPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathController());
		return str.toString();
	}

	/************************** action *********************/
	public String getActionName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.ACTION_NAME);
	}

	public String getActionFileName() {
		return getActionName() + Constants.JAVA_FILE_SUFFIX;
	}

	public String getActionPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathAction());
		return str.toString();
	}

	/** 路径前缀 **/
	protected String getPackagePath() {
		StringBuilder str = new StringBuilder();
		str.append(this.configuration.getPackagePath());
		if (!str.toString().endsWith(".")) {
			str.append(".");
		}
		return str.toString();
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

	public DataBaseTable getTable() {
		return table;
	}

	public void setTable(DataBaseTable table) {
		this.table = table;
	}

	public void setTableNameHandle(String tableNameHandle) {
		this.tableNameHandle = tableNameHandle;
	}

	public XNode getFileBody() {
		return fileBody;
	}

	public void setFileBody(XNode fileBody) {
		this.fileBody = fileBody;
	}

	public XNode getQueryField() {
		return queryField;
	}

	public void setQueryField(XNode queryField) {
		this.queryField = queryField;
	}

	public XNode getUpdateField() {
		return updateField;
	}

	public void setUpdateField(XNode updateField) {
		this.updateField = updateField;
	}

	public XNode getDeleteField() {
		return deleteField;
	}

	public void setDeleteField(XNode deleteField) {
		this.deleteField = deleteField;
	}

	public XNode getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(XNode orderByField) {
		this.orderByField = orderByField;
	}
	/********************* mapper *********************/
	public String getMapperName() {
		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
				FileNameEnumType.MAPPER_NAME);
	}

	public String getMapperFileName() {
		return getMapperName() + Constants.MAPPER_FILE_SUFFIX;
	}

	public String getMapperPackage() {
		StringBuilder str = new StringBuilder();
		str.append(getPackagePath()).append(this.configuration.getPathMapper());
		return str.toString();
	}
	
	/********************* resultMap *********************/
	public String getResultMapName() {
		StringBuilder name = new StringBuilder();
		name.append(this.configuration.getCalssPrefix()).append(getTableNameHandle()).append("Map");
		return name.toString();
	}
}
