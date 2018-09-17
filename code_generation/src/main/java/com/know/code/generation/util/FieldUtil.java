package com.know.code.generation.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.mybatis.po.MTableFieldPO;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class FieldUtil {
	
	public static List<String> notUpdateFieldList = new ArrayList<String>();
	/**
	 * 默认加载不需要update字段
	 */
	 static{
		 String field_str = PropertiesUtil.getValue(MConstants.NOT_UPDATE_FIELD);
		 if( StringUtils.isNotBlank( field_str )){
			 String[] fields = field_str.split(",");
			 for(String field:fields){
				 notUpdateFieldList.add(field);
			 }			 
		 }		 
	 }

	
	public static String fieldType(String fieldType){
		String returnType = "String";;
		switch(fieldType){
		case "int":
			returnType="Long";
			break;
		case "bigint":
			returnType="Long";
			break;
		case "timestamp":
			returnType="Timestamp";
			break;
		case "varchar":
			returnType="String";
			break;
		case "datetime":
			returnType="Date";
			break;
		case "date":
			returnType="Date";
			break;
		case "char":
			returnType="String";
			break;
		}
		return returnType;
	}
	/** 拼接 get方法**/
	public static String getMethod(String column_name){
		return "get"+NameUtil.firstCharacterToUpper(column_name);
	}
	/** 拼接 set方法**/
	public static String setMethod(String column_name){
		return "set"+NameUtil.firstCharacterToUpper(column_name);
	}
	
	/*** 枚举字段处理 ***/
	public static String[] isEnum(MTableFieldPO filedPO,Boolean attribute_hump,String prefix,String getMethod  ){
		
		//不是枚举字段直接返回null
		if( !filedPO.isNeedImport() ){
			return null;
		}
		StringBuffer methodStr = new StringBuffer(); 
		StringBuffer attributeStr = new StringBuffer(); 
		
		String fieldName = filedPO.getColumn_name();
		//驼峰
		if( attribute_hump){
			fieldName+= "Str";
		}else{//不是驼峰
			fieldName+= "_str";
		}
		//注释，属性
		attributeStr.append("	/** ").append( filedPO.getColumn_comment() ).append("字符串 **/ \n ");
		attributeStr.append("	private String  ").append( fieldName ).append(";\n");
		
		//get，set方法
		methodStr.append("	public String ").append(FieldUtil.getMethod( fieldName.toLowerCase() )).append("() {\n")
				 .append(" 		if(StringUtils.isBlank(").append(fieldName).append(")&& null != ").append(prefix).append(getMethod).append("()){\n")
				 .append("        			").append( fieldName).append("=").append(prefix).append(getMethod).append("().getDescription();\n")
		         .append("       }\n")
				 .append("		return this.").append(fieldName).append(";\n")
		         .append("	}\n")
		         .append("\n");
		methodStr.append("	public void ").append(FieldUtil.setMethod( fieldName )).append("(String ").append(fieldName).append(") {\n")
		         .append("		this.").append( fieldName ).append(" = ").append( fieldName).append(";\n")
		         .append("	}\n")
		         .append("\n");
		
		String[] strArray = new String[2];
		         strArray[0] = attributeStr.toString();
		         strArray[1] = methodStr.toString();
		return strArray;
	}
	// 需要追加字符串的字段
	public static String[] voNeedStr(MTableFieldPO filedPO,Boolean attribute_hump,String prefix,String getMethod  ){
		//不需要引入的直接返回null
		if( !ImportClassUtil.voNeedStr( filedPO.getData_type() ) ){
			return null;
		}
		StringBuffer methodStr = new StringBuffer(); 
		StringBuffer attributeStr = new StringBuffer(); 
		
		String fieldName = filedPO.getColumn_name();
		//需要添加字符串的字段，比如Date类型 需要字符串显示
		 
			//驼峰
			if( attribute_hump){
				fieldName+= "Str";
			}else{//不是驼峰
				fieldName+= "_str";
			}
			//注释，属性
			attributeStr.append("	/** ").append( filedPO.getColumn_comment() ).append("字符串 **/ \n ");
			attributeStr.append("	private String  ").append( fieldName ).append(";\n");
			//get，set方法
			methodStr.append("	public String ").append(FieldUtil.getMethod( fieldName.toLowerCase() )).append("() {\n")
					 .append(" 		if(StringUtils.isBlank(").append(fieldName).append(")&& null != ").append(prefix).append(getMethod)
					 		 .append("()){\n")
					 .append("        			").append( fieldName).append("=").append(ImportClassUtil.voNeedStrMethodInsert( filedPO.getData_type(), prefix, getMethod)).append(";\n")
			         .append("       }\n")
					 .append("		return this.").append(fieldName).append(";\n")
			         .append("	}\n")
			         .append("\n");
			methodStr.append("	public void ").append(FieldUtil.setMethod( fieldName )).append("(String ").append(fieldName).append(") {\n")
			         .append("		this.").append( fieldName ).append(" = ").append( fieldName).append(";\n")
			         .append("	}\n")
			         .append("\n");
		String[] strArray = new String[2];
	         	 strArray[0] = attributeStr.toString();
	         	 strArray[1] = methodStr.toString();
	     return strArray;
		
	}
	
	public static String[] importField(MTableFieldPO filedPO,String fieldName ,String fieldType,String getMethod){
		StringBuffer methodStr = new StringBuffer(); 
		StringBuffer attributeStr = new StringBuffer(); 
		StringBuffer importStr = new StringBuffer();
		//根据 数据类型，引入类
		String importTemp =  ImportClassUtil.poImport( filedPO.getData_type());
		if( StringUtils.isNotBlank( importTemp )){
			importStr.append(importTemp);
		}
		//注释，属性
		attributeStr.append("	/** ").append( filedPO.getColumn_comment() ).append(" **/ \n ");
		// po内没有必要放验证，放在form里最好
		//attributeStr.append( FormFileUtil.validatorLimit( filedPO ) );
		attributeStr.append("	protected ").append( fieldType ).append("  ").append( fieldName ).append(";\n");
		
		//get，set方法
		methodStr.append("	public ").append(fieldType).append(" ").append(getMethod).append("() {\n");
		if( "String".equals(fieldType) ){
			methodStr.append("		return StringUtils.isBlank(this.").append(fieldName).append(")?null:this.").append(fieldName).append(";\n");
		}else{
			methodStr.append("		return this.").append(fieldName).append(";\n");
		}
		
		methodStr.append("	}\n")
		         .append("\n");
		methodStr.append("	public void ").append(FieldUtil.setMethod( fieldName )).append("(").append(fieldType).append(" ").append(fieldName).append(") {\n")
		         .append("		this.").append( fieldName ).append(" = ").append( fieldName).append(";\n")
		         .append("	}\n")
		         .append("\n");
		
		String[] importArray = new String[3];
				 importArray[0] = attributeStr.toString();
				 importArray[1] = methodStr.toString();
				 importArray[2] = importStr.toString();
    	 return importArray;
	}
	
	
	public static String[] fieldNameType(MTableFieldPO filedPO){
		if( null == filedPO){
			return null;
		}
		//import 字符串
		StringBuffer importStr = new StringBuffer();
		//字段名
		String fieldName = NameUtil.subFieldName( filedPO.getColumn_name());
		//字段属性
		String fieldType = FieldUtil.fieldType( filedPO.getData_type());
		//需要引入类
		if( filedPO.isNeedImport() ){
			importStr.append("import ").append( filedPO.getClass_path()).append(";\n");
			fieldType = filedPO.getColumn_type();
		}
		//get方法名称
		String getMethod =FieldUtil.getMethod( fieldName.toLowerCase() );
		String[] str = new String[4];
		         str[0]=importStr.toString();
		         str[1]=fieldName;
		         str[2]=fieldType;
		         str[3]=getMethod;
		return str;
	}
}
