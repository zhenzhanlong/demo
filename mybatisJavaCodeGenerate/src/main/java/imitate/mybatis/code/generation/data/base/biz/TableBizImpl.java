package imitate.mybatis.code.generation.data.base.biz;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import imitate.mybatis.code.generation.creating.api.IBizFileCreating;
import imitate.mybatis.code.generation.creating.api.IDaoFileCreating;
import imitate.mybatis.code.generation.creating.api.IServiceFileCreating;
import imitate.mybatis.code.generation.creating.api.IWebFileCreating;
import imitate.mybatis.code.generation.creating.controller.ControllerFileCreating;
import imitate.mybatis.code.generation.creating.entity.file.FormFileCreating;
import imitate.mybatis.code.generation.creating.entity.file.POFileCreating;
import imitate.mybatis.code.generation.creating.entity.file.VOFileCreating;
import imitate.mybatis.code.generation.creating.entity.xml.FormByXmlFileCreating;
import imitate.mybatis.code.generation.creating.entity.xml.POByXmlFileCreating;
import imitate.mybatis.code.generation.creating.entity.xml.VOByXmlFileCreating;
import imitate.mybatis.code.generation.creating.impl.BizImplFileCreating;
import imitate.mybatis.code.generation.creating.impl.ServiceImplFileCreating;
import imitate.mybatis.code.generation.creating.impl.WebServiceImplFileCreating;
import imitate.mybatis.code.generation.creating.xml.XmlFileCreating;
import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.data.base.po.QueryTableField;
import imitate.mybatis.code.generation.data.base.service.ITableService;
import imitate.mybatis.code.generation.field.CreateEntityFileConfig;
import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.io.Resources;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.session.CreateSessionFactory;
import imitate.mybatis.code.generation.session.CreateSessionFactoryBuilder;
import imitate.mybatis.code.generation.type.JdbcType;
import imitate.mybatis.code.generation.util.NameUtil;

/**
 * 逻辑业务层 代码实现类
 * 
 * @author lenovo
 *
 */
