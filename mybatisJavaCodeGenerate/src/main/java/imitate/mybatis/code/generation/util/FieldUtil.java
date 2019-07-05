package imitate.mybatis.code.generation.util;

import java.util.List;

import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.type.Constants;
import imitate.mybatis.code.generation.type.CreateFileType;

/**
 * 文件生成帮助类
 * 
 * @author lenovo
 *
 */
public class FieldUtil {

	/** 拼接 get方法 **/
	public static String getMethod(String column_name) {
		return "get" + NameUtil.firstCharacterToUpper(column_name);
	}

	/** 拼接 set方法 **/
	public static String setMethod(String column_name) {
		return "set" + NameUtil.firstCharacterToUpper(column_name);
	}

	// /*** 枚举字段处理 ***/
	// public static String[] isEnum(MTableFieldPO filedPO,Boolean
	// attribute_hump,String prefix,String getMethod ){
	//
	// //不是枚举字段直接返回null
	// if( !filedPO.isNeedImport() ){
	// return null;
	// }
	// StringBuilder methodStr = new StringBuilder();
	// StringBuilder attributeStr = new StringBuilder();
	//
	// String fieldName = filedPO.getColumn_name();
	// //驼峰
	// if( attribute_hump){
	// fieldName+= "Str";
	// }else{//不是驼峰
	// fieldName+= "_str";
	// }
	// //注释，属性
	// attributeStr.append(" /** ").append( filedPO.getColumn_comment()
	// ).append("字符串 **/ \n ");
	// attributeStr.append(" private String ").append( fieldName ).append(";\n");
	//
	// //get，set方法
	// methodStr.append(" public String ").append(FieldUtil.getMethod(
	// fieldName.toLowerCase() )).append("() {\n")
	// .append(" if(StringUtils.isBlank(").append(fieldName).append(")&& null !=
	// ").append(prefix).append(getMethod).append("()){\n")
	// .append(" ").append(
	// fieldName).append("=").append(prefix).append(getMethod).append("().getDescription();\n")
	// .append(" }\n")
	// .append(" return this.").append(fieldName).append(";\n")
	// .append(" }\n")
	// .append("\n");
	// methodStr.append(" public void ").append(FieldUtil.setMethod( fieldName
	// )).append("(String ").append(fieldName).append(") {\n")
	// .append(" this.").append( fieldName ).append(" = ").append(
	// fieldName).append(";\n")
	// .append(" }\n")
	// .append("\n");
	//
	// String[] strArray = new String[2];
	// strArray[0] = attributeStr.toString();
	// strArray[1] = methodStr.toString();
	// return strArray;
	// }
	// // 需要追加字符串的字段
	// public static String[] voNeedStr(MTableFieldPO filedPO,Boolean
	// attribute_hump,String prefix,String getMethod ){
	// //不需要引入的直接返回null
	// if( !ImportClassUtil.voNeedStr( filedPO.getData_type() ) ){
	// return null;
	// }
	// StringBuilder methodStr = new StringBuilder();
	// StringBuilder attributeStr = new StringBuilder();
	//
	// String fieldName = filedPO.getColumn_name();
	// //需要添加字符串的字段，比如Date类型 需要字符串显示
	//
	// //驼峰
	// if( attribute_hump){
	// fieldName+= "Str";
	// }else{//不是驼峰
	// fieldName+= "_str";
	// }
	// //注释，属性
	// attributeStr.append(" /** ").append( filedPO.getColumn_comment()
	// ).append("字符串 **/ \n ");
	// attributeStr.append(" private String ").append( fieldName ).append(";\n");
	// //get，set方法
	// methodStr.append(" public String ").append(FieldUtil.getMethod(
	// fieldName.toLowerCase() )).append("() {\n")
	// .append(" if(StringUtils.isBlank(").append(fieldName).append(")&& null !=
	// ").append(prefix).append(getMethod)
	// .append("()){\n")
	// .append(" ").append(
	// fieldName).append("=").append(ImportClassUtil.voNeedStrMethodInsert(
	// filedPO.getData_type(), prefix, getMethod)).append(";\n")
	// .append(" }\n")
	// .append(" return this.").append(fieldName).append(";\n")
	// .append(" }\n")
	// .append("\n");
	// methodStr.append(" public void ").append(FieldUtil.setMethod( fieldName
	// )).append("(String ").append(fieldName).append(") {\n")
	// .append(" this.").append( fieldName ).append(" = ").append(
	// fieldName).append(";\n")
	// .append(" }\n")
	// .append("\n");
	// String[] strArray = new String[2];
	// strArray[0] = attributeStr.toString();
	// strArray[1] = methodStr.toString();
	// return strArray;
	//
	// }
	/** 字段信息转换为 get,set方法 **/
	public static String attribute(List<JavaField> fieldList, CreateFileType fileType) {
		StringBuilder attributeStr = new StringBuilder();
		fieldList.stream().forEach(field -> {
			attributeStr.append(attribute(field, fileType));
		});
		return attributeStr.toString();
	}

