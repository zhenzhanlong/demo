package com.know.code.generation.constants;

import org.springframework.core.annotation.Order;

import com.know.code.generation.util.PropertiesUtil;

/*** 常量类 **/
@Order(2)
public class MConstants {
	/** 数据库实例名称****/
	public static final String MYSQL_INSTANCE="mysql.instance"; 
	/** 数据库表明的前缀 **/
	public static final String DATA_BASE_NAME_PREFIX="database.name.prefix";
	/** 字段属性驼峰显示 **/
	public static final String ATTRIBUTE_HUMP="attribute_hump";
	/** 程序的前半部分路径  ***/
	public static final String PACKAGE_PATH="package.path";
	/** 相对路径 ***/
	public static final String RELATIVE_PATH="relative_path";
	/** 配置以用户自己配置的为准，（否则走默认的）**/
	public static final String USE_CONFIG_BY_USER="use.config.by.user";
	/** 所有配置为是   的 常量**/
	public static final String YES="yes";
	/** 所有配置为否   的 常量**/
	public static final String NO="no";
	/** #BasePO,BaseVO,BaseForm 已经存在字段在po，vo，form里面去掉 **/
	public static final String BASE_FIELD_HAS_EXITS="base.field.has.exits";
	/** vo是否集成po **/
	public static final String VO_EXTEND_PO="vo_extend_po";
	
	/** 数据库内不能更新的字段 **/
	public static final String NOT_UPDATE_FIELD="not_update_field";
	
	/** 默认公用包路径 **/
	public static final String DEFAULT_PACKAGE_PATH=PropertiesUtil.getValue(PACKAGE_PATH)!=null?PropertiesUtil.getValue(PACKAGE_PATH):"com.sx.manage.";
	/** 默认 po 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_PO=PropertiesUtil.getValue("default.package.path.po")!=null?PropertiesUtil.getValue("default.package.path.po"):"mybatis.po";
	/** 默认 vo 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_VO=PropertiesUtil.getValue("default.package.path.vo")!=null?PropertiesUtil.getValue("default.package.path.vo"):"mybatis.vo";
	/** 默认 form 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_FORM=PropertiesUtil.getValue("default.package.path.form")!=null?PropertiesUtil.getValue("default.package.path.form"):"mybatis.form";
	/** 默认 dao 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_DAO_API=PropertiesUtil.getValue("default.package.path.dao.api")!=null?PropertiesUtil.getValue("default.package.path.dao.api"):"dao";
	/** 默认 mybatis的xml文件 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_MAPPER=PropertiesUtil.getValue("default.package.path.mapper")!=null?PropertiesUtil.getValue("default.package.path.mapper"):"dao.mapper";
	/** 默认 service 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_SERVICE=PropertiesUtil.getValue("default.package.path.service")!=null?PropertiesUtil.getValue("default.package.path.service"):"service.impl";
	/** 默认 service的api 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_SERVICE_API=PropertiesUtil.getValue("default.package.path.service.api")!=null?PropertiesUtil.getValue("default.package.path.service.api"):"service";
	/** 默认 biz 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_BIZ=PropertiesUtil.getValue("default.package.path.biz")!=null?PropertiesUtil.getValue("default.package.path.biz"):"biz.impl";
	/** 默认 biz的api 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_BIZ_API=PropertiesUtil.getValue("default.package.path.biz.api")!=null?PropertiesUtil.getValue("default.package.path.biz.api"):"biz";
	/** 默认 web端service 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_WEB_SERVICE=PropertiesUtil.getValue("default.package.path.web.service")!=null?PropertiesUtil.getValue("default.package.path.web.service"):"web_service";
	/** 默认 controller 路径 **/
	public static final String DEFAULT_PACKAGE_PATH_CONTROLLER=PropertiesUtil.getValue("default.package.path.controller")!=null?PropertiesUtil.getValue("default.package.path.controller"):"controller";
	
