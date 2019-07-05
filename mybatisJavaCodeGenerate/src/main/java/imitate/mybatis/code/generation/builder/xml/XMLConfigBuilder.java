/**
 *    Copyright 2009-2017 the original author or authors.
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.executor.ErrorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.builder.BaseBuilder;
import imitate.mybatis.code.generation.field.FieldMethod;
import imitate.mybatis.code.generation.field.FieldOrderBy;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.field.PropertiesVO;
import imitate.mybatis.code.generation.io.Resources;
import imitate.mybatis.code.generation.parsing.XNode;
import imitate.mybatis.code.generation.parsing.XNodeUtil;
import imitate.mybatis.code.generation.parsing.XPathParser;
import imitate.mybatis.code.generation.reflection.DefaultReflectorFactory;
import imitate.mybatis.code.generation.reflection.MetaClass;
import imitate.mybatis.code.generation.reflection.ReflectorFactory;
import imitate.mybatis.code.generation.session.Configuration;

/**
 * config xml创建者
 * 
 * @author Clinton Begin
 * @author Kazuki Shimizu
 */
public class XMLConfigBuilder extends BaseBuilder {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/** 是否解析过了 也就是mybatis每次启动，只能解析一次 **/
	private boolean parsed;
	// path 解析
	private final XPathParser parser;
	// local 映射工厂
	private final ReflectorFactory localReflectorFactory = new DefaultReflectorFactory();

	//
	public XMLConfigBuilder(Reader reader) {
		this(reader, null);
	}

	public XMLConfigBuilder(Reader reader, Properties props) {
		this(new XPathParser(reader, true, props, new XMLConfigEntityResolver()), props);
	}

	public XMLConfigBuilder(InputStream inputStream) {
		this(inputStream, null);
	}

	public XMLConfigBuilder(InputStream inputStream, Properties props) {
		this(new XPathParser(inputStream, true, props, new XMLConfigEntityResolver()), props);
	}

	private XMLConfigBuilder(XPathParser parser, Properties props) {
		super(new Configuration());
		ErrorContext.instance().resource("SQL Mapper Configuration");
		this.configuration.setVariables(props);
		this.parsed = false;
		this.parser = parser;
	}

	public Configuration parse() {
		if (parsed) {
			throw new BuilderException("Each XMLConfigBuilder can only be used once.");
		}
		parsed = true;
		parseConfiguration(parser.evalNode("/configuration"));
		return configuration;
	}

	private void parseConfiguration(XNode root) {
		try {
			// issue #117 read properties first
			propertiesElement(root.evalNode("properties"));
			// 解析settings 配置
			Properties settings = settingsAsProperties(root.evalNode("settings"));
			// 解析路径配置
			Properties paths = pathsAnalysis(root.evalNode("paths"));
			// 属性值合并
			settings.putAll(paths);
			// 设置 setting属性
			settingsElement(settings);
			// fields 配置解析、需要有自己update方法的，query 方法的。delete方法的
			fieldsConfigurationAnalysis(root.evalNode("fields"));
			// 解析需要生成的表配置
			tablesAnalysis(root.evalNode("tables"));
			// 加载反射工厂
			reflectorFactoryElement(root.evalNode("reflectorFactory"));
			// issue #117 read properties first
			resultElement(root.evalNode("resultsVO"));
			// 解析需要生成的表配置
			methodNamesAnalysis(root.evalNode("methodNames"));

		} catch (Exception e) {
			throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
		}
	}

	private Properties settingsAsProperties(XNode context) {
		if (context == null) {
			return new Properties();
		}
		Properties props = context.getChildrenAsProperties();
		// Check that all settings are known to the configuration class
		// 检查所有从settings加载的设置,确保它们都在Configuration定义的范围内
		MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
		for (Object key : props.keySet()) {
			// setting 里面的节点不存在抛出异常
			if (!metaConfig.hasSetter(String.valueOf(key))) {
				throw new BuilderException(
						"The setting " + key + " is not known. Make sure you spelled it correctly(case sensitive).");
			}
		}
		return props;
	}

