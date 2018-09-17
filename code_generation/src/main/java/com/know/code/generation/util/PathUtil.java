package com.know.code.generation.util;

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
public class PathUtil {
	/**
	 * 日志
	 */
	private static Logger logger_name_util = LoggerFactory.getLogger(PathUtil.class);
	/**
	 * 生成文件 package路径
	 * @param table_name
	 * @param nameType
	 * @return
	 */
	public static String createPackagePath(String table_name, NameEnumType nameType){
		//系统路径公用部分
		String package_path = PropertiesUtil.getValue(MConstants.PACKAGE_PATH);
		       package_path = (null == package_path ) ?MConstants.DEFAULT_PACKAGE_PATH:package_path;
		//路径变量 
		StringBuffer name = new StringBuffer();
					 name.append( package_path );
		//生成完成路径			 
		switch( nameType.toString() ){
			case MNameConstants.PO_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_PO )
				    .append(";\n");
				 break;
			case MNameConstants.VO_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_VO )
				    .append(";\n");
				 break;
			case MNameConstants.FORM_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_FORM )
				    .append(";\n");
				 break;
			case MNameConstants.DAO_API_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_DAO_API )
				    .append(";\n");
				 break;
			case MNameConstants.SERVICE_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_SERVICE )
				    .append(";\n");
				 break;
			case MNameConstants.SERVICE_API_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_SERVICE_API )
				    .append(";\n");
				 break;
			case MNameConstants.BIZ_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_BIZ )
				    .append(";\n");
				 break;
			case MNameConstants.BIZ_API_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_BIZ_API )
				    .append(";\n");
				 break;
			case MNameConstants.CONTROLLER_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_CONTROLLER )
				    .append(";\n");
				break;
			case MNameConstants.MAPPER_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_MAPPER );
				break;
			case MNameConstants.WEB_SERVICE_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_WEB_SERVICE );
				break;
			default: break;
		}
		logger_name_util.debug(" import文件路径："+name.toString());
		return name.toString();
	}
	/**
	 * 生成文件 import 引入路径
	 * @param table_name
	 * @param nameType
	 * @return
	 */
	public static String createImportPath(String table_name, NameEnumType nameType){
		//系统路径公用部分
		String package_path = PropertiesUtil.getValue(MConstants.PACKAGE_PATH);
		       package_path = (null == package_path ) ?MConstants.DEFAULT_PACKAGE_PATH:package_path;
		//路径变量 
		StringBuffer name = new StringBuffer();
					 name.append( package_path );
		//生成完成路径			 
		switch( nameType.toString() ){
			case MNameConstants.PO_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_PO )
				    .append(".")
                    .append(NameUtil.createFileName(table_name, NameEnumType.PO_NAME));
				 break;
			case MNameConstants.VO_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_VO )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.VO_NAME));
				 break;
			case MNameConstants.FORM_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_FORM )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.FORM_NAME));
				 break;
			case MNameConstants.DAO_API_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_DAO_API )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.DAO_API_NAME));
				 break;
			case MNameConstants.SERVICE_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_SERVICE )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.SERVICE_NAME));
				 break;
			case MNameConstants.SERVICE_API_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_SERVICE_API )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.SERVICE_API_NAME));
				 break;
			case MNameConstants.BIZ_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_BIZ )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.BIZ_NAME));
				 break;
			case MNameConstants.BIZ_API_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_BIZ_API )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.BIZ_API_NAME));
				 break;
			case MNameConstants.CONTROLLER_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_CONTROLLER )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.CONTROLLER_NAME));
				break;
			case MNameConstants.MAPPER_NAME:  
				name.append(MConstants.DEFAULT_PACKAGE_PATH_MAPPER )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.MAPPER_NAME));
				break;	
				
		}
		logger_name_util.debug(" import文件路径："+name.toString());
		return name.toString();
	}
	/**
	 * 生成文件 import 引入路径
	 * @param table_name
	 * @param nameType
	 * @return
	 */
	public static String createImportClassPath(String table_name, NameEnumType nameType){
		//系统路径公用部分
		String package_path = PropertiesUtil.getValue(MConstants.PACKAGE_PATH);
		       package_path = (null == package_path ) ?MConstants.DEFAULT_PACKAGE_PATH:package_path;
		//路径变量 
		StringBuffer importStr = new StringBuffer();
					 importStr.append( package_path );
		//生成完成路径			 
		switch( nameType.toString() ){
			case MNameConstants.PO_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_PO )
				    .append(".")
                    .append(NameUtil.createFileName(table_name, NameEnumType.PO_NAME));
				 break;
			case MNameConstants.VO_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_VO )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.VO_NAME));
				 break;
			case MNameConstants.FORM_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_FORM )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.FORM_NAME));
				 break;
			case MNameConstants.DAO_API_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_DAO_API )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.DAO_API_NAME));
				 break;
			case MNameConstants.SERVICE_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_SERVICE )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.SERVICE_NAME));
				 break;
			case MNameConstants.SERVICE_API_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_SERVICE_API )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.SERVICE_API_NAME));
				 break;
			case MNameConstants.BIZ_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_BIZ )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.BIZ_NAME));
				 break;
			case MNameConstants.BIZ_API_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_BIZ_API )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.BIZ_API_NAME));
				 break;
			case MNameConstants.CONTROLLER_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_CONTROLLER )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.CONTROLLER_NAME));
				break;
			case MNameConstants.MAPPER_NAME:  
				importStr.append(MConstants.DEFAULT_PACKAGE_PATH_MAPPER )
				    .append(".")
	                .append(NameUtil.createFileName(table_name, NameEnumType.MAPPER_NAME));
				break;	
				
		}
		importStr.append(";\n");
		logger_name_util.debug(" import文件路径："+importStr.toString());
		return importStr.toString();
	}
	
}