	/** 字段属性驼峰显示-yes **/
	public static final String ATTRIBUTE_HUMP_YES="YES";  
	/** 字段属性驼峰显示 -no **/
	public static final String ATTRIBUTE_HUMP_NO="NO"; 
	
	
	/**1 dao层 新增数据**/
	public static final String DAO_METHOD_INSERT=PropertiesUtil.getValue("dao.method.insert")!=null?PropertiesUtil.getValue("dao.method.insert"):"insert";
	/**2 dao层 根据 id删除数据**/
	public static final String DAO_METHOD_DELETE_BY_ID=PropertiesUtil.getValue("dao.method.delete.by.id")!=null?PropertiesUtil.getValue("dao.method.delete.by.id"):"delete_by_id";
	/**3 dao层 修改  数据**/
	public static final String DAO_METHOD_UPDATE=PropertiesUtil.getValue("dao.method.update")!=null?PropertiesUtil.getValue("dao.method.update"):"update";
	/**4 dao层  根据id查询 **/
	public static final String DAO_METHOD_QUERY_BY_ID=PropertiesUtil.getValue("dao.method.query.by.id")!=null?PropertiesUtil.getValue("dao.method.query.by.id"):"query_by_id";	
	/**5 dao层  查询数量 **/
	public static final String DAO_METHOD_QUERY_COUNT_NUM=PropertiesUtil.getValue("dao.method.query.count.num")!=null?PropertiesUtil.getValue("dao.method.query.count.num"):"count_num";	
	/**6 dao层  分页查询 **/
	public static final String DAO_METHOD_QUERY_PAGE=PropertiesUtil.getValue("dao.method.query.page")!=null?PropertiesUtil.getValue("dao.method.query.page"):"query_page";
	/**7 dao层  分页查询 **/
	public static final String DAO_METHOD_QUERY_LIST=PropertiesUtil.getValue("dao.method.query.list")!=null?PropertiesUtil.getValue("dao.method.query.list"):"query_list";
	/**8 dao层  更新数据状态 **/
	public static final String DAO_METHOD_EDIT_STATUS=PropertiesUtil.getValue("dao.method.edit.status")!=null?PropertiesUtil.getValue("dao.method.edit.status"):"edit_status";
	/**9 dao层  更新数据状态 **/
	public static final String DAO_METHOD_EDIT_STATUS_BATCH=PropertiesUtil.getValue("dao.method.edit.status.batch")!=null?PropertiesUtil.getValue("dao.method.edit.status.batch"):"edit_status_batch";

	/**8 dao层  根据or查询唯一数据 **/
	public static final String DAO_METHOD_QUERY_UNIQUE_BY_OR=PropertiesUtil.getValue("dao.method.query.unique.by.or")!=null?PropertiesUtil.getValue("dao.method.query.unique.by.or"):"query_unique_by_or";
	/**9 dao层  根据or 查询集合 **/
	public static final String DAO_METHOD_QUERY_LIST_BY_OR=PropertiesUtil.getValue("dao.method.query.list.by.or")!=null?PropertiesUtil.getValue("dao.method.query.list.by.or"):"query_list_by_or";
	/**10 dao层 保存数据**/
	public static final String DAO_METHOD_SAVE=PropertiesUtil.getValue("dao.method.save")!=null?PropertiesUtil.getValue("dao.method.save"):"save";
	
	/**1 数据字段  :数据状态 ***/
	public static final String DB_FIELD_DATA_STATUS="data_status";
	
	
	
	
	/**  MConsumerLoginVO 引入路径**/
	public static final String CONSUMER_LOGIN_VO_PATH=PropertiesUtil.getValue("consumer.login.vo.path")!=null?PropertiesUtil.getValue("consumer.login.vo.path"):"com.sx.manage.mybatis.vo.consumer.login.MConsumerLoginVO";
	/**  ResultListVO 引入路径**/
	public static final String RESULT_LIST_VO_PATH=PropertiesUtil.getValue("result.vo.path")!=null?("import "+PropertiesUtil.getValue("result.vo.path")+"ResultListVO"):"com.sx.manage.mybatis.vo.result.ResultListVO";
	/**  ResultObjectVO 引入路径**/
	public static final String RESULT_OBJECT_VO_PATH=PropertiesUtil.getValue("result.vo.path")!=null?("import "+PropertiesUtil.getValue("result.vo.path")+"ResultObjectVO"):"com.sx.manage.mybatis.vo.result.ResultObjectVO";
	/**  ResultBooleanVO 引入路径**/
	public static final String RESULT_BOOLEAN_VO_PATH=PropertiesUtil.getValue("result.vo.path")!=null?("import "+PropertiesUtil.getValue("result.vo.path")+"ResultBooleanVO"):"com.sx.manage.mybatis.vo.result.ResultBooleanVO";

	
	/**  MConsumerLoginVO 引入路径**/
	public static final String CONSUMER_LOGIN_VO=PropertiesUtil.getValue("consumer.login.vo")!=null?PropertiesUtil.getValue("consumer.login.vo"):"MConsumerLoginVO";
	/**  ResultListVO 引入路径**/
	public static final String RESULT_LIST_VO=PropertiesUtil.getValue("result.list.vo")!=null?PropertiesUtil.getValue("result.list.vo"):"ResultListVO";
	/**  ResultObjectVO 引入路径**/
	public static final String RESULT_OBJECT_VO=PropertiesUtil.getValue("result.object.vo")!=null?PropertiesUtil.getValue("result.object.vo"):"ResultObjectVO";
	/**  ResultBooleanVO 引入路径**/
	public static final String RESULT_BOOLEAN_VO=PropertiesUtil.getValue("result.boolean.vo")!=null?PropertiesUtil.getValue("result.boolean.vo"):"ResultBooleanVO";

	
	