	/** 字段信息转换为 属性声明信息 **/
	public static String attribute(JavaField fieldVO, CreateFileType fileType) {
		StringBuilder attributeStr = new StringBuilder();
		// 注释，属性
		attributeStr.append("	/** ").append(fieldVO.getColumnComment()).append(" **/ \n ");
		// vo、form加 swagger说明
		if (CreateFileType.VO.equals(fileType) || CreateFileType.FORM.equals(fileType)) {
			attributeStr.append(voOrFormAnnotation(fieldVO,fileType));
		}
		attributeStr.append("@JsonInclude(JsonInclude.Include.NON_NULL) \n ");
		attributeStr.append("	private ").append(fieldVO.getJavaType()).append("  ").append(fieldVO.getJavaName())
				.append(";\n");
		return attributeStr.toString();
	}

	/** vo 或者 form 需要的属性标签 **/
	private static String voOrFormAnnotation(JavaField fieldVO,CreateFileType fileType) {
		StringBuilder fieldStr = new StringBuilder();
		// vo特有标签
		if (CreateFileType.VO.equals(fileType)) {
			fieldStr.append(voAnnotation(fieldVO));
		}
		// form特有标签
		if (CreateFileType.FORM.equals(fileType)) {
			fieldStr.append(formAnnotation(fieldVO));
		}
		//共有标签
		fieldStr.append("@ApiModelProperty(value = \"").append(fieldVO.getColumnComment()).append("\", name = \"")
				.append(fieldVO.getJavaName()).append("\") \n ");
		return fieldStr.toString();
	}

	/** vo 需要的属性标签 **/
	private static String voAnnotation(JavaField fieldVO) {
		StringBuilder fieldStr = new StringBuilder();
		if (Constants.JAVA_DATE.equals(fieldVO.getJavaType())) {
			fieldStr.append("@JsonFormat(timezone = \"GMT+8\", pattern = \"yyyy-MM-dd HH:mm:ss\")");
		}
		fieldStr.append(" \n ");
		return fieldStr.toString();
	}

	/** form 需要的属性标签 **/
	private static String formAnnotation(JavaField fieldVO) {
		StringBuilder fieldStr = new StringBuilder();
		fieldStr.append(" \n ");
		return fieldStr.toString();
	}

	/** 字段信息转换为 get,set方法 **/
	public static String getSetMethod(List<JavaField> fieldList, CreateFileType fileType) {
		StringBuilder getSetStr = new StringBuilder();
		fieldList.stream().forEach(field -> {
			getSetStr.append(getSetMethod(field, fileType));
		});
		return getSetStr.toString();
	}

	/** 字段信息转换为 get,set方法 **/
	public static String getSetMethod(JavaField fieldVO, CreateFileType fileType) {
		StringBuilder methodStr = new StringBuilder();
		// get，
		methodStr.append("	public ").append(fieldVO.getJavaType()).append(" ").append(fieldVO.getMethod())
				.append("() {\n");
		// form 处理字符串 为""的情况
		if (CreateFileType.FORM.equals(fileType) && Constants.JAVA_STRING.equals(fieldVO.getJavaType())) {

			methodStr.append("		return StringUtils.isBlank(this.").append(fieldVO.getJavaName())
					.append(")?null:this.").append(fieldVO.getJavaName()).append(";\n");
		} else {
			methodStr.append("		return this.").append(fieldVO.getJavaName()).append(";\n");
		}

		methodStr.append("	}\n").append("\n");

		// set 方法
		methodStr.append("	public void ").append(fieldVO.setMethod()).append("(").append(fieldVO.getJavaType())
				.append(" ").append(fieldVO.getJavaName()).append(") {\n").append("		this.")
				.append(fieldVO.getJavaName()).append(" = ").append(fieldVO.getJavaName()).append(";\n")
				.append("	}\n").append("\n");
		return methodStr.toString();
	}