@Service("tableBizImpl")
public class TableBizImpl implements ITableBiz {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "tableServiceImpl")
	private ITableService tableServiceImpl;
	// 配置类
	private Configuration configuration;

	/** 开始生成文件 */
	public void createFiles() {
		// 1、解析配置
		Reader reader = null;
		CreateSessionFactory sqlSessionFactory = null;
		try {
			reader = Resources.getResourceAsReader("mybatis-3-code-generation.xml");
			sqlSessionFactory = new CreateSessionFactoryBuilder().build(reader);
			// 配置类
			this.configuration = sqlSessionFactory.getConfiguration();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// 2、加载数据库表、与字段信息
		loadTableAndField();
		// 3、生成文件
		createFileList();
	}

	public void loadTableAndField() {
		// 数据库实例名称

		String mysqlInstance = configuration.getMysqlInstance();
		log.debug("数据库实例 mysqlInstance={}", mysqlInstance);
		// 查询数据库表集合
		List<DataBaseTable> tableList = this.tableServiceImpl.queryTableList(mysqlInstance);
		// 表名，与字段关系
		Map<String, CreateEntityFileConfig> relationMap = new HashMap<>(tableList.size());
		// 字段集合
		List<QueryTableField> myBatisFieldList = null;
		// 需要生成 entity 代码配置信息
		List<CreateEntityFileConfig> needCreateEntityFileTableList = new ArrayList<>(tableList.size());
		// 需要生成代码信息的表
		List<DataBaseTable> needCreateFileTableList = new ArrayList<>(tableList.size());
		for (DataBaseTable table : tableList) {
			CreateEntityFileConfig fileVO = new CreateEntityFileConfig(table, this.configuration);
			// 只生成部分表代码
			if (configuration.isCreateTableLimit()
					&& (configuration.getCreateTableNameList().indexOf(table.getTable_name()) < 0)) {
				log.debug("没有配置table_name={}", table.getTable_name());
				continue;
			}
			myBatisFieldList = this.tableServiceImpl.queryTableFieldList(mysqlInstance, table.getTable_name());
			// 去除多余字段（如basePO,baseForm里面存在的字段），增加需要的枚举字段
			fileVO.setJavaFieldList(mysqlField2JavaField(myBatisFieldList));
			// 加入集合
			needCreateEntityFileTableList.add(fileVO);
			// 表名加入集合
			needCreateFileTableList.add(table);
			// 关系数据保存
			relationMap.put(table.getTable_name(), fileVO);
		}
		// 解析后的数据记入配置类内
		this.configuration.getNeedCreateEntityFileTableList().addAll(needCreateEntityFileTableList);
		// 加入配置
		this.configuration.getNeedCreateFileTableList().addAll(needCreateFileTableList);
		// 加入配置类
		this.configuration.getNeedCreateEntityFileTableMap().putAll(relationMap);
	}

	// 生成java 文件
	public void createFileList() {
		// 生成 entity文件
		// createEntityFile();
		// // 生成dao层api文件
		// createDaoApiFile();
		// // 生成service 层 api文件
		// createServiceApiFile();
		// // 生成service 层 api文件
		// createBizApiFile();
		// // 生成 web 层 api文件
		// createIWebApiFile();
		// // 生成 service 层Impl
		// createServiceImplApiFile();
		// // 生成 biz 层Impl
		// createBizImplApiFile();
		// // 生成 web 端service 层Impl
		// createWebServiceImplApiFile();
		// // 生成 controller 层 文件
		// createControllerFile();
		// 生成 mapper 层 文件
		// createMapperFile();
		// 生成 entity 通过 xml 配置
		createEntityByXmlFile();

	}

	// 生成po、vo、form、 文件
	public void createEntityByXmlFile() {
		// 生成 po文件
		FormByXmlFileCreating form = new FormByXmlFileCreating(this.configuration);
		form.createEntityFileProcess();
		// 生成vo文件
		VOByXmlFileCreating vo = new VOByXmlFileCreating(this.configuration);
		vo.createEntityFileProcess();
		// 生成form文件
		POByXmlFileCreating po = new POByXmlFileCreating(this.configuration);
		po.createEntityFileProcess();
	}

	// 生成 mapper 层Impl
	public void createMapperFile() {
		XmlFileCreating xmlFile = new XmlFileCreating(this.configuration);
		xmlFile.createMapperProcess();
	}

	// 生成 web 端service 层Impl
	public void createControllerFile() {
		ControllerFileCreating controllerFile = new ControllerFileCreating(this.configuration);
		controllerFile.createServletFileProcess();
	}

	// 生成 web 端service 层Impl
	public void createWebServiceImplApiFile() {
		WebServiceImplFileCreating webServiceImplFile = new WebServiceImplFileCreating(this.configuration);
		webServiceImplFile.createImplFileProcess();
	}

	// 生成 biz 层Impl
	public void createBizImplApiFile() {
		BizImplFileCreating bizImplFile = new BizImplFileCreating(this.configuration);
		bizImplFile.createImplFileProcess();
	}

	// 生成 service 层Impl
	public void createServiceImplApiFile() {
		ServiceImplFileCreating serviceImplFile = new ServiceImplFileCreating(this.configuration);
		serviceImplFile.createImplFileProcess();
	}

	// 生成web层api
	public void createIWebApiFile() {
		IWebFileCreating webServiceImplFile = new IWebFileCreating(this.configuration);
		webServiceImplFile.createApiFileProcess();
	}

	// 生成service层api
	public void createBizApiFile() {
		IBizFileCreating bizApiFile = new IBizFileCreating(this.configuration);
		bizApiFile.createApiFileProcess();
	}

	// 生成service层api
	public void createServiceApiFile() {
		IServiceFileCreating serviceApiFile = new IServiceFileCreating(this.configuration);
		serviceApiFile.createApiFileProcess();
	}

	// dao层api
	public void createDaoApiFile() {
		IDaoFileCreating daoApiFile = new IDaoFileCreating(this.configuration);
		daoApiFile.createApiFileProcess();
	}

	// 生成po、vo、form、 文件
	public void createEntityFile() {
		// 生成 po文件
		POFileCreating po = new POFileCreating(this.configuration);
		po.createFiles();
		// 生成vo文件
		VOFileCreating vo = new VOFileCreating(this.configuration);
		vo.createFiles();
		// 生成form文件
		FormFileCreating form = new FormFileCreating(this.configuration);
		form.createFiles();
	}

	/**
	 * 数据库字段类，转换为java类
	 *
	 * @param fieldList
	 * @return
	 */
	private List<JavaField> mysqlField2JavaField(final List<QueryTableField> fieldList) {
		List<JavaField> attrList = new ArrayList<>(fieldList.size());
		fieldList.forEach(field -> {
			JavaField attrVO = new JavaField();
			// mysql属性
			attrVO.setTableCatalog(field.getTable_catalog());
			attrVO.setTableSchema(field.getTable_schema());
			attrVO.setTableName(field.getTable_name());
			attrVO.setColumnName(field.getColumn_name());
			attrVO.setOrdinalPosition(field.getOrdinal_position());
			attrVO.setColunmDefault(field.getColunm_default());
			attrVO.setIsNullable(field.getIs_nullable());
			attrVO.setColumnDataType(field.getData_type());
			attrVO.setCharacterMaxinumLength(field.getCharacter_maxinum_length());
			attrVO.setCharacterOctetLength(field.getCharacter_octet_length());
			attrVO.setNumericPrecision(field.getNumeric_precision());
			attrVO.setNumericScale(field.getNumeric_scale());
			attrVO.setDatatimePrecision(field.getDatatime_precision());
			attrVO.setCharacterSetName(field.getCharacter_set_name());
			attrVO.setCollationName(field.getCollation_name());
			attrVO.setColumnType(field.getColumn_type());
			attrVO.setExtra(field.getExtra());
			attrVO.setPrivileges(field.getPrivileges());
			attrVO.setColumnComment(field.getColumn_comment());
			// java属性
			// 字段名
			attrVO.setJavaName(this.subFieldName(field.getColumn_name()));
			// 字段属性
			confirmJavaType(attrVO, field.getData_type());
			// 加入集合
			attrList.add(attrVO);
			System.out.println(attrVO.toString());
		});
		return attrList;
	}

	// 根据配置 找到 JdbcType 对应的 JavaType
	private void confirmJavaType(JavaField attrVO, String columnDataType) {
		// 不是枚举字段
		JavaField javaFieldEnum = this.configuration.getEnumFieldMap().get(attrVO.getColumnName());
		if (null == javaFieldEnum) {
			JdbcType jdbcType = JdbcType.forJavaName(columnDataType);
			log.info("columnDataType={},jdbcType={}", columnDataType, jdbcType);
			attrVO.setJavaType(jdbcType.TYPE_NAME);
			attrVO.setJdbcType(jdbcType);
		} else {
			attrVO.setClassPath(javaFieldEnum.getClassPath());
			attrVO.setJavaType(javaFieldEnum.getColumnDataType());
			attrVO.setJavaType(javaFieldEnum.getColumnDataType());
		}

	}

	/*********************** 公用，可塑性代码生成出 *******************************/
	/** 截取数据库字段名 生成文件使用 **/
	public String subFieldName(String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			log.error("字段为空，不可处理fieldName={}", fieldName);
		}
		// 不是驼峰显示直接返回
		if (!this.configuration.isAttributeHump()) {
			return fieldName;
		}
		if (fieldName.indexOf(NameUtil.FIELD_DELIMITER) >= 0) {
			String[] fieldNames = fieldName.split(NameUtil.FIELD_DELIMITER);
			StringBuilder fieldNameStr = new StringBuilder();
			for (int i = 0, l = fieldNames.length; i < l; i++) {
				fieldNames[i] = fieldNames[i].toLowerCase();
				if (StringUtils.isBlank(fieldNames[i])) {
					continue;
				}
				// 去掉分隔符首字母大写
				if (i > 0) {
					fieldNameStr.append(NameUtil.firstCharacterToUpper(fieldNames[i]));
				} else {// 首个单词的首字母不大写
					fieldNameStr.append(fieldNames[i]);
				}
			}
			log.debug("字段处理完成 fieldNameStr={}", fieldNameStr);
			return fieldNameStr.toString();
		}
		log.debug("处理后的字段 fieldName={}", fieldName);
		return fieldName;
	}
}
