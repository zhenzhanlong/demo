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
public class WebServiceFileUtil {
	public static String content(MTablePO tablePO) {
		// vo的类名
		String vo_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME);
		// form的类名
		String form_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME);
		// biz api名称
		String biz_api_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.BIZ_API_NAME);
		//String biz_api_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME);
		// biz impl 名称
		String biz_impl_name = NameUtil.createPrivateName(tablePO.getTable_name(), NameEnumType.BIZ_NAME);
		//String biz_impl_name = NameUtil.createPrivateName(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME);
		
		StringBuffer attribute = new StringBuffer();
		attribute.append("package ").append(PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME)).append(" \n")
				.append("import java.util.List; \n")
				.append("import java.util.Map; \n")
				.append("import org.slf4j.Logger; \n ")
				.append("import org.slf4j.LoggerFactory; \n")
				.append("import ").append(PathUtil.createImportPath(tablePO.getTable_name(), NameEnumType.BIZ_API_NAME)).append(";\n")
				.append(MConstants.CONSUMER_LOGIN_VO_PATH).append("\n")
				.append(MConstants.RESULT_LIST_VO_PATH).append("\n")
				.append(MConstants.RESULT_OBJECT_VO_PATH).append("\n")
				.append(MConstants.RESULT_BOOLEAN_VO_PATH).append("\n")

				.append("/**\n").append("  *").append(tablePO.getTable_comment()).append("\n")
				.append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
				.append("  *@version 1.0.0 ").append(DateUtil.dateFormat(new Date(), DateUtil.DATE_FORMAT)).append(" \n")
				.append("  */\n")
				.append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME)).append("{ \n")
				.append("		/*** 日志*/ \n")
				.append("		private Logger log = LoggerFactory.getLogger(this.getClass());\n \n")
				.append("		@Resource \n")
				.append("		private ").append(biz_api_name).append(" ").append(biz_impl_name).append(";\n\n")
				.append("		public ").append(biz_api_name).append(" ").append(FieldUtil.getMethod(biz_impl_name))
				.append("(){ \n").append("			return this.").append(biz_impl_name).append(";\n").append("		}\n\n")
				.append("		public void ").append(FieldUtil.setMethod(biz_impl_name))
				.append("(").append(biz_api_name).append(" ").append(biz_impl_name).append("){ \n")
				.append("			 this.").append(biz_impl_name).append(" = ").append(biz_impl_name)
				.append(";\n").append("		}\n \n \n");

		StringBuffer methodStr = new StringBuffer();
		//添加界面跳转
		methodStr.append(view_add(form_name));
		//编辑界面跳转
		methodStr.append(view_edit(vo_name,biz_impl_name));
		//列表界面跳转
		methodStr.append(view_list(form_name));
		//新增数据
		methodStr.append(insert(form_name,biz_impl_name));
		//编辑数据
		methodStr.append(update(form_name,biz_impl_name));
		//删除数据
		methodStr.append(delete(biz_impl_name));
		//禁用
		methodStr.append(disable(biz_impl_name));
		//启用
		methodStr.append(enable(biz_impl_name));
		//查询数据集合
		methodStr.append(queryList(vo_name,form_name,biz_impl_name));
		//查询分页
		methodStr.append(queryPage(vo_name,form_name,biz_impl_name));

		attribute.append(methodStr).append("} ");

		return attribute.toString();

	}

	// 添加界面跳转
	public static String view_add(String form_name) {
		StringBuilder str = new StringBuilder();
		str.append("/** 添加界面跳转 */ \n")
		   .append("public ViewAndModel view_add(").append(form_name).append("form,ViewAndModel model){ \n")
		   .append("		log.debug(\"添加界面方法调用\"); \n")
		   .append("		model.setViewName(\"\"); \n")
		   .append("		return model; \n ")
		   .append("}\n \n \n");
		
		return str.toString();
	}

	// 编辑界面跳转
	public static String view_edit(String vo_name,String biz_impl_name) {
		StringBuilder str = new StringBuilder();
					  str.append("public ViewAndModel view_edit(ViewAndModel model,Long id) {\n")
					  	 .append("		log.debug(\"编辑界面方法调用\"); \n")
					  	 .append("		model.setViewName(\"\"); \n")
					  	 .append("		ResultObjectVO<").append(vo_name).append("> result =").append(biz_impl_name).append(".")
					  	 						   .append(MConstants.SERVICE_METHOD_QUERY_BY_ID).append("(id); \n")
					  	 .append("		model.addObject(\"objVO\", result.getObjVo()); \n")
					  	 .append("return model;\n")
					  	 .append("}");
		return str.toString();
	}

	// 列表界面跳转
	public static String view_list(String form_name) {
		StringBuilder str = new StringBuilder();
		 str.append(" /** 数据列表列表 */ \n ")
	  	 //.append("public ViewAndModel list_view(").append(form_name).append(" form) \n");
		 .append("public ViewAndModel list_view( ").append(form_name).append(" form ,ViewAndModel model){ \n")
	  	 .append("		log.debug(\"列表界面方法调用\");\n")
	  	 .append("		model.setViewName(\"\"); \n")
	  	 .append("		return model; \n")
	  	 .append("} \n");
		
		return str.toString();
	}

	// 新增数据
	public static String insert(String form_name,String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultBooleanVO ").append(MConstants.SERVICE_METHOD_ADD).append("(").append(form_name).append(" form, MConsumerLoginVO loginVO){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_ADD).append("(form,loginVO);\n")
	  	 .append("}\n");
		return str.toString();
	}

	// 编辑数据
	public static String update(String form_name,String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultBooleanVO ").append(MConstants.SERVICE_METHOD_UPDATE).append("(").append(form_name).append(" form, MConsumerLoginVO loginVO){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_UPDATE).append("(form,loginVO);\n")
	  	 .append("}\n");
		return str.toString();
	}

	// 删除数据
	public static String delete(String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultBooleanVO ").append(MConstants.SERVICE_METHOD_DELETE_BY_ID).append("(Long id, MConsumerLoginVO loginVO){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_DELETE_BY_ID).append("(id,loginVO);\n")
	  	 .append("}\n");
		return str.toString();
	}

	// 禁用
	public static String disable(String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultBooleanVO disable(Long id, MConsumerLoginVO loginVO){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_EDIT_STATUS).append("(MDataStatusType.DATA_DISABLE, id,loginVO);\n")
	  	 .append("}\n");
		return str.toString();
	}

	// 启用
	public static String enable(String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultBooleanVO enable(Long id, MConsumerLoginVO loginVO){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_EDIT_STATUS).append("(MDataStatusType.DATA_AVAILABLE, id,loginVO);\n")
	  	 .append("}\n");
		return str.toString();
	}

	// 查询集合
	public static String queryList(String vo_name,String form_name,String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultListVO<").append(vo_name).append(">").append(MConstants.SERVICE_METHOD_QUERY_LIST).append("(").append(form_name).append(" form){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_LIST).append("(form);\n")
	  	 .append("}\n");
		return str.toString();
	}

	// 分页
	public static String queryPage(String vo_name,String form_name,String biz_impl_name) {
		StringBuilder str = new StringBuilder();
		 str.append("public ResultListVO<").append(vo_name).append(">").append(MConstants.SERVICE_METHOD_QUERY_PAGE).append("(").append(form_name).append(" form){\n ")
		 .append("return ").append(biz_impl_name).append(".").append(MConstants.SERVICE_METHOD_QUERY_PAGE).append("(form);\n")
	  	 .append("}\n");
		return str.toString();
	}
}
