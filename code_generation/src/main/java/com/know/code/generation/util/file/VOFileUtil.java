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
import com.know.code.generation.util.PropertiesUtil;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class VOFileUtil {
	public static String content(MTablePO tablePO,List<MTableFieldPO> fieldList){
		//po的类名
		String po_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME);
		
		//vo是否集成po
		String vo_extend_po_str = PropertiesUtil.getValue(MConstants.VO_EXTEND_PO);
		Boolean vo_extend_po = false;
		if(MConstants.YES.equalsIgnoreCase(vo_extend_po_str)){
			vo_extend_po=true;
		}
		//是否驼峰显示
		Boolean attribute_hump = false;
		if( null != PropertiesUtil.getValue(MConstants.ATTRIBUTE_HUMP)&&
				MConstants.YES.equalsIgnoreCase(PropertiesUtil.getValue(MConstants.ATTRIBUTE_HUMP))	){
			attribute_hump = true;
		}
		
		//引入class类
		StringBuffer importStr = new StringBuffer();
					 importStr.append("package ").append( PathUtil.createPackagePath(tablePO.getTable_name(),NameEnumType.VO_NAME)).append(" \n")
			                  .append("import java.io.Serializable; \n")
			                  .append("import ").append(PathUtil.createImportPath(tablePO.getTable_name(), NameEnumType.PO_NAME)).append(";\n");
		//类的主题			 
		StringBuffer attribute = new StringBuffer();
					 attribute.append("/**\n")
			                  .append("  *").append( tablePO.getTable_comment() ).append("\n")
			                  .append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
			                  .append("  *@version 1.0.0 ").append(DateUtil.dateToStr(new Date())).append(" \n")
			                  .append("  */\n")
			                  .append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME));
							 if(vo_extend_po){//继承
								 attribute.append("  extends ").append(po_name);
							 }else{//不继承
								 
							 }
					 attribute.append("  implements Serializable { \n")
							  .append("	 /** 序列化 */\n")
			                  .append("	  private static final long serialVersionUID = ").append(System.currentTimeMillis()).append("L;\n");
					 
		StringBuffer  methodStr = new StringBuffer(); 
		String fieldName = null;
		String fieldType = null;
		//引入前缀
		String prefix = "this.";
		for( MTableFieldPO filedPO : fieldList){

			String[] arrayField=FieldUtil.fieldNameType(filedPO);
			
			String getMethod = null; 
	         if( null != arrayField){
	        	 importStr.append(arrayField[0]);
	        	 fieldName = arrayField[1];
	        	 fieldType = arrayField[2];
	        	 getMethod = arrayField[3];
	         }
			//vo不继承po时，需要声明属性，生成get，set方法
			if( !vo_extend_po){
				String[] importArray = FieldUtil.importField(filedPO, fieldName, fieldType, getMethod);
				if( null != importArray){
					attribute.append(importArray[0]);
					methodStr.append(importArray[1]);
				}
			}else{
				//需要声明时，查看有没有时间一类的字段，需要生成 字符串
				prefix="super.";
			}
			//字段需要增加str字段
			String[] voNeedStr = FieldUtil.voNeedStr( filedPO, attribute_hump, prefix, getMethod  );
			if( null != voNeedStr){
				attribute.append(voNeedStr[0]);
				methodStr.append(voNeedStr[1]);
			}
			
			//字段是枚举类型字段
			String[] enumArray = FieldUtil.isEnum( filedPO, attribute_hump, prefix, getMethod  );
			if( null != enumArray){
				attribute.append(enumArray[0]);
				methodStr.append(enumArray[1]);
			}
		}
		attribute.append("\n \n");
		/** 构造方法 **/
		attribute.append("	public ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME)).append("(){ \n")
		         .append("		super() ; \n")
		         .append("	} \n");
		/** 构造方法**/
		attribute.append("	public ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME))
		         .append("(").append(FieldUtil.fieldType( fieldList.get(0).getData_type())).append("  ")
		                     .append(  fieldList.get(0).getColumn_name() )
		         .append(" ){\n")
		         .append("		super() ; \n")
		         .append("		this.").append( fieldList.get(0).getColumn_name()).append(" = ").append(fieldList.get(0).getColumn_name()).append(" ;\n")
		         .append("	} \n");
		attribute.append(methodStr)
		         .append("} //end class");
		
		importStr.append(attribute);
		  return importStr.toString();            
		         
	}

	
}