	private Properties pathsAnalysis(XNode context) {
		if (context == null) {
			return new Properties();
		}
		Properties props = context.getChildrenAsProperties();
		// Check that all settings are known to the configuration class
		// 检查所有从settings加载的设置,确保它们都在Configuration定义的范围内
		MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
		for (Object key : props.keySet()) {
			// setting 里面的节点不存在抛出异常
			if (!metaConfig.hasSetter(String.valueOf(key))) {
				throw new BuilderException(
						"The setting " + key + " is not known. Make sure you spelled it correctly(case sensitive).");
			}
		}
		return props;
	}

	/** 解析 tables（生成部分表代码时使用，不配置生成数据库全部表数据） **/
	private void tablesAnalysis(XNode context) {
		if (context == null) {
			return;
		}
		// 配置开启、生成部分表代码，才解析表名的配置
		if (this.configuration.isCreateTableLimit()) {
			// 获取xml 配置的表
			List<String> createTableNameList = context.getCreateTableNameList();
			this.configuration.getCreateTableNameList().addAll(createTableNameList);
		}
	}

	/** 解析 result返回类的配置 **/
	private void resultElement(XNode context) {
		if (context == null) {
			return;
		}
		List<PropertiesVO> proList = XNodeUtil.resultVOs(context);
		Map<String, PropertiesVO> proMap = proList.stream()
				.collect(Collectors.toMap(PropertiesVO::getName, proVO -> proVO));
		configuration.getResultMap().putAll(proMap);
	}

	/** 解析 dao、service、biz、webService中的方法名字 **/
	private void methodNamesAnalysis(XNode context) {
		if (context == null) {
			return;
		}
		// 获取dao 层方法定义
		Properties daoPro = XNodeUtil.daoMethod(context);
		this.configuration.getDaoMethod().putAll(daoPro);

		// 获取service 层方法定义
		Properties servicePro = XNodeUtil.serviceMethod(context);
		this.configuration.getServiceMethod().putAll(servicePro);

		// 获取biz 层方法定义
		Properties bizPro = XNodeUtil.bizMethod(context);
		this.configuration.getBizMethod().putAll(bizPro);

		// 获取web Service 层方法定义
		Properties webServicePro = XNodeUtil.webServiceMethod(context);
		this.configuration.getWebServiceMethod().putAll(webServicePro);
		log.info("各个接口、类方法名称对应关系解析完毕");
	}