	/**1 service层 新增数据**/
	public static final String SERVICE_METHOD_SAVE=PropertiesUtil.getValue("service.method.save")!=null?PropertiesUtil.getValue("service.method.save"):"save";
	/**2 service层 根据 id删除数据**/
	public static final String SERVICE_METHOD_DELETE_BY_ID=PropertiesUtil.getValue("service.method.delete.by.id")!=null?PropertiesUtil.getValue("service.method.delete.by.id"):"delete_by_id";
	/**3 service层 修改  数据**/
	public static final String SERVICE_METHOD_UPDATE=PropertiesUtil.getValue("service.methos.update")!=null?PropertiesUtil.getValue("service.methos.update"):"update";
	/**4 service层  根据id查询 **/
	public static final String SERVICE_METHOD_QUERY_BY_ID=PropertiesUtil.getValue("service.method.query.by.id")!=null?PropertiesUtil.getValue("service.method.query.by.id"):"query_by_id";	
	/**5 service层  查询数量 **/
	public static final String SERVICE_METHOD_QUERY_COUNT_NUM=PropertiesUtil.getValue("service.method.query.count.num")!=null?PropertiesUtil.getValue("service.method.query.count.num"):"count_num";	
	/**6 service层  分页查询 **/
	public static final String SERVICE_METHOD_QUERY_PAGE=PropertiesUtil.getValue("service.method.query.page")!=null?PropertiesUtil.getValue("service.method.query.page"):"query_page";
	/**7 service层  更新数据状态 **/
	public static final String SERVICE_METHOD_EDIT_STATUS=PropertiesUtil.getValue("service.method.edit.status")!=null?PropertiesUtil.getValue("service.method.edit.status"):"edit_status";
	/**8 service层  根据or查询唯一数据 **/
	public static final String SERVICE_METHOD_QUERY_UNIQUE_BY_OR=PropertiesUtil.getValue("service.method.query.unique.by.or")!=null?PropertiesUtil.getValue("service.method.query.unique.by.or"):"query_unique_by_or";
	/**9 service层  根据or 查询集合 **/
	public static final String SERVICE_METHOD_QUERY_LIST_BY_OR=PropertiesUtil.getValue("service.method.query.list.by.or")!=null?PropertiesUtil.getValue("service.method.query.list.by.or"):"query_list_by_or";
	/**10 service层  根据or 查询集合 **/
	public static final String SERVICE_METHOD_QUERY_LIST=PropertiesUtil.getValue("service.method.query.list")!=null?PropertiesUtil.getValue("service.method.query.list"):"query_list";
	/**11 service层  更新数据状态 **/
	public static final String SERVICE_METHOD_EDIT_STATUS_BATCH=PropertiesUtil.getValue("service.method.edit.status.batch")!=null?PropertiesUtil.getValue("service.method.edit.status.batch"):"edit_status_batch";
	/**12 service层  更新数据状态 **/
	public static final String SERVICE_METHOD_ADD=PropertiesUtil.getValue("service.method.add")!=null?PropertiesUtil.getValue("service.method.add"):"add";

}
