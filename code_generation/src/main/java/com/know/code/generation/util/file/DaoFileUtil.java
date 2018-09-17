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
 * @author lenovo
 *
 */
public class DaoFileUtil {
	public static String content(MTablePO tablePO,List<MTableFieldPO> fieldList){
		//po的类名
		String po_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME);
		
		StringBuffer attribute = new StringBuffer();
					 attribute.append("package ").append( PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.DAO_API_NAME)).append(" \n")
					 		  .append("import java.util.List; \n")
					 		  .append("import java.util.Map; \n")
					 		  .append("import ").append(PathUtil.createImportPath(tablePO.getTable_name(), NameEnumType.PO_NAME)).append(";\n")

			                  .append("/**\n")
			                  .append("  *").append( tablePO.getTable_comment() ).append("\n")
			                  .append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
			                  .append("  *@version 1.0.0 ").append(DateUtil.dateFormat(new Date(),DateUtil.DATE_FORMAT)).append(" \n")
			                  .append("  */\n")
			                  .append("public interface ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.DAO_API_NAME)).append("  { \n");
					 
		StringBuffer  methodStr = new StringBuffer(); 
					//增加数据
				      methodStr.append(" /** 1  \n ")
					 		   .append("  * 增加数据  \n ")
					 		   .append("  * @param po \n ")
					 		   .append("  */ \n ")
					 		   .append("  public Long ").append(MConstants.DAO_METHOD_INSERT).append(" ( ").append(po_name).append("  po ); \n ");
				      //删除数据	 
				      methodStr.append(" /** 2 \n ")
			 		   .append("  * 根据id删除  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public Long  ").append(MConstants.DAO_METHOD_DELETE_BY_ID).append("( ").append( po_name).append("  po ); \n ");		      
				      //修改数据	 
				      methodStr.append(" /** 3  \n ")
			 		   .append("  * 更新信息  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public Long ").append(MConstants.DAO_METHOD_UPDATE).append("( ").append( po_name).append("  po ); \n ");
						//根据id查询	 
				      methodStr.append(" /** 4 \n ")
			 		   .append("  * 根据id查询  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public ").append(po_name).append(" ").append(MConstants.DAO_METHOD_QUERY_BY_ID).append("( ").append( po_name ).append("  po ); \n ");
						//查询数量	
				      methodStr.append(" /** 5 \n ")
			 		   .append("  * 查询满足条件的数量  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public Long ").append(MConstants.DAO_METHOD_QUERY_COUNT_NUM).append("( ").append( po_name) .append("  po ); \n ");
				      
						//查询分页	 
				      methodStr.append(" /** 6 \n ")
			 		   .append("  * 查询分页  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public List<").append(po_name).append(">  ").append(MConstants.DAO_METHOD_QUERY_PAGE).append("( ").append( po_name).append("  po ); \n ");
				      
						//更改数据状态	 
				      methodStr.append(" /** 7 \n ")
			 		   .append("  * 根据ids 更改数据状态  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public Long  ").append(MConstants.DAO_METHOD_EDIT_STATUS).append("( ").append( po_name).append("  po ); \n ");
						
				      //根据or条件获取唯一数据 
				      methodStr.append(" /** 8 \n ")
			 		   .append("  * 根据or条件获取唯一数据  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public ").append(po_name).append(" ").append(MConstants.DAO_METHOD_QUERY_UNIQUE_BY_OR).append("( ").append( po_name ).append("  po ); \n ");
						
				      //根据or条件获取数据集合	 
				      methodStr.append(" /** 9 \n ")
			 		   .append("  * 根据 or 条件获取数据集合  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public List<").append(po_name).append("> ").append(MConstants.DAO_METHOD_QUERY_LIST_BY_OR).append("( ").append( po_name).append("  po ); \n ");
				    //根据 and 条件获取数据集合	 
				      methodStr.append(" /** 10 \n ")
			 		   .append("  * 根据 and 条件获取数据集合  \n ")
			 		   .append("  * @param po \n ")
			 		   .append("  */ \n ")
			 		   .append("  public List<").append(po_name).append("> ").append(MConstants.DAO_METHOD_QUERY_LIST).append("( ").append( po_name).append("  po ); \n ");
				    //批量修改状态
				      methodStr.append( edit_status_batch(po_name) );	      
		attribute.append(methodStr)
		         .append("} //end class");
		
		  return attribute.toString();            
		         
	}
	
	/** 批量修改状态的方法***/
	public static String edit_status_batch(String po_name){
		StringBuffer methodStr = new StringBuffer();
	      methodStr.append(" /** 11 \n ")
		   .append("  * 根据ids 更改数据状态  \n ")
		   .append("  * @param po \n ")
		   .append("  */ \n ")
		   .append("  public Long  ").append(MConstants.DAO_METHOD_EDIT_STATUS_BATCH).append("( ").append( po_name).append("  po ); \n ");
	     
	     return methodStr.toString();
	}
	
	
}