	/** 解析 fields 属性 各种作用如：有自己update方法、query方法、delete方法 **/
	private void fieldsConfigurationAnalysis(XNode context) {
		if (context == null) {
			return;
		}
		// 获取不能更新的方法
		List<FieldMethod> unUpdateFieldList = context.getUnUpdateField();
		try {
			Map<String, FieldMethod> unUpdateFieldMap = unUpdateFieldList.stream()
					.collect(Collectors.toMap(FieldMethod::getFieldName, fieldMethod -> fieldMethod));
			configuration.getUnUpdateFieldMap().putAll(unUpdateFieldMap);
		} catch (Exception e) {
			log.error("不可更新字段你配置重复error={}", e);
			return;
		}

		// 获取有自己query 方法的字段
		List<FieldMethod> queryFieldList = context.getQueryField();
		try {
			Map<String, FieldMethod> queryFieldMap = queryFieldList.stream()
					.collect(Collectors.toMap(FieldMethod::getFieldName, fieldMethod -> fieldMethod));
			configuration.getQueryFieldMap().putAll(queryFieldMap);
		} catch (Exception e) {
			log.error("查询字段配置重复error={}", e);
			return;
		}

		// 获取有自己update 方法字段
		List<FieldMethod> updateFieldList = context.getNeewUpdateMethodField();
		try {
			Map<String, FieldMethod> updateFieldMap = updateFieldList.stream()
					.collect(Collectors.toMap(FieldMethod::getFieldName, fieldMethod -> fieldMethod));
			configuration.getUpdateFieldMap().putAll(updateFieldMap);
		} catch (Exception e) {
			log.error("编辑方法字段配置重复error={}", e);
			return;
		}

		// 获取自己有delete 方法字段
		List<FieldMethod> deleteFieldList = context.getNeewDeleteMethodField();
		try {
			Map<String, FieldMethod> deleteFieldMap = deleteFieldList.stream()
					.collect(Collectors.toMap(FieldMethod::getFieldName, fieldMethod -> fieldMethod));
			configuration.getDeleteFieldMap().putAll(deleteFieldMap);
		} catch (Exception e) {
			log.error("删除方法字段配置重复error={}", e);
			return;
		}

		// 获取枚举类型 字段
		List<JavaField> enumFieldList = context.getEnumFieldList();
		try {
			Map<String, JavaField> enumFieldMap = enumFieldList.stream()
					.collect(Collectors.toMap(JavaField::getColumnName, javaField -> javaField));
			configuration.getEnumFieldMap().putAll(enumFieldMap);
		} catch (Exception e) {
			log.error("枚举配置重复error={}", e);
			return;
		}

		// 获取排序字段
		List<FieldOrderBy> orderByFieldList = context.getCanOrderByField();
		try {
			Map<String, FieldOrderBy> orderByFieldMap = orderByFieldList.stream()
					.collect(Collectors.toMap(FieldOrderBy::getFieldName, fieldMethod -> fieldMethod));
			configuration.getOrderByFieldMap().putAll(orderByFieldMap);
		} catch (Exception e) {
			log.error("排序字段配置重复error={}", e);
			return;
		}

		// 获取po、vo、form、父类中已经存在的字段，子类不用再生成 get、set方法，不用再声明属性
		Set<String> exitsField = context.getBaseClassExitsField();
		try {
			configuration.getBaseClassExitsField().addAll(exitsField);
		} catch (Exception e) {
			log.error("父类存在字段配置重复error={}", e);
			return;
		}
		log.info("各类字段解析完毕");
	}
	//
	// private void loadCustomVfs(Properties props) throws ClassNotFoundException {
	// String value = props.getProperty("vfsImpl");
	// if (value != null) {
	// String[] clazzes = value.split(",");
	// for (String clazz : clazzes) {
	// if (!clazz.isEmpty()) {
	// @SuppressWarnings("unchecked")
	// Class<? extends VFS> vfsImpl = (Class<? extends VFS>)
	// Resources.classForName(clazz);
	// configuration.setVfsImpl(vfsImpl);
	// }
	// }
	// }
	// }
	//
	// // 别名解析
	// private void typeAliasesElement(XNode parent) {
	// if (parent != null) {
	//
	// for (XNode child : parent.getChildren()) {
	// if ("package".equals(child.getName())) {
	// String typeAliasPackage = child.getStringAttribute("name");
	// logger.debug("别名解析 package typeAliasPackage={}",typeAliasPackage);
	// configuration.getTypeAliasRegistry().registerAliases(typeAliasPackage);
	// } else {
	// String alias = child.getStringAttribute("alias");
	// String type = child.getStringAttribute("type");
	// logger.debug("别名解析 alias={},type={}",alias,type);
	// try {
	// Class<?> clazz = Resources.classForName(type);
	// if (alias == null) {
	// typeAliasRegistry.registerAlias(clazz);
	// } else {
	// typeAliasRegistry.registerAlias(alias, clazz);
	// }
	// } catch (ClassNotFoundException e) {
	// throw new BuilderException("Error registering typeAlias for '" + alias + "'.
	// Cause: " + e, e);
	// }
	// }
	// }
	// }
	// }
	//
	// // 插件解析
	// private void pluginElement(XNode parent) throws Exception {
	// if (parent != null) {
	// // 可以有多个插件
	// for (XNode child : parent.getChildren()) {
	// String interceptor = child.getStringAttribute("interceptor");
	// Properties properties = child.getChildrenAsProperties();
	// // 确定类，先从别名注册器里面获取，如果没有，直接Resoure 加载， 在newInstance 生成
	// Interceptor interceptorInstance = (Interceptor)
	// resolveClass(interceptor).newInstance();
	// interceptorInstance.setProperties(properties);
	// configuration.addInterceptor(interceptorInstance);
	// }
	// }
	// }
	//
	// // 对象工厂解析
	// private void objectFactoryElement(XNode context) throws Exception {
	// if (context != null) {
	// String type = context.getStringAttribute("type");
	// Properties properties = context.getChildrenAsProperties();
	// // 对象工厂 与拦截器解析 一样
	// ObjectFactory factory = (ObjectFactory) resolveClass(type).newInstance();
	// factory.setProperties(properties);
	// configuration.setObjectFactory(factory);
	// }
	// }
	//
	// // 对象包装器解析
	// private void objectWrapperFactoryElement(XNode context) throws Exception {
	// if (context != null) {
	// String type = context.getStringAttribute("type");
	// // 一样先从别名注册器里面找，对象包装器
	// ObjectWrapperFactory factory = (ObjectWrapperFactory)
	// resolveClass(type).newInstance();
	// configuration.setObjectWrapperFactory(factory);
	// }
	// }