	/** 生成 and 的if 语句 **/
	public static String createAndIf(JavaField attriVO) {
		StringBuilder ifStr = new StringBuilder();
		// and 条件
		ifStr.append("				<if test=\"").append(attriVO.getJavaName()).append(" !=null \"> \n  ")
				.append(" and ").append(attriVO.getColumnName()).append(" = #{").append(attriVO.getJavaName())
				.append("} \n").append("				</if> \n");
		return ifStr.toString();
	}

	/** 生成 or 的if 语句 **/
	public static String createOrIf(JavaField attriVO) {
		StringBuilder ifStr = new StringBuilder();
		// and 条件
		ifStr.append("	<if test=\"").append(attriVO.getJavaName()).append(" !=null \"> \n  ").append(" or ")
				.append(attriVO.getColumnName()).append(" = #{").append(attriVO.getJavaName()).append("} \n")
				.append(" </if> \n");
		return ifStr.toString();
	}

	/** 生成 set 中的if 语句 **/
	public static String createSetIf(JavaField attriVO) {
		StringBuilder setStrByIf = new StringBuilder();
		setStrByIf.append("	<if test=\"").append(attriVO.getJavaName()).append(" !=null \"> \n ")
				.append(attriVO.getColumnName()).append("=#{").append(attriVO.getJavaName()).append("},\n ")
				.append("	</if> \n ");
		return setStrByIf.toString();
	}

	/** queryFields 查询字段 **/
	public static String queryFields(List<JavaField> fieldList) {
		// 字段字符串
		StringBuilder queryFields = new StringBuilder();
		for (JavaField attriVO : fieldList) {
			// select 字段
			if (queryFields.length() > 0) {
				queryFields.append(",").append(attriVO.getColumnName());
			} else {
				queryFields.append(attriVO.getColumnName());
			}
		}
		return queryFields.toString();
	}

	/** select 语句字段 **/
	public static String daoWhereField(List<JavaField> fieldList) {
		// 字段字符串
		StringBuilder selectFieldSql = new StringBuilder();
		for (JavaField fieldVO : fieldList) {
			selectFieldSql.append(createAndIf(fieldVO));
		}
		return selectFieldSql.toString();

	}

	/** select 语句字段 **/
	public static String resumtMapResults(List<JavaField> fieldList) {
		// 字段字符串
		StringBuilder resultMapField = new StringBuilder();
		for (JavaField fieldVO : fieldList) {
			resultMapField.append(mapperResult(fieldVO));
		}
		return resultMapField.toString();
	}

	/** update 语句字段 **/
	public static String updateFields(List<JavaField> fieldList) {
		// 字段字符串
		StringBuilder updateFields = new StringBuilder();
		for (JavaField fieldVO : fieldList) {
			updateFields.append(createSetIf(fieldVO));
		}
		return updateFields.toString();
	}

	/** 生成 resultMap 中的 result 语句 **/
	public static String mapperResult(JavaField field) {
		StringBuilder mapperStr = new StringBuilder();
		mapperStr.append("	<result property=\"").append(field.getJavaName()).append("\" column=\"")
				.append(field.getColumnName()).append("\" />\n");
		return mapperStr.toString();
	}

	/** insert 语句 **/
	public static String insertFields(List<JavaField> fieldList) {
		// 字段字符串
		StringBuilder insertFields = new StringBuilder();
		for (JavaField field : fieldList) {
			if (insertFields.length() > 0) {
				insertFields.append(",").append("#{").append(field.getColumnName()).append("}");
			} else {
				insertFields.append("#{").append(field.getJavaName()).append("}");
			}
		}
		return insertFields.toString();
	}

	/** insertBatch 语句 **/
	public static String insertBatchFields(List<JavaField> fieldList) {
		// 字段字符串
		StringBuilder insertBatchField = new StringBuilder();
		for (JavaField field : fieldList) {
			if (insertBatchField.length() > 0) {
				insertBatchField.append(",").append("#{item.").append(field.getColumnName()).append("}");
			} else {
				insertBatchField.append("#{item.").append(field.getJavaName()).append("}");
			}
		}
		return insertBatchField.toString();
	}
}
