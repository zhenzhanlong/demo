package imitate.mybatis.code.generation.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.FieldMethod;
import imitate.mybatis.code.generation.field.FieldOrderBy;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.field.PropertiesVO;
import imitate.mybatis.code.generation.reflection.DefaultReflectorFactory;
import imitate.mybatis.code.generation.reflection.ReflectorFactory;
import imitate.mybatis.code.generation.type.TypeAliasRegistry;
import imitate.mybatis.code.generation.type.TypeHandlerRegistry;

/**
 * 
 * @author zhenzhanlong
 *
 */
public class Configuration {
	/*** 日志 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 别名注册器
	protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
	// 类型处理注册器
	protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
	// 默认的反射器工厂,用于操作属性、构造器方便
	protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
	// settings下的properties属性
	protected Properties variables = new Properties();
	/** 解析后得到的，需要生成 代码的表数据信息 **/
	protected final List<CreateEntityFileConfig> needCreateEntityFileTableList = new ArrayList<>();
	/** 解析后得到的，需要生成 代码的表数据信息 **/
	Map<String, CreateEntityFileConfig> needCreateEntityFileTableMap = new HashMap<>(); 
	/** 解析后得到的，需要生成 代码的表数据信息 **/
	protected final List<DataBaseTable> needCreateFileTableList = new ArrayList<>();
	/** 表与字段关系 **/
	protected final Map<String, CreateEntityFileConfig> relationMap = new HashMap<>();
	/** 数据库实例名 **/
	private String mysqlInstance;
	/** 表名前缀 **/
	private String databaseNamePrefix;
	/** 有的时候只需要生成一个或者几个表的内容，没有必要每一次都新增 **/
	private boolean createTableLimit = false;
	/** 只生成配置的表的数据. true ,会将所有表文件生成 **/
	private final List<String> createTableNameList = new ArrayList<>();
	/** 字段使用驼峰 模式 **/
	private boolean attributeHump = false;
	/** 使用用户自己的配置 **/
	private boolean useConfigByUser = false;
	/** api 接口前缀 **/
	private String apiPrefix;
	/** class 类前缀 **/
	private String calssPrefix;

	/** 已经生成的文件集合，避免重复生成 **/
	protected final List<String> hasCreateFileList = new ArrayList<>();

	/** 获取不能更新的方法 **/
	protected final Map<String, FieldMethod> unUpdateFieldMap = new HashMap<>();

	/** 获取有自己query 方法的字段 **/
	protected final Map<String, FieldMethod> queryFieldMap = new HashMap<>();
	/** 获取有自己update 方法字段 **/
	protected final Map<String, FieldMethod> updateFieldMap = new HashMap<>();

	/** 获取自己有delete 方法字段 **/
	protected final Map<String, FieldMethod> deleteFieldMap = new HashMap<>();

	/** 获取枚举类型 字段 **/
	protected final Map<String, JavaField> enumFieldMap = new HashMap<>();

	/** 获取排序字段 **/
	protected final Map<String, FieldOrderBy> orderByFieldMap = new HashMap<>();
	
	/** result配置 **/
	protected final Map<String, PropertiesVO> resultMap = new HashMap<>();

	/** dao 方法名定义 **/
	protected final Properties daoMethod = new Properties();

	/** service 方法名定义 **/
	protected final Properties serviceMethod = new Properties();

	/** biz 方法名定义 **/
	protected final Properties bizMethod = new Properties();

	/** webService 方法名定义 **/
	protected final Properties webServiceMethod = new Properties();

	/** #BasePO,BaseVO,BaseForm 已经存在字段在po，vo，form里面去掉 **/
	protected final Set<String> baseClassExitsField = new HashSet<>();
	/** **/
	protected String pathPo;
	/** **/
	protected String pathVo;

	protected String pathForm;

	protected String pathDaoApi;

	protected String pathService;

	protected String pathMapper;

	protected String pathServiceImpl;

	protected String pathBiz;

	protected String pathBizImpl;

	protected String pathWebService;

	protected String pathWebServiceImpl;

	protected String pathController;

	protected String pathAction;

	protected String pathException;

	protected String packagePath;
	/** 代码生成后的路径 E:/enterprise/ **/
	protected String relativePath;

	public Configuration() {
	}
	
	public Map<String, PropertiesVO> getResultMap() {
		return resultMap;
	}

	public Map<String, CreateEntityFileConfig> getNeedCreateEntityFileTableMap() {
		return needCreateEntityFileTableMap;
	}

	public void setNeedCreateEntityFileTableMap(Map<String, CreateEntityFileConfig> needCreateEntityFileTableMap) {
		this.needCreateEntityFileTableMap = needCreateEntityFileTableMap;
	}

	public List<CreateEntityFileConfig> getNeedCreateEntityFileTableList() {
		return needCreateEntityFileTableList;
	}

	public Set<String> getBaseClassExitsField() {
		return baseClassExitsField;
	}

	public List<DataBaseTable> getNeedCreateFileTableList() {
		return needCreateFileTableList;
	}

	public List<String> getCreateTableNameList() {
		return createTableNameList;
	}

