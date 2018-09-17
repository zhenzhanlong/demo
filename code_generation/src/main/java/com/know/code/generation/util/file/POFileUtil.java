package com.know.code.generation.util.file;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.know.code.generation.constants.type.NameEnumType;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;
import com.know.code.generation.util.DateUtil;
import com.know.code.generation.util.FieldUtil;
import com.know.code.generation.util.ImportClassUtil;
import com.know.code.generation.util.NameUtil;
import com.know.code.generation.util.PathUtil;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class POFileUtil {
	public static String content(MTablePO tablePO,List<MTableFieldPO> fieldList){
		//需要引入的类，类型的临时存储集合
		Set<String> hasImport = new HashSet<String>();
		
		StringBuffer importStr = new StringBuffer();
					 importStr.append("package ").append( PathUtil.createPackagePath(tablePO.getTable_name(),NameEnumType.PO_NAME)).append(" \n")
			                  .append("import java.io.Serializable; \n")
			                  .append("import com.sx.manage.mybatis.po.BasePO; \n");
					 
		StringBuffer attribute = new StringBuffer();	
					 
					 attribute.append("/**\n")
			                  .append("  *").append( tablePO.getTable_comment() ).append("\n")
			                  .append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
			                  .append("  *@version 1.0.0 ").append(DateUtil.dateToStr(new Date())).append(" \n")
			                  .append("  */\n")
			                  .append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME)).append(" extends BasePO implements Serializable { \n")
			                  .append("	 /** 序列化 */\n")
			                  .append("	 private static final long serialVersionUID = ").append(System.currentTimeMillis()).append("L;\n");
					 
		StringBuffer  methodStr = new StringBuffer(); 
		String fieldName = null;
		String fieldType = null;
		for( MTableFieldPO filedPO : fieldList){
			String[] arrayField=FieldUtil.fieldNameType(filedPO);
			
			String getMethod = null; 
	         if( null != arrayField){
	        	 importStr.append(arrayField[0]);
	        	 fieldName = arrayField[1];
	        	 fieldType = arrayField[2];
	        	 getMethod = arrayField[3];
	         }
			//根据 数据类型，引入类
			String importTemp =  ImportClassUtil.poImport( filedPO.getData_type());
			//没有被引入过，引入。比如Date有的类里面有多个Date字段，只需引入一次
			if( StringUtils.isNotBlank( importTemp )&&!hasImport.contains(importTemp)){
				importStr.append(importTemp);
				//放入已经引入的结果集合
				hasImport.add(importTemp);
			}
			String[] importArray = FieldUtil.importField(filedPO, fieldName, fieldType, getMethod);
			if( null != importArray){
				attribute.append(importArray[0]);
				methodStr.append(importArray[1]);
			}
			
		}
		attribute.append(" \n\n");
		/** 构造方法 **/
		attribute.append("	public ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME)).append("(){ \n")
		         .append("		super() ; \n")
		         .append("	} \n");
		/** 构造方法**/
		attribute.append("	public ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME))
		         .append("(").append(FieldUtil.fieldType( fieldList.get(0).getData_type())).append("  ")
		                     .append(  fieldList.get(0).getColumn_name().toLowerCase() )
		         .append(" ){\n")
		         .append("		super() ; \n")
		         .append("		this.").append( fieldList.get(0).getColumn_name().toLowerCase()).append(" = ").append(fieldList.get(0).getColumn_name().toLowerCase()).append(" ;\n")
		         .append("	} \n");
		//拼接get，set方法
		attribute.append(methodStr)
		         .append("} //end class");
		//拼接为返回str
		StringBuffer resultStr = new StringBuffer();
					 resultStr.append(importStr)
					          .append(attribute);
					 
		  return resultStr.toString();            
		         
	}
}