	// 映射关系解析
	private void reflectorFactoryElement(XNode context) throws Exception {
		if (context != null) {
			String type = context.getStringAttribute("type");
			// 反射工厂
			ReflectorFactory factory = (ReflectorFactory) resolveClass(type).newInstance();
			configuration.setReflectorFactory(factory);
		}
	}

	// 属性解析
	private void propertiesElement(XNode context) throws Exception {
		if (context != null) {
			// 加载property节点为property
			Properties defaults = context.getChildrenAsProperties();
			String resource = context.getStringAttribute("resource");
			String url = context.getStringAttribute("url");
			// resource或者url 只能包含一个
			if (resource != null && url != null) {
				throw new BuilderException(
						"The properties element cannot specify both a URL and a resource based property file reference. Please specify one or the other.");
			}
			if (resource != null) {
				defaults.putAll(Resources.getResourceAsProperties(resource));
			} else if (url != null) {
				defaults.putAll(Resources.getUrlAsProperties(url));
			}
			Properties vars = configuration.getVariables();
			if (vars != null) {
				defaults.putAll(vars);
			}
			// 解析出来的属性放入 xml config建造器内，是不是因为，配置文件 能通过${}调用，这些属性
			parser.setVariables(defaults);
			// 解析出来的属性放入配置类
			configuration.setVariables(defaults);
		}
	}

