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
public class ControllerFileUtil {
	public static String content(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		// vo的类名
		String vo_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME);
		// form的类名
		String form_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME);
		// dao api名称
		String web_service_api_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME);
		// dao impl 名称
		String web_service_impl_name = NameUtil.createPrivateName(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME);

		StringBuffer attribute = new StringBuffer();
		attribute.append("package ").append(PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.CONTROLLER_NAME)).append(" \n").append("import java.util.List; \n")
				.append("import java.util.Map; \n").append("import com.sx.manage.controller.base.MBaseController;\n ").append("import org.slf4j.Logger; \n ")
				.append("import org.slf4j.LoggerFactory; \n").append("import ").append(PathUtil.createImportPath(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME)).append(";\n")
				.append(MConstants.CONSUMER_LOGIN_VO_PATH).append("\n").append(MConstants.RESULT_LIST_VO_PATH).append("\n").append(MConstants.RESULT_OBJECT_VO_PATH).append("\n")
				.append(MConstants.RESULT_BOOLEAN_VO_PATH).append("\n")

				.append("/**\n").append("  *").append(tablePO.getTable_comment()).append("\n").append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
				.append("  *@version 1.0.0 ").append(DateUtil.dateFormat(new Date(), DateUtil.DATE_FORMAT)).append(" \n").append("  */\n").append("@Controller \n ").append("@RequestMapping(\"\") \n")
				.append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.CONTROLLER_NAME)).append("  extends MBaseController{ \n").append("		/*** 日志*/ \n")
				.append("		private Logger logger = LoggerFactory.getLogger(this.getClass());\n \n").append("		@Resource\n").append("		private ")
				.append(web_service_api_name).append(" ").append(web_service_impl_name).append(";\n\n").append("		public ").append(web_service_api_name).append(" ")
				.append(FieldUtil.getMethod(web_service_impl_name)).append("(){ \n").append("			return this.").append(web_service_impl_name).append(";\n").append("		}\n\n").append("		public void ")
				.append(FieldUtil.setMethod(web_service_impl_name)).append("(").append(web_service_api_name).append(" ").append(web_service_impl_name).append("){ \n").append("			 this.")
				.append(web_service_impl_name).append(" = ").append(web_service_impl_name).append(";\n").append("		}\n \n \n");

		StringBuffer methodStr = new StringBuffer();
		// 数据列表列界面
		methodStr.append(view_list(form_name, web_service_impl_name));
		// 添加界面标砖
		methodStr.append(view_add(form_name, web_service_impl_name));
		// 编辑界面跳转
		methodStr.append(view_edit(vo_name, web_service_impl_name));
		// 数据新增
		methodStr.append(add(form_name, web_service_impl_name));
		// 数据編輯
		methodStr.append(update(form_name, web_service_impl_name));
		// 根据id删除数据
		methodStr.append(delete(web_service_impl_name));
		// 翻页信息获取
		methodStr.append(page(vo_name, form_name, web_service_impl_name));
		// 启用
		methodStr.append(enable(web_service_impl_name));
		// 禁用
		methodStr.append(disable(web_service_impl_name));
		// 下拉框选择数据
		methodStr.append(ajax_list(vo_name, form_name, web_service_impl_name));

		attribute.append(methodStr).append("} //end class");

		return attribute.toString();

	}

	// 列表界面跳转
	public static String view_list(String form_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append("	/**数据列表列表*/ \n ").append(" 	@RequestMapping(value=\"list_view\")\n").append(" 	public ViewAndModel list_view ( ").append(form_name).append(" form  ){ \n ")
				.append("      logger.debug(\"列表界面方法调用\");  \n")
				.append("  		return ").append(web_service_impl_name).append(".list_view(form,createView()); \n")
				.append("	}\n\n");
		return str.toString();
	}

	// 添加界面跳转
	public static String view_add(String form_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append("	/**添加界面跳转*/\n").append("		@RequestMapping(value=\"view_add\") \n").append("		public ViewAndModel view_add(").append(form_name).append(" form){ \n")
				.append("      logger.debug(\"添加界面方法调用\");  \n")
				.append("  		return ").append(web_service_impl_name).append(".view_add(form,createView()); \n")
				.append("		} \n \n \n");
		return str.toString();
	}

	// 编辑界面跳转
	public static String view_edit(String vo_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append("	/**编辑界面跳转*/ \n ").append("		@RequestMapping(value=\"view_edit/{id}\") \n").append("		public ViewAndModel view_edit(@PathVariable Long id){\n")
				.append("      logger.debug(\"编辑界面方法调用\");  \n")
				.append("  		return ").append(web_service_impl_name).append(".view_edit(createView(),id); \n")
				.append(" 	}\n \n \n");
		return str.toString();
	}

	// 数据新增
	public static String add(String form_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append("	/**数据新增*/ \n").append(" 	@RequestMapping(value=\"save\") \n").append("     @ResponseBody \n").append(" 	public  ResultBooleanVO add( ").append(form_name)
				.append(" form ,BindingResult errors){ \n").append("      logger.debug(\"新增方法调用\");  \n").append(" 		String error = this.fieldValidate(form, errors);\n")
				.append(" 		if( null != error){\n").append(" 			return new  ResultBooleanVO(false,null,error); \n").append(" 		}\n")
				.append("  		return ").append(web_service_impl_name).append(".").append(MConstants.SERVICE_METHOD_ADD).append("(form, loginConsumer()); \n")
				.append(" 	}\n\n\n");
		return str.toString();
	}

	// 数据编辑
	public static String update(String form_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append("	/**数据編輯*/ \n").append(" 	@RequestMapping(value=\"update\") \n").append("     @ResponseBody \n").append(" 	public  ResultBooleanVO update( ").append(form_name)
				.append(" form ,BindingResult errors){ \n").append("      logger.debug(\"編輯方法调用\");  \n").append(" 		String error = this.fieldValidate(form, errors);\n")
				.append(" 		if( null != error){\n").append(" 			return new  ResultBooleanVO(false,null,error); \n").append(" 		}\n")
				.append("  		return ").append(web_service_impl_name).append(".").append(MConstants.SERVICE_METHOD_UPDATE).append("(form, loginConsumer());\n")
				.append(" 	}\n\n\n");
		return str.toString();
	}

	// 删除
	public static String delete(String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append(" 	/**根据id删除数据*/\n").append(" 	@RequestMapping(value=\"delete\")\n").append(" 	@ResponseBody\n").append(" 	public ResultBooleanVO delete(").append(" Long id){\n")
				.append("      logger.debug(\"删除方法调用\");  \n")
				.append(" 		return ").append(web_service_impl_name).append(".").append(MConstants.SERVICE_METHOD_DELETE_BY_ID)
				.append("(id,this.loginConsumer());\n").append(" 	}\n\n\n");
		return str.toString();
	}

	// 翻页
	public static String page(String vo_name, String form_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append(" 	/**翻页信息获取*/\n").append(" 	@RequestMapping(value=\"page\")\n").append(" 	@ResponseBody\n").append(" 	public ResultListVO<").append(vo_name).append("> page(").append(form_name)
				.append(" form){\n").append("      logger.debug(\"翻页方法调用\");  \n").append(" 		return ").append(web_service_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_PAGE)
				.append("(form);\n").append(" 	}\n\n\n");
		return str.toString();
	}

	// 启用
	public static String enable(String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append(" 	/****启用**/ \n").append(" 	@RequestMapping(value=\"enable\") \n").append(" 	@ResponseBody \n").append(" 	public ResultBooleanVO enable(Long id  ){ \n")
				.append("      logger.debug(\"启用方法调用\");  \n").append(" 		  return ").append(web_service_impl_name).append(".enable")
				.append("(id,loginConsumer()); \n").append(" 	} \n\n\n");
		return str.toString();
	}

	// 禁用
	public static String disable(String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append(" 	/**** 禁用  **/\n").append(" 	@RequestMapping(value=\"disable\") \n").append(" 	@ResponseBody \n").append(" 	public ResultBooleanVO disable(Long id  ){\n")
				.append("      logger.debug(\"禁用方法调用\");  \n").append(" 		   return ").append(web_service_impl_name).append(".disable")
				.append("(id,loginConsumer());\n").append(" 	} \n \n\n");
		return str.toString();
	}

	// 下拉框选择数据
	public static String ajax_list(String vo_name, String form_name, String web_service_impl_name) {
		StringBuilder str = new StringBuilder();
		str.append("	/**数据下拉列表**/\n").append("	@RequestMapping(value=\"ajax_list\")\n").append("	@ResponseBody\n").append("	public ResultListVO<").append(vo_name).append("> ajax_list(")
				.append(form_name).append(" form){\n").append("      logger.debug(\"数据下拉列表方法调用\");  \n").append("			return ").append(web_service_impl_name).append(".")
				.append(MConstants.SERVICE_METHOD_QUERY_LIST).append("( form);\n").append("	}\n");
		return str.toString();
	}
}
