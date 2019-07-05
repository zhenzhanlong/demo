package imitate.mybatis.code.generation.creating.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.creating.BaseCreatingFile;
import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.CreateIApiFileConfig;
import imitate.mybatis.code.generation.field.CreateImplFileConfig;
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
import imitate.mybatis.code.generation.util.NameUtil;

/**
 * impl 类文件生成类
 * 
 * @author Administrator
 *
 */
public abstract class ServletCreatingFile extends BaseCreatingFile {
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

	public ServletCreatingFile(Configuration configuration) {
		super(configuration);
	}

	public void createServletFileProcess() {
		// 第一步解析。要生成的文件的xml的配置
		analysisServletFile();
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
		// 生成文件 queryField 块
		String queryFieldContent = createContentQuery();
		// 生成文件 updateField 块
		String updateFieldContent = createContentUpdate();
		// 生成文件 deleteField 块
		String deleteFieldContent = createContentDelete();
		// 生成文件 orderByField 块
		String orderByFieldContent = createContentOrderBy();
		content.append(bodyCodeContent.substring(0, bodyCodeContent.lastIndexOf("}"))).append(queryFieldContent)
				.append(updateFieldContent).append(deleteFieldContent).append(orderByFieldContent).append("}");
		// 生成文件
		createFile();
	}

	public String createContentBodyCode() {
		XNode fileBody = this.apiFileConfig.getFileBody();
		return ApiXmlFileParser.parse(fileBody.getStringBody(), pro);
	}

	public String createContentQuery() {
		XNode queryByNode = this.apiFileConfig.getQueryField();
		// 获取需要排序的字段
		Map<String, FieldMethod> queryFieldMap = this.configuration.getQueryFieldMap();
		// 获取表的所有字段
		CreateEntityFileConfig entityFileConfig = this.configuration.getNeedCreateEntityFileTableMap()
				.get(this.apiFileConfig.getTable().getTable_name());
		List<JavaField> list = entityFileConfig.getJavaFieldList();
		// 方法名称的配置关系加入集合
		StringBuilder orderContent = new StringBuilder();
		list.stream().forEach(field -> {
			if (null != queryFieldMap.get(field.getColumnName())) {
				pro.put("queryField", NameUtil.firstCharacterToUpper(field.getJavaName()));
				orderContent.append(ApiXmlFileParser.parse(queryByNode.getStringBody(), pro));
			}
		});
		return orderContent.toString();
	}

	public String createContentUpdate() {
		XNode updateByNode = this.apiFileConfig.getUpdateField();
		// 获取需要排序的字段
		Map<String, FieldMethod> updateFieldMap = this.configuration.getUpdateFieldMap();
		// 获取表的所有字段
		CreateEntityFileConfig entityFileConfig = this.configuration.getNeedCreateEntityFileTableMap()
				.get(this.apiFileConfig.getTable().getTable_name());
		List<JavaField> list = entityFileConfig.getJavaFieldList();
		// 方法名称的配置关系加入集合
		StringBuilder orderContent = new StringBuilder();
		list.stream().forEach(field -> {
			if (null != updateFieldMap.get(field.getColumnName())) {
				pro.put("updateField", NameUtil.firstCharacterToUpper(field.getJavaName()));
				orderContent.append(ApiXmlFileParser.parse(updateByNode.getStringBody(), pro));
			}
		});
		return orderContent.toString();
	}

	public String createContentDelete() {
		XNode deleteNode = this.apiFileConfig.getDeleteField();
		// 获取需要排序的字段
		Map<String, FieldMethod> deleteFieldMap = this.configuration.getDeleteFieldMap();
		// 获取表的所有字段
		CreateEntityFileConfig entityFileConfig = this.configuration.getNeedCreateEntityFileTableMap()
				.get(this.apiFileConfig.getTable().getTable_name());
		List<JavaField> list = entityFileConfig.getJavaFieldList();
		// 方法名称的配置关系加入集合
		StringBuilder orderContent = new StringBuilder();
		list.stream().forEach(field -> {
			if (null != deleteFieldMap.get(field.getColumnName())) {
				pro.put("deleteField", NameUtil.firstCharacterToUpper(field.getJavaName()));
				orderContent.append(ApiXmlFileParser.parse(deleteNode.getStringBody(), pro));
			}
		});
		return orderContent.toString();
	}

	public String createContentOrderBy() {
		XNode orderByNode = this.apiFileConfig.getOrderByField();
		// 获取需要排序的字段
		Map<String, FieldOrderBy> orderFieldMap = this.configuration.getOrderByFieldMap();
		// 获取表的所有字段
		CreateEntityFileConfig entityFileConfig = this.configuration.getNeedCreateEntityFileTableMap()
				.get(this.apiFileConfig.getTable().getTable_name());
		List<JavaField> list = entityFileConfig.getJavaFieldList();
		// 方法名称的配置关系加入集合
		StringBuilder orderContent = new StringBuilder();
		list.stream().forEach(field -> {
			if (null != orderFieldMap.get(field.getColumnName())) {
				pro.put("orderField", NameUtil.firstCharacterToUpper(field.getJavaName()));
				orderContent.append(ApiXmlFileParser.parse(orderByNode.getStringBody(), pro));
			}
		});
		return orderContent.toString();
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
	public abstract void analysisServletFile();

	// 解析 生成文件
	public abstract void createFile();

}