	// setting 设置解析
	private void settingsElement(Properties props) throws Exception {
		/** 是否只生成配置表名的代码 **/
		configuration.setCreateTableLimit(booleanValueOf(props.getProperty("createTableLimit"), false));
		/** 数据库实例名 **/
		configuration.setMysqlInstance(props.getProperty("mysqlInstance"));
		/** 数据库 表名前缀 **/
		configuration.setDatabaseNamePrefix(props.getProperty("databaseNamePrefix"));
		/** 是否是驼峰属性 **/
		configuration.setAttributeHump(booleanValueOf(props.getProperty("attributeHump"), false));
		/** 是否使用自己配置 **/
		configuration.setUseConfigByUser(booleanValueOf(props.getProperty("useConfigByUser"), false));
		/* api 接口前缀 */
		configuration.setApiPrefix(props.getProperty("apiPrefix"));
		/* class 类前缀 */
		configuration.setCalssPrefix(props.getProperty("calssPrefix"));
		/* 文件生成的路径 */
		configuration.setRelativePath(stringValueOf(props.getProperty("relativePath"), "./code"));
		/* 程序的前半部分路径 */
		configuration.setPackagePath(stringValueOf(props.getProperty("packagePath"), "com.code.generation"));
		/* po 路径 */
		configuration.setPathPo(stringValueOf(props.getProperty("pathPo"), "mybatis.po"));
		/* vo 路径 */
		configuration.setPathVo(stringValueOf(props.getProperty("pathVo"), "mybatis.vo"));
		/* form 路径 */
		configuration.setPathForm(stringValueOf(props.getProperty("pathForm"), "mybatis.form"));
		/* dao文件路径 */
		configuration.setPathDaoApi(stringValueOf(props.getProperty("pathDaoApi"), "dao"));
		/* mapper文件 路径 */
		configuration.setPathMapper(stringValueOf(props.getProperty("pathMapper"), "dao.mapper"));
		/* serviceImpl 文件路径 */
		configuration.setPathServiceImpl(stringValueOf(props.getProperty("pathServiceImpl"), "service.impl"));
		/* service 文件路径 */
		configuration.setPathService(stringValueOf(props.getProperty("pathService"), "service"));
		/* bizImpl 文件路径 */
		configuration.setPathBiz(stringValueOf(props.getProperty("pathBiz"), "biz"));
		/* biz 文件路径 */
		configuration.setPathBizImpl(stringValueOf(props.getProperty("pathBizImpl"), "biz.impl"));
		/* web端service 文件路径 */
		configuration.setPathWebService(stringValueOf(props.getProperty("pathWebService"), "webservice"));
		/* web端service实现类 文件路径 */
		configuration.setPathWebServiceImpl(stringValueOf(props.getProperty("pathWebServiceImpl"), "webservice.impl"));
		/* controller 文件路径 */
		configuration.setPathController(stringValueOf(props.getProperty("pathController"), "controller"));
		/* action 文件路径 */
		configuration.setPathAction(stringValueOf(props.getProperty("pathAction"), "action"));
		/* 异常 文件路径 */
		configuration.setPathException(stringValueOf(props.getProperty("pathException"), "exception"));

		// configuration.setAutoMappingBehavior(
		// AutoMappingBehavior.valueOf(props.getProperty("autoMappingBehavior",
		// "PARTIAL")));
		// configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior
		// .valueOf(props.getProperty("autoMappingUnknownColumnBehavior", "NONE")));
		// configuration.setCacheEnabled(booleanValueOf(props.getProperty("cacheEnabled"),
		// true));
		// configuration.setProxyFactory((ProxyFactory)
		// createInstance(props.getProperty("proxyFactory")));
		// configuration.setLazyLoadingEnabled(booleanValueOf(props.getProperty("lazyLoadingEnabled"),
		// false));
		// configuration.setAggressiveLazyLoading(booleanValueOf(props.getProperty("aggressiveLazyLoading"),
		// false));
		// configuration
		// .setMultipleResultSetsEnabled(booleanValueOf(props.getProperty("multipleResultSetsEnabled"),
		// true));
		// configuration.setUseColumnLabel(booleanValueOf(props.getProperty("useColumnLabel"),
		// true));
		// configuration.setUseGeneratedKeys(booleanValueOf(props.getProperty("useGeneratedKeys"),
		// false));
		// configuration.setDefaultExecutorType(ExecutorType.valueOf(props.getProperty("defaultExecutorType",
		// "SIMPLE")));
		// configuration.setDefaultStatementTimeout(integerValueOf(props.getProperty("defaultStatementTimeout"),
		// null));
		// configuration.setDefaultFetchSize(integerValueOf(props.getProperty("defaultFetchSize"),
		// null));
		// configuration.setMapUnderscoreToCamelCase(booleanValueOf(props.getProperty("mapUnderscoreToCamelCase"),
		// false));
		// configuration.setSafeRowBoundsEnabled(booleanValueOf(props.getProperty("safeRowBoundsEnabled"),
		// false));
		// configuration.setLocalCacheScope(LocalCacheScope.valueOf(props.getProperty("localCacheScope",
		// "SESSION")));
		// configuration.setJdbcTypeForNull(JdbcType.valueOf(props.getProperty("jdbcTypeForNull",
		// "OTHER")));
		// configuration.setLazyLoadTriggerMethods(
		// stringSetValueOf(props.getProperty("lazyLoadTriggerMethods"),
		// "equals,clone,hashCode,toString"));
		// configuration.setSafeResultHandlerEnabled(booleanValueOf(props.getProperty("safeResultHandlerEnabled"),
		// true));
		// configuration.setDefaultScriptingLanguage(resolveClass(props.getProperty("defaultScriptingLanguage")));
		// @SuppressWarnings("unchecked")
		// Class<? extends TypeHandler> typeHandler = (Class<? extends TypeHandler>)
		// resolveClass(
		// props.getProperty("defaultEnumTypeHandler"));
		// configuration.setDefaultEnumTypeHandler(typeHandler);
		// configuration.setCallSettersOnNulls(booleanValueOf(props.getProperty("callSettersOnNulls"),
		// false));
		// configuration.setUseActualParamName(booleanValueOf(props.getProperty("useActualParamName"),
		// true));
		// configuration
		// .setReturnInstanceForEmptyRow(booleanValueOf(props.getProperty("returnInstanceForEmptyRow"),
		// false));
		// configuration.setLogPrefix(props.getProperty("logPrefix"));
		// @SuppressWarnings("unchecked")
		// Class<? extends Log> logImpl = (Class<? extends Log>)
		// resolveClass(props.getProperty("logImpl"));
		// configuration.setLogImpl(logImpl);
		// configuration.setConfigurationFactory(resolveClass(props.getProperty("configurationFactory")));

	}
	//
	// // 环境设置解析
	// private void environmentsElement(XNode context) throws Exception {
	// if (context != null) {
	// // 只能配置一次，没有配置时，获取默认的配置
	// if (environment == null) {
	// environment = context.getStringAttribute("default");
	// }
	// for (XNode child : context.getChildren()) {// 如果没有配置环境，这里就是个size为0的集合
	// String id = child.getStringAttribute("id");
	// // 判断 解析的环境是否为需要的环境
	// if (isSpecifiedEnvironment(id)) {
	// // 事务配置并创建事务工厂
	// TransactionFactory txFactory =
	// transactionManagerElement(child.evalNode("transactionManager"));
	// // 数据源配置加载并实例化数据源, 数据源是必备的
	// DataSourceFactory dsFactory =
	// dataSourceElement(child.evalNode("dataSource"));
	// DataSource dataSource = dsFactory.getDataSource();
	// // 创建Environment.Builder
	// Environment.Builder environmentBuilder = new
	// Environment.Builder(id).transactionFactory(txFactory)
	// .dataSource(dataSource);
	// configuration.setEnvironment(environmentBuilder.build());
	// }
	// }
	// }
	// }
	//
	// // 数据库id配置解析
	// private void databaseIdProviderElement(XNode context) throws Exception {
	// DatabaseIdProvider databaseIdProvider = null;
	// if (context != null) {
	// String type = context.getStringAttribute("type");
	// // awful patch to keep backward compatibility
	// if ("VENDOR".equals(type)) {
	// type = "DB_VENDOR";
	// }
	// Properties properties = context.getChildrenAsProperties();
	// databaseIdProvider = (DatabaseIdProvider) resolveClass(type).newInstance();
	// databaseIdProvider.setProperties(properties);
	// }
	// Environment environment = configuration.getEnvironment();
	// if (environment != null && databaseIdProvider != null) {
	// String databaseId =
	// databaseIdProvider.getDatabaseId(environment.getDataSource());
	// configuration.setDatabaseId(databaseId);
	// }
	// }
	//
	// // 事物管理器解析
	// private TransactionFactory transactionManagerElement(XNode context) throws
	// Exception {
	// if (context != null) {
	//
	// String type = context.getStringAttribute("type");
	// logger.debug("事物管理器解析 type={}",type);
	// Properties props = context.getChildrenAsProperties();
	// TransactionFactory factory = (TransactionFactory)
	// resolveClass(type).newInstance();
	// factory.setProperties(props);
	// return factory;
	// }
	// throw new BuilderException("Environment declaration requires a
	// TransactionFactory.");
	// }
	//
	// // 数据源解析
	// private DataSourceFactory dataSourceElement(XNode context) throws Exception {
	// if (context != null) {
	// String type = context.getStringAttribute("type");
	// logger.debug("数据源解析 type={}",type);
	// Properties props = context.getChildrenAsProperties();
	// DataSourceFactory factory = (DataSourceFactory)
	// resolveClass(type).newInstance();
	// factory.setProperties(props);
	// return factory;
	// }
	// throw new BuilderException("Environment declaration requires a
	// DataSourceFactory.");
	// }
	//
	// // 类型处理器解析
	// private void typeHandlerElement(XNode parent) throws Exception {
	// if (parent != null) {
	// for (XNode child : parent.getChildren()) {
	// if ("package".equals(child.getName())) {
	// String typeHandlerPackage = child.getStringAttribute("name");
	// logger.debug("类型处理器解析 typeHandlerPackage={}",typeHandlerPackage);
	// typeHandlerRegistry.register(typeHandlerPackage);
	// } else {
	// String javaTypeName = child.getStringAttribute("javaType");
	// String jdbcTypeName = child.getStringAttribute("jdbcType");
	// String handlerTypeName = child.getStringAttribute("handler");
	// logger.debug("类型处理器解析
	// javaType={},jdbcType={},handler={}",javaTypeName,jdbcTypeName,handlerTypeName);
	// Class<?> javaTypeClass = resolveClass(javaTypeName);
	// JdbcType jdbcType = resolveJdbcType(jdbcTypeName);
	// Class<?> typeHandlerClass = resolveClass(handlerTypeName);
	// if (javaTypeClass != null) {
	// if (jdbcType == null) {
	// typeHandlerRegistry.register(javaTypeClass, typeHandlerClass);
	// } else {
	// typeHandlerRegistry.register(javaTypeClass, jdbcType, typeHandlerClass);
	// }
	// } else {
	// typeHandlerRegistry.register(typeHandlerClass);
	// }
	// }
	// }
	// }
	// }
	//
	// // mapper 配置解析
	// private void mapperElement(XNode parent) throws Exception {
	// if (parent != null) {
	// for (XNode child : parent.getChildren()) {
	// //
	// 如果要同时使用package自动扫描和通过mapper明确指定要加载的mapper，一定要确保package自动扫描的范围不包含明确指定的mapper，
	// //
	// 否则在通过package扫描的interface的时候，尝试加载对应xml文件的loadXmlResource()的逻辑中出现判重出错，报org.apache.ibatis.binding.BindingException异常，即使xml文件中包含的内容和mapper接口中包含的语句不重复也会出错，包括加载mapper接口时自动加载的xml
	// // mapper也一样会出错。
	// if ("package".equals(child.getName())) {
	// String mapperPackage = child.getStringAttribute("name");
	// logger.debug("mapper 配置解析 mapperPackage={}",mapperPackage);
	// configuration.addMappers(mapperPackage);
	// } else {
	// String resource = child.getStringAttribute("resource");
	// String url = child.getStringAttribute("url");
	// String mapperClass = child.getStringAttribute("class");
	// logger.debug("mapper 配置解析
	// resource={},url={},mapperClass={}",resource,url,mapperClass);
	// if (resource != null && url == null && mapperClass == null) {
	// ErrorContext.instance().resource(resource);
	// InputStream inputStream = Resources.getResourceAsStream(resource);
	// XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream,
	// configuration, resource,
	// configuration.getSqlFragments());
	// mapperParser.parse();
	// } else if (resource == null && url != null && mapperClass == null) {
	// ErrorContext.instance().resource(url);
	// InputStream inputStream = Resources.getUrlAsStream(url);
	// XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream,
	// configuration, url,
	// configuration.getSqlFragments());
	// mapperParser.parse();
	// } else if (resource == null && url == null && mapperClass != null) {
	// Class<?> mapperInterface = Resources.classForName(mapperClass);
	// configuration.addMapper(mapperInterface);
	// } else {
	// throw new BuilderException(
	// "A mapper element may only specify a url, resource or class, but not more
	// than one.");
	// }
	// }
	// }
	// }
	// }
	//
	// // 是否指定的环境，没有指定抛出异常
	// private boolean isSpecifiedEnvironment(String id) {
	// if (environment == null) {
	// throw new BuilderException("No environment specified.");
	// } else if (id == null) {
	// throw new BuilderException("Environment requires an id attribute.");
	// } else if (environment.equals(id)) {
	// return true;
	// }
	// return false;
	// }

}
