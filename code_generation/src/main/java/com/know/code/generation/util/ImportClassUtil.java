package com.know.code.generation.util;
/**
 * 生成 po，vo，form， service，api，dubbo controller引入 jar包帮助类
 * @author lenovo
 *
 */
public class ImportClassUtil {
	/**  po引入jar路径**/
	public static String poImport(String fieldType){
		//引入jar包路径
		String importStr = null;
		switch(fieldType){
			case "timestamp":
				importStr="import java.sql.Timestamp;\n";
				break;
			case "datetime":
				importStr="import java.util.Date;\n";
				break;
			case "date":
				importStr="import java.util.Date;\n";
				break;
			case "decimal":
				importStr="import java.math.BigDecimal;\n";
				break;
			case "double":
				importStr="import java.math.BigDecimal;\n";
				break;
			case "float":
				importStr="import java.math.BigDecimal;\n";
				break;
			case "numeric":
				importStr="import java.math.BigDecimal;\n";
				break;
		}
		return importStr;
	}
	/**  vo引入jar路径**/
	public static String voImport(String fieldType){
		//引入jar包路径
		String importStr = ImportClassUtil.poImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		return null;
	}
	/**  form引入jar路径**/
	public static String formImport(String fieldType){
		//引入jar包路径
		String importStr = ImportClassUtil.voImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		return null;
	}
	/**  service引入jar路径**/
	public static String serviceImplImport(String fieldType){
		//引入jar包路径
		String importStr = ImportClassUtil.formImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		return null;
	}
	/** apiImpl 引入jar路径**/
	public static String apiImplImport(String fieldType){
		//引入jar包路径
		String importStr = ImportClassUtil.formImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		return null;
	}
	/**  dubboImpl引入jar路径**/
	public static String dubboImplImport(String fieldType){
		//引入jar包路径
		String importStr = ImportClassUtil.formImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		return null;
	}
	/** controller 引入jar路径**/
	public static String controllerImport(String fieldType){
		//引入jar包路径
		String importStr = ImportClassUtil.formImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		importStr = ImportClassUtil.serviceImplImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		importStr = ImportClassUtil.apiImplImport(fieldType);
		if( null != importStr){
			return importStr;
		}
		return null;
	}
	/**  判断 类是否需要引入时间，等类**/
	public static Boolean voNeedStr(String fieldType){
		Boolean voNeedStr =false;
		switch(fieldType){
			case "timestamp":
				voNeedStr=true;
				break;
			case "datetime":
				voNeedStr=true;
				break;
			case "date":
				voNeedStr=true;
				break;
		}
		return voNeedStr;
	}
	/** 引入的时间等类的转换**/
	public static String voNeedStrMethodInsert(String fieldType,String prefix,String getMethod){
		String str = null;
		switch(fieldType){
			case "timestamp":
				str = "DateUtil.dateToStrTimestamp( "+prefix+getMethod+"() )";
				break;
			case "datetime":
				str = "DateUtil.dateToStrTimestamp( "+prefix+getMethod+"() )";
				break;
			case "date":
				str = "DateUtil.dateToStr( "+prefix+getMethod+"() )";
				break;
		}
		return str;
	}
}
