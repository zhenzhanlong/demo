package com.know.code.generation.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.MNameConstants;
import com.know.code.generation.constants.type.NameEnumType;

/**
 * 生成 po，vo等名字帮助类
 * @author lenovo
 *
 */
public class NameUtil {
	/**
	 * 日志
	 */
	private static Logger logger_name_util = LoggerFactory.getLogger(NameUtil.class);
	//api 名称的前缀
	private static final String API_PREFIX="IM";
	//class类 名称的前缀
	private static final String CLASS_PREFIX="M";
	/**
	 * 生成文件名字
	 * @param nameType
	 * @return
	 */
	public static String createFileName(String table_name, NameEnumType nameType){
		StringBuffer name = new StringBuffer();
		switch( nameType.toString() ){
			case MNameConstants.PO_NAME:  
				 name.append(CLASS_PREFIX)
				     .append( NameUtil.subFileName(table_name) )
				     .append("PO");
				 break;
			case MNameConstants.VO_NAME:  
				 name.append(CLASS_PREFIX)
			     	 .append( NameUtil.subFileName(table_name) )
			         .append("VO");
				 break;
			case MNameConstants.FORM_NAME:  
				 name.append(CLASS_PREFIX)
			     	 .append( NameUtil.subFileName(table_name) )
		             .append("Form");
				 break;
			case MNameConstants.DAO_API_NAME:  
				 name.append(API_PREFIX)
			     	 .append( NameUtil.subFileName(table_name) )
		             .append("Dao");
				 break;
			case MNameConstants.SERVICE_NAME:  
				 name.append(CLASS_PREFIX)
		     	 	 .append( NameUtil.subFileName(table_name) )
		             .append("ServiceImpl");
				 break;
			case MNameConstants.SERVICE_API_NAME:  
				 name.append(API_PREFIX)
		     	 	 .append( NameUtil.subFileName(table_name) )
		             .append("Service");
				 break;
			case MNameConstants.BIZ_NAME:  
				 name.append(CLASS_PREFIX)
		     	 	 .append( NameUtil.subFileName(table_name) )
		             .append("BizImpl");
				 break;
			case MNameConstants.BIZ_API_NAME:  
				 name.append(API_PREFIX)
		     	 	 .append( NameUtil.subFileName(table_name) )
		             .append("Biz");
				 break;
			case MNameConstants.CONTROLLER_NAME:  
				 name.append(CLASS_PREFIX)
		     	 	 .append( NameUtil.subFileName(table_name) )
		             .append("Controller");
				break;
			case MNameConstants.MAPPER_NAME:  
				 name.append(API_PREFIX)
		     	 	 .append( NameUtil.subFileName(table_name) )
		             .append("Dao");
				break;
			case MNameConstants.MAPPER_MAP_NAME:  
				 name.append( NameUtil.subFileName(table_name) )
		             .append("Map");
				break;
			case MNameConstants.WEB_SERVICE_NAME:  
				 name.append( NameUtil.subFileName(table_name) )
		             .append("WebServiceImpl");
				break;
			default :break;
				
		}
		
		return name.toString();
	}
	/**截取数据库表明**/
	public static String subFileName(String table_name1){
		String file_name = table_name1;
		//数据库表明的前缀
		String prefix =  PropertiesUtil.getValue( MConstants.DATA_BASE_NAME_PREFIX );
		if( StringUtils.isBlank( prefix )){
			logger_name_util.debug("没有定义数据库表明前缀，直接返回 file_name ="+file_name);
			//不用截取前缀，直接返回
			return file_name;
		}
		if(file_name.indexOf(prefix)<0){
			return file_name;
		}
		//截取前缀
		file_name = file_name.substring(file_name.indexOf(prefix)+prefix.length()) ;
		logger_name_util.debug("去掉前缀后的名字 file_name="+file_name);
		if( file_name.indexOf("_")>=0){
			String[] fileNames = file_name.split("_");
			StringBuffer fileNameStr = new StringBuffer(); 
			for( String fileName :fileNames){
				//去掉分隔符首字母大写
				fileNameStr.append( firstCharacterToUpper(fileName));
			}
			logger_name_util.debug("名称处理完成 tableNameStr="+fileNameStr.toString() );
			return fileNameStr.toString();
		}else{
			//没有_字符 首字母大写即可
			file_name = firstCharacterToUpper(file_name);
		}
		return file_name;
	}
	/**截取数据库表明**/
	public static String subTableName(String table_name){
		//数据库表明的前缀
		String prefix =  PropertiesUtil.getValue( MConstants.DATA_BASE_NAME_PREFIX );
		if( StringUtils.isBlank( prefix )){
			logger_name_util.debug("没有定义数据库表明前缀，直接返回表明 table_name ="+table_name);
			//不用截取前缀，直接返回
			return table_name;
		}
		if(table_name.indexOf(prefix)<0){
			return table_name;
		}
		//截取前缀
		table_name = table_name.substring(table_name.indexOf(prefix)+prefix.length()) ;
		logger_name_util.debug("去掉前缀后的名字 table_name="+table_name);
		//是否驼峰显示
		if(table_name.indexOf("_")>=0){
			String[] tableNames = table_name.split("_");
			StringBuffer tableNameStr = new StringBuffer(); 
			for( String tableName :tableNames){
				//去掉分隔符首字母大写
				tableNameStr.append( firstCharacterToUpper(tableName));
			}
			logger_name_util.debug("名称处理完成 tableNameStr="+tableNameStr.toString() );
			return tableNameStr.toString();
		}else if(table_name.indexOf("_")>=0){//名字里面有_ 则就是_PO,_VO
			table_name = table_name+"_";
		}
		return table_name;
	}
	/** 截取数据库字段名  生成文件使用**/
	public static String subFieldName(String field_name){
		//是否驼峰显示
		String attribute_hump =PropertiesUtil.getValue( MConstants.ATTRIBUTE_HUMP );
		//logger_name_util.debug("是否驼峰显示 attribute_hump="+attribute_hump );
		if(MConstants.ATTRIBUTE_HUMP_YES.equals(attribute_hump) && field_name.indexOf("_")>=0){
			String[] fieldNames = field_name.split("_");
			StringBuffer fieldNameStr = new StringBuffer(); 
			for( int i=0,l=fieldNames.length;i<l;i++){
				fieldNames[i] = fieldNames[i].toLowerCase();
				//去掉分隔符首字母大写
				if(i>0){
					fieldNameStr.append( firstCharacterToUpper(fieldNames[i]));
				}else{//首个单词的首字母不大写
					fieldNameStr.append( fieldNames[i]);
				}
				
			}
			logger_name_util.debug("字段处理完成 fieldNameStr="+fieldNameStr.toString() );
			return fieldNameStr.toString();
		}
		logger_name_util.debug("处理后的字段 field_name="+field_name);
		return field_name;
	}
	/**
	 * 生成service ,dao的变量名称
	 * @param nameType
	 * @return
	 */
	public static String createPrivateName(String table_name, NameEnumType nameType){
		//变量名称前半段
		String name_str = NameUtil.firstCharacterToLower(NameUtil.subTableName(table_name));
		//变量名称
		StringBuffer name = new StringBuffer();
		             name.append(name_str);
		switch( nameType.toString() ){
			case MNameConstants.DAO_API_NAME:  
				 name.append("DaoImpl");				 break;
			case MNameConstants.SERVICE_API_NAME:  
				 name.append("ServiceImpl");
				 break;
			case MNameConstants.BIZ_NAME:  
				 name.append("BizImpl");
				 break;
			case MNameConstants.MAPPER_NAME:  
				 name.append("DaoMapperImpl");
				break;
			case MNameConstants.WEB_SERVICE_NAME:  
				 name.append("WebServiceImpl");
				break;
			default:break;
		}
		
		return name.toString();
	}
	
	
	/**
	 * 首字母大写 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {  
	   return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);  
	}
	/**
	 * 首字母小写
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToLower(String srcStr) {  
	   return srcStr.substring(0, 1).toLowerCase() + srcStr.substring(1);  
	}
	/**
	 * 生成service 引入类名
	 * @param name
	 * @return
	 */
	public static String createSpringServiceName(String name){
		  return firstCharacterToLower(name.substring(1).toLowerCase());  
	}
}
