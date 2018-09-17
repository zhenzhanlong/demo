package com.know.code.generation.util.file;

import java.util.Date;
import java.util.List;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.NameEnumType;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;
import com.know.code.generation.util.DateUtil;
import com.know.code.generation.util.FieldUtil;
import com.know.code.generation.util.NameUtil;
import com.know.code.generation.util.PathUtil;

/**
 * 文件生成帮助类
 * 
 * @author lenovo
 *
 */
public class BizFileUtil {
	public static String content(MTablePO tablePO, List<MTableFieldPO> fieldList) {

		// vo的类名
		String vo_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME);
		// form的类名
		String form_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME);
		// service api名称
		String service_api_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_API_NAME);
		// service impl 名称
		String service_impl_name = NameUtil.createPrivateName(tablePO.getTable_name(), NameEnumType.SERVICE_API_NAME);
		StringBuffer attribute = new StringBuffer();
		// 拼接文件头
		attribute.append(head(tablePO, service_api_name, service_impl_name));

		StringBuffer methodStr = new StringBuffer();
		// 增加数据
		methodStr.append(insert(form_name, service_impl_name));
		// 删除数据
		methodStr.append(delete(service_impl_name));
		// 修改数据
		methodStr.append(update( form_name, service_impl_name));
		// 根据id查询
		methodStr.append(queryById(vo_name, service_impl_name));
		// 分页
		methodStr.append(page(vo_name, form_name, service_impl_name));
		// 更改数据状态
		methodStr.append(edit_status(service_impl_name));
		
		// 根据or条件获取唯一数据
		methodStr.append(queryUniqueByOr(vo_name, form_name, service_impl_name));
		// 根据or条件获取列表数据
		methodStr.append(queryListByOr(vo_name, form_name, service_impl_name));
		// 获取列表数据
		methodStr.append(queryList(vo_name, form_name, service_impl_name));
		// 更改数据状态
		methodStr.append(edit_status_batch(service_impl_name));
		attribute.append(methodStr).append("} //end class");

		return attribute.toString();

	}

	// 拼接文件头
	public static String head(MTablePO tablePO, String service_api_name, String service_impl_name) {
		StringBuffer attribute = new StringBuffer();
		attribute.append("package ").append(PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.BIZ_NAME)).append("import java.util.List; \n").append("import java.util.Date; \n")
				.append("import com.sx.cache.Cache; \n").append("import com.sx.manage.constants.MDataStatusType; \n")
				.append("import ").append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.VO_NAME))
				.append("import ").append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.FORM_NAME))
				.append("import ").append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.SERVICE_API_NAME))
				.append("import com.sx.common.util.json.JackJsonUtil; \n")
				.append("import com.sx.manage.mybatis.vo.consumer.login.MConsumerLoginVO;\n")
				.append("import com.sx.common.util.BeanUtils;\n")
				.append("import com.sx.common.util.NumberUtil;\n")
				.append("import org.slf4j.Logger;\n")
				.append("import org.slf4j.LoggerFactory;\n")
				.append(MConstants.CONSUMER_LOGIN_VO_PATH).append("\n")
				.append(MConstants.RESULT_LIST_VO_PATH).append("\n")
				.append(MConstants.RESULT_OBJECT_VO_PATH).append("\n")
				.append(MConstants.RESULT_BOOLEAN_VO_PATH).append("\n")

				.append("/**\n").append("  *").append(tablePO.getTable_comment()).append("\n").append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
				.append("  *@version 1.0.0 ").append(DateUtil.dateFormat(new Date(), DateUtil.DATE_FORMAT)).append(" \n").append("  */\n")
				.append("@Service(\"").append(NameUtil.createSpringServiceName(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_NAME))).append("\") \n")
				.append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.BIZ_NAME))
				.append("  implements ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.BIZ_API_NAME)).append("{ \n")
				.append("		/*** 日志*/ \n")
				.append("		private Logger logger = LoggerFactory.getLogger(this.getClass());\n \n")
				.append("		@Resource\n")
				.append("		private ").append(service_api_name).append(" ").append(service_impl_name).append(";\n\n")
				.append("     @Resource(name=\"redisCache\") \n")
				.append("		protected Cache cache; \n")
				.append("		public ").append(service_api_name).append(" ").append(FieldUtil.getMethod(service_impl_name)).append("(){ \n")
				.append("			return this.").append(service_impl_name).append(";\n")
				.append("		}\n\n")
				.append("		public void ").append(FieldUtil.setMethod(service_impl_name)).append("(").append(service_api_name).append(" ").append(service_impl_name).append("){ \n")
				.append("			 this.").append(service_impl_name).append(" = ").append(service_impl_name).append(";\n")
				.append("		}\n \n \n");

		return attribute.toString();
	}

	// 新增数据
	public static String insert(String form_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		// 增加数据
		methodStr.append("	/** 1增加数据  */ \n public ").append(MConstants.RESULT_BOOLEAN_VO).append("  ").append(MConstants.SERVICE_METHOD_ADD).append("(").append(form_name).append("  form,").append(MConstants.CONSUMER_LOGIN_VO).append("   loginVO){ \n ")
				.append("		logger.debug(\"方法 ").append(MConstants.SERVICE_METHOD_ADD).append("执行 form ={},loginVO={}\",form,loginVO );\n	").append("     return ").append(service_impl_name)
				.append(".").append(MConstants.SERVICE_METHOD_ADD).append("(form); \n").append("	}// \n\n\n");
		return methodStr.toString();
	}

	// 更新数据
	public static String update(String form_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("	/** 3更新数据信息 */ \n public ").append(MConstants.RESULT_BOOLEAN_VO).append("  ").append(MConstants.SERVICE_METHOD_UPDATE).append("(").append(form_name)
				.append("  form , ").append(MConstants.CONSUMER_LOGIN_VO).append("   loginVO){ \n ").append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_UPDATE).append("(form); \n")
				.append("	}  \n \n \n");
		return methodStr.toString();
	}

	//删除数据
	public static String delete(String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("	/** 2根据id删除数据 */ \n public ").append(MConstants.RESULT_BOOLEAN_VO).append("  ").append(MConstants.SERVICE_METHOD_DELETE_BY_ID).append("( Long id,").append(MConstants.CONSUMER_LOGIN_VO).append("  loginVO  ){ \n ")
				.append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_DELETE_BY_ID).append("(id); \n").append("	} \n \n \n");
		return methodStr.toString();
	}

	// 根据id查询数据
	public static String queryById(String vo_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("	/** 4根据id查询数据*/ \n public ").append(MConstants.RESULT_OBJECT_VO).append("<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_BY_ID).append("( Long id ){ \n ")
				.append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_BY_ID).append("(id); \n").append(" } \n \n \n");
		return methodStr.toString();
	}

	// 查询分页信息
	public static String page(String vo_name, String form_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		// 查询分页
		methodStr.append("	/** 5查询分页*/ \n public ").append(MConstants.RESULT_LIST_VO).append("<").append(vo_name).append(">  ").append(MConstants.SERVICE_METHOD_QUERY_PAGE).append("( ").append(form_name)
				.append("  form ){ \n ").append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_PAGE).append("(form); \n").append("	} \n \n \n");
		return methodStr.toString();
	}

	// 更改数据状态
	public static String edit_status(String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("	/** 6 根据ids 更改数据状态 */ \n public ").append(MConstants.RESULT_BOOLEAN_VO).append("  ").append(MConstants.SERVICE_METHOD_EDIT_STATUS).append("( MDataStatusType data_status,Long id,MConsumerLoginVO loginVO ){ \n ")
				.append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_EDIT_STATUS).append("(data_status,id); \n").append("    } \n \n \n");

		return methodStr.toString();
	}

	// 根据or条件查询唯一数据
	public static String queryUniqueByOr(String vo_name, String form_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("	/** 7 根据or条件获取唯一数据*/ \n public ").append(MConstants.RESULT_OBJECT_VO).append("<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_UNIQUE_BY_OR).append("( ")
				.append(form_name).append("  form ){ \n ").append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_UNIQUE_BY_OR).append("(form); \n")
				.append("	}\n \n \n ");
		return methodStr.toString();
	}

	// 根据or条件查询列表数据
	public static String queryListByOr(String vo_name, String form_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		// 根据or条件获取数据集合
		methodStr.append("	/** 8 根据 or 条件获取数据集合 */ \n public ").append(MConstants.RESULT_LIST_VO).append("<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_LIST_BY_OR).append("( ").append(form_name)
				.append("  form ){ \n ").append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_LIST_BY_OR).append("(form); \n").append("	}\n \n \n ");
		return methodStr.toString();
	}

	// 查询列表数据
	public static String queryList(String vo_name, String form_name, String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		// 根据or条件获取数据集合
		methodStr.append("	/**9获取数据集合 */ \n public ").append(MConstants.RESULT_LIST_VO).append("<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_LIST).append("( ").append(form_name)
				.append("  form ){ \n ").append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_LIST).append("(form); \n").append("	}\n \n \n ");
		return methodStr.toString();
	}

	// 批量更改数据状态
	public static String edit_status_batch(String service_impl_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append("	/** 10 根据ids 更改数据状态 */ \n public ").append(MConstants.RESULT_BOOLEAN_VO).append("  ").append(MConstants.SERVICE_METHOD_EDIT_STATUS_BATCH).append("( MDataStatusType data_status,Long[] ids,MConsumerLoginVO loginVO ){ \n ")
				.append("     return ").append(service_impl_name).append(".").append(MConstants.SERVICE_METHOD_EDIT_STATUS_BATCH).append("(data_status,ids); \n").append("    } \n \n \n");

		return methodStr.toString();
	}
}