	public String getMysqlInstance() {
		return mysqlInstance;
	}

	public void setMysqlInstance(String mysqlInstance) {
		this.mysqlInstance = mysqlInstance;
	}
	
	public Map<String, CreateEntityFileConfig> getRelationMap() {
		return relationMap;
	}

	public String getDatabaseNamePrefix() {
		return databaseNamePrefix;
	}

	public void setDatabaseNamePrefix(String databaseNamePrefix) {
		this.databaseNamePrefix = databaseNamePrefix;
	}

	public boolean isCreateTableLimit() {
		return createTableLimit;
	}

	public void setCreateTableLimit(boolean createTableLimit) {
		this.createTableLimit = createTableLimit;
	}

	public boolean isAttributeHump() {
		return attributeHump;
	}

	public void setAttributeHump(boolean attributeHump) {
		this.attributeHump = attributeHump;
	}

	public boolean isUseConfigByUser() {
		return useConfigByUser;
	}

	public void setUseConfigByUser(boolean useConfigByUser) {
		this.useConfigByUser = useConfigByUser;
	}

	public String getApiPrefix() {
		return apiPrefix;
	}

	public void setApiPrefix(String apiPrefix) {
		this.apiPrefix = apiPrefix;
	}

	public String getCalssPrefix() {
		return calssPrefix;
	}

	public void setCalssPrefix(String calssPrefix) {
		this.calssPrefix = calssPrefix;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Properties getVariables() {
		return variables;
	}

	public void setVariables(Properties variables) {
		this.variables = variables;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getPathPo() {
		return pathPo;
	}

	public void setPathPo(String pathPo) {
		this.pathPo = pathPo;
	}

	public String getPathVo() {
		return pathVo;
	}

	public void setPathVo(String pathVo) {
		this.pathVo = pathVo;
	}

	public String getPathForm() {
		return pathForm;
	}

	public void setPathForm(String pathVorm) {
		this.pathForm = pathVorm;
	}

	public String getPathDaoApi() {
		return pathDaoApi;
	}

	public void setPathDaoApi(String pathDaoApi) {
		this.pathDaoApi = pathDaoApi;
	}

	public String getPathService() {
		return pathService;
	}

	public void setPathService(String pathService) {
		this.pathService = pathService;
	}

	public String getPathMapper() {
		return pathMapper;
	}

	public void setPathMapper(String pathMapper) {
		this.pathMapper = pathMapper;
	}

	public String getPathServiceImpl() {
		return pathServiceImpl;
	}

	public void setPathServiceImpl(String pathServiceImpl) {
		this.pathServiceImpl = pathServiceImpl;
	}

	public String getPathBiz() {
		return pathBiz;
	}

	public void setPathBiz(String pathBiz) {
		this.pathBiz = pathBiz;
	}

	public String getPathBizImpl() {
		return pathBizImpl;
	}

	public void setPathBizImpl(String pathBizImpl) {
		this.pathBizImpl = pathBizImpl;
	}

	public String getPathWebService() {
		return pathWebService;
	}

	public void setPathWebService(String pathWebService) {
		this.pathWebService = pathWebService;
	}

	public String getPathWebServiceImpl() {
		return pathWebServiceImpl;
	}

	public void setPathWebServiceImpl(String pathWebServiceImpl) {
		this.pathWebServiceImpl = pathWebServiceImpl;
	}

	public String getPathController() {
		return pathController;
	}

	public void setPathController(String pathController) {
		this.pathController = pathController;
	}

	public String getPathAction() {
		return pathAction;
	}

	public void setPathAction(String pathAction) {
		this.pathAction = pathAction;
	}

	public String getPathException() {
		return pathException;
	}

	public void setPathException(String pathException) {
		this.pathException = pathException;
	}

	public Map<String, FieldMethod> getUnUpdateFieldMap() {
		return unUpdateFieldMap;
	}

	public Map<String, FieldMethod> getQueryFieldMap() {
		return queryFieldMap;
	}

	public Map<String, FieldMethod> getUpdateFieldMap() {
		return updateFieldMap;
	}

	public Map<String, FieldMethod> getDeleteFieldMap() {
		return deleteFieldMap;
	}

	public Map<String, JavaField> getEnumFieldMap() {
		return enumFieldMap;
	}

	public Map<String, FieldOrderBy> getOrderByFieldMap() {
		return orderByFieldMap;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<String> getHasCreateFileList() {
		return hasCreateFileList;
	}

	public TypeAliasRegistry getTypeAliasRegistry() {
		return typeAliasRegistry;
	}

	public TypeHandlerRegistry getTypeHandlerRegistry() {
		return typeHandlerRegistry;
	}

	public ReflectorFactory getReflectorFactory() {
		return reflectorFactory;
	}

	public void setReflectorFactory(ReflectorFactory reflectorFactory) {
		this.reflectorFactory = reflectorFactory;
	}

	public Properties getDaoMethod() {
		return daoMethod;
	}

	public Properties getServiceMethod() {
		return serviceMethod;
	}

	public Properties getBizMethod() {
		return bizMethod;
	}

	public Properties getWebServiceMethod() {
		return webServiceMethod;
	}

}
