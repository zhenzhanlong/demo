package com.know.code.generation.util.file;

import java.util.Date;
import java.util.List;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.NameEnumType;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;
import com.know.code.generation.util.DateUtil;
import com.know.code.generation.util.NameUtil;
import com.know.code.generation.util.PathUtil;

/**
 * 文件生成帮助类
 * 
 * @author lenovo
 *
 */
public class BizApiFileUtil {
	public static String content(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		/*
		 * // 截取的表名称 String table_name =
		 * NameUtil.subTableName(tablePO.getTable_name());
		 */
		// vo的类名
		String vo_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME);
		// form的类名
		String form_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME);

		StringBuffer attribute = new StringBuffer();
		// 拼接文件头
		attribute.append(head(tablePO));

		StringBuffer methodStr = new StringBuffer();
		// 新增数据
		methodStr.append(insert(form_name));
		// 删除数据
		methodStr.append(delete());
		// 编辑数据
		methodStr.append(update(form_name));
		// 根据id查询
		methodStr.append(queryById(vo_name));
		// 查询分页
		methodStr.append(page(vo_name, form_name));
		// 更改数据状态
		methodStr.append(edit_status());
		// 批量更新数据状态
		methodStr.append(edit_status_batch());
		// 根据or条件获取唯一数据
		methodStr.append(queryUniqueByOr(vo_name, form_name));
		// 根据or条件获取数据集合
		methodStr.append(queryListByOr(vo_name, form_name));
		// 取数据集合
		methodStr.append(queryList(vo_name, form_name));
		attribute.append(methodStr).append("} //end class");

		return attribute.toString();

	}

	// 拼接文件头
	public static String head(MTablePO tablePO) {
		StringBuffer attribute = new StringBuffer();
		attribute.append("package ").append(PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.BIZ_API_NAME)).append("import java.util.List; \n")
				.append("import com.sx.manage.constants.MDataStatusType; \n").append("import ").append(PathUtil.createImportPath(tablePO.getTable_name(), NameEnumType.VO_NAME)).append(";\n")
				.append("import ").append(PathUtil.createImportPath(tablePO.getTable_name(), NameEnumType.FORM_NAME)).append(";\n")
				.append(MConstants.CONSUMER_LOGIN_VO_PATH).append("\n")
				.append(MConstants.RESULT_LIST_VO_PATH).append("\n")
				.append(MConstants.RESULT_OBJECT_VO_PATH).append("\n")
				.append(MConstants.RESULT_BOOLEAN_VO_PATH).append("\n")

				.append("/**\n").append("  *").append(tablePO.getTable_comment()).append("\n").append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
				.append("  *@version 1.0.0 ").append(DateUtil.dateFormat(new Date(), DateUtil.DATE_FORMAT)).append(" \n").append("  */\n").append("public interface ")
				.append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.BIZ_API_NAME)).append("  { \n");

		return attribute.toString();
	}

	// 新增数据
	public static String insert(String form_name) {
		StringBuffer methodStr = new StringBuffer();
		// 增加数据
		methodStr.append(" /** 1  \n ").append("  * 增加数据  \n ").append("  * @param form \n ").append("  * @param loginVO \n ").append("  */ \n ")
				.append("  public ").append(MConstants.RESULT_BOOLEAN_VO).append("  ")
				.append(MConstants.SERVICE_METHOD_ADD).append(" ( ").append(form_name).append("  form ,MConsumerLoginVO loginVO ); \n ");

		return methodStr.toString();
	}

	//删除用户数据
	public static String delete() {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 2 \n ").append("  * 根据id删除用户  \n ").append("  * @param id \n ").append("  * @param loginVO \n ").append("  */ \n ").append("  public ResultBooleanVO  ")
				.append(MConstants.DAO_METHOD_DELETE_BY_ID).append("(Long id,MConsumerLoginVO loginVO  ); \n ");

		return methodStr.toString();
	}

	// 编辑数据
	public static String update(String form_name) {
		StringBuffer methodStr = new StringBuffer();
		// 增加数据
		methodStr.append(" /** 3  \n ").append("  * 编辑数据  \n ").append("  * @param form \n ").append("  * @param loginVO \n ").append("  */ \n ").append("  public ResultBooleanVO ")
				.append(MConstants.SERVICE_METHOD_UPDATE).append(" ( ").append(form_name).append("  form ,MConsumerLoginVO loginVO ); \n ");

		return methodStr.toString();
	}

	// 根据id查询数据
	public static String queryById(String vo_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 4 \n ").append("  * 根据id查询用户  \n ").append("  * @param id \n ").append("  */ \n ").append("  public ResultObjectVO<").append(vo_name).append("> ")
				.append(MConstants.DAO_METHOD_QUERY_BY_ID).append("(Long id ); \n ");

		return methodStr.toString();
	}

	// 查询分页信息
	public static String page(String vo_name, String form_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 5 \n ").append("  * 查询分页  \n ").append("  * @param form \n ").append("  */ \n ").append("  public ResultListVO<").append(vo_name).append(">  ")
				.append(MConstants.DAO_METHOD_QUERY_PAGE).append("( ").append(form_name).append("  form ); \n ");

		return methodStr.toString();
	}

	// 更改数据状态
	public static String edit_status() {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 6 \n ").append("  * 根据ids 更改数据状态  \n ").append("  * @param data_status \n ").append("  * @param id \n ").append("  */ \n ").append("  public ResultBooleanVO  ")
				.append(MConstants.DAO_METHOD_EDIT_STATUS).append("( MDataStatusType data_status,Long id,MConsumerLoginVO loginVO  ); \n ");

		return methodStr.toString();
	}

	// 更改数据状态
	public static String edit_status_batch() {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 7 \n ").append("  * 根据ids 更改数据状态  \n ").append("  * @param data_status \n ").append("  * @param ids \n ").append("  */ \n ").append("  public ResultBooleanVO  ")
				.append(MConstants.DAO_METHOD_EDIT_STATUS_BATCH).append("( MDataStatusType data_status,Long[] ids,MConsumerLoginVO loginVO  ); \n ");

		return methodStr.toString();
	}

	// 根据or条件查询唯一数据
	public static String queryUniqueByOr(String vo_name, String form_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 8 \n ").append("  * 根据or条件获取唯一数据  \n ").append("  * @param form \n ").append("  */ \n ").append("  public ResultObjectVO<").append(vo_name).append("> ")
				.append(MConstants.DAO_METHOD_QUERY_UNIQUE_BY_OR).append("( ").append(form_name).append("  form ); \n ");

		return methodStr.toString();
	}

	// 根据or条件查询列表数据
	public static String queryListByOr(String vo_name, String form_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 9 \n ").append("  * 根据 or 条件获取数据集合  \n ").append("  * @param form \n ").append("  */ \n ").append("  public ResultListVO<").append(vo_name).append("> ")
				.append(MConstants.DAO_METHOD_QUERY_LIST_BY_OR).append("( ").append(form_name).append("  form ); \n ");

		return methodStr.toString();
	}

	// 根据条件查询列表数据
	public static String queryList(String vo_name, String form_name) {
		StringBuffer methodStr = new StringBuffer();
		methodStr.append(" /** 10 \n ").append("  * 获取数据集合  \n ").append("  * @param form \n ").append("  */ \n ").append("  public ResultListVO<").append(vo_name).append("> ")
				.append(MConstants.SERVICE_METHOD_QUERY_LIST).append("( ").append(form_name).append("  form ); \n ");

		return methodStr.toString();
	}
}
