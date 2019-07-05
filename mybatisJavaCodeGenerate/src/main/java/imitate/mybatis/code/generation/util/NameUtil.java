package imitate.mybatis.code.generation.util;

import org.apache.commons.lang3.StringUtils;

import imitate.mybatis.code.generation.type.FileNameEnumType;
import imitate.mybatis.code.generation.type.MNameConstants;
import imitate.mybatis.code.generation.type.NameEnumType;

/**
 * 生成 po，vo等名字帮助类
 * 
 * @author lenovo
 *
 */
public class NameUtil {

	private NameUtil() {
	}

	/** 数据库字段名分隔符 **/
	public static final String FIELD_DELIMITER = "_";

	/**
	 * 生成文件名字
	 * 
	 * @param nameType
	 * @return
	 */
	public static String createClassName(String classPrefix, String tableName, FileNameEnumType nameType) {
		StringBuilder name = new StringBuilder();
		if (null == classPrefix) {
			classPrefix = "";
		}
		switch (nameType.toString()) {
		case MNameConstants.PO_NAME:
			name.append(classPrefix).append(tableName).append("PO");
			break;
		case MNameConstants.VO_NAME:
			name.append(classPrefix).append(tableName).append("VO");
			break;
		case MNameConstants.FORM_NAME:
			name.append(classPrefix).append(tableName).append("Form");
			break;
		case MNameConstants.DAO_API_NAME:
			name.append(classPrefix).append(tableName).append("Dao");
			break;
		case MNameConstants.SERVICE_NAME:
			name.append(classPrefix).append(tableName).append("ServiceImpl");
			break;
		case MNameConstants.SERVICE_API_NAME:
			name.append(classPrefix).append(tableName).append("Service");
			break;
		case MNameConstants.BIZ_NAME:
			name.append(classPrefix).append(tableName).append("BizImpl");
			break;
		case MNameConstants.BIZ_API_NAME:
			name.append(classPrefix).append(tableName).append("Biz");
			break;
		case MNameConstants.CONTROLLER_NAME:
			name.append(classPrefix).append(tableName).append("Controller");
			break;
		case MNameConstants.MAPPER_NAME:
			name.append(classPrefix).append(tableName).append("Dao");
			break;
		case MNameConstants.MAPPER_MAP_NAME:
			name.append(tableName).append("Map");
			break;
		case MNameConstants.WEB_SERVICE_NAME:
			name.append(tableName).append("WebServiceImpl");
			break;
		case MNameConstants.WEB_SERVICE_API_NAME:
			name.append(classPrefix).append(tableName).append("WebService");
			break;
		case MNameConstants.ACTION_NAME:
			name.append(tableName).append("Action");
			break;
		default:
			break;

		}

		return name.toString();
	}

	/** 截取数据库表明 **/
	public static String subFileName(String prefix, String tableName) {
		String fileName = tableName;
		// 数据库表明的前缀
		if (StringUtils.isBlank(prefix)) {
			// 不用截取前缀，直接返回
			return fileName;
		}
		if (fileName.indexOf(prefix) < 0) {
			return fileName;
		}
		// 截取前缀
		fileName = fileName.substring(fileName.indexOf(prefix) + prefix.length());
		if (fileName.indexOf(FIELD_DELIMITER) >= 0) {
			String[] fileNames = fileName.split(FIELD_DELIMITER);
			StringBuilder fileNameStr = new StringBuilder();
			for (String fileNameTemp : fileNames) {
				// 去掉分隔符首字母大写
				fileNameStr.append(firstCharacterToUpper(fileNameTemp));
			}
			return fileNameStr.toString();
		} else {
			// 没有_字符 首字母大写即可
			fileName = firstCharacterToUpper(fileName);
		}
		return fileName;
	}

	/** 截取数据库表明 **/
	public static String subTableName(String tableName, String prefix) {
		// 数据库表明的前缀
		if (StringUtils.isBlank(prefix)) {
			// 不用截取前缀，直接返回
			return tableName;
		}
		if (tableName.indexOf(prefix) < 0) {
			return tableName;
		}
		// 截取前缀
		tableName = tableName.substring(tableName.indexOf(prefix) + prefix.length());
		if (tableName.indexOf(FIELD_DELIMITER) >= 0) {
			String[] tableNames = tableName.split(FIELD_DELIMITER);
			StringBuilder tableNameStr = new StringBuilder();
			for (String tableNameTemp : tableNames) {
				// 去掉分隔符首字母大写
				tableNameStr.append(firstCharacterToUpper(tableNameTemp));
			}
			return tableNameStr.toString();
		}
		return firstCharacterToUpper(tableName);
	}

	/**
	 * 生成service ,dao的变量名称
	 * 
	 * @param nameType
	 * @return
	 */
	public static String createPrivateName(String tableName, NameEnumType nameType) {
		// 变量名称前半段
		String nameStr = tableName;
		// 变量名称
		StringBuilder name = new StringBuilder();
		name.append(nameStr);
		switch (nameType.toString()) {
		case MNameConstants.DAO_API_NAME:
			name.append("DaoImpl");
			break;
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
		default:
			break;
		}

		return name.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		if (StringUtils.isBlank(srcStr)) {
			return null;
		}
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToLower(String srcStr) {
		if (StringUtils.isBlank(srcStr)) {
			return null;
		}
		return srcStr.substring(0, 1).toLowerCase() + srcStr.substring(1);
	}

	/**
	 * 生成service 引入类名
	 * 
	 * @param name
	 * @return
	 */
	public static String createSpringServiceName(String name) {
		return firstCharacterToLower(name.substring(1).toLowerCase());
	}
}
