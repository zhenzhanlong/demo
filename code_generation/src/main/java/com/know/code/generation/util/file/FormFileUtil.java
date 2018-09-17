package com.know.code.generation.util.file;

import java.util.Date;
import java.util.List;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.MNameConstants;
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
public class FormFileUtil {
	public static String content(MTablePO tablePO,List<MTableFieldPO> fieldList){
		StringBuffer importStr = new StringBuffer();
					 importStr.append("package ").append( PathUtil.createPackagePath(tablePO.getTable_name(),NameEnumType.FORM_NAME)).append(" \n")
			                  .append("import java.io.Serializable; \n")
			                  .append("import org.apache.commons.lang3.StringUtils; \n")
			                  .append("import com.sx.manage.mybatis.form.BaseForm; \n");
					 
		StringBuffer attribute = new StringBuffer();	                  
					 attribute.append("/**\n")
			                  .append("  *").append( tablePO.getTable_comment() ).append("\n")
			                  .append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
			                  .append("  *@version 1.0.0 ").append(DateUtil.dateToStr(new Date())).append(" \n")
			                  .append("  */\n")
			                  .append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME)).append(" extends BaseForm implements Serializable { \n")
			                  .append("	 /** 序列化 */\n")
			                  .append("	  private static final long serialVersionUID = ").append(System.currentTimeMillis()).append("L;\n");
					 
		StringBuffer  methodStr = new StringBuffer(); 
		String fieldName = null;
		String fieldType = null;
		
		//是否驼峰显示
		Boolean attribute_hump = false;
		if( null != PropertiesUtil.getValue(MConstants.ATTRIBUTE_HUMP)&&
				MConstants.YES.equalsIgnoreCase(PropertiesUtil.getValue(MConstants.ATTRIBUTE_HUMP))	){
			attribute_hump = true;
		}
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
			//生成get，set方法
			String[] importArray = FieldUtil.importField(filedPO, fieldName, fieldType, getMethod);
			if( null != importArray){
				attribute.append(importArray[0]);
				methodStr.append(importArray[1]);
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
		/** 构造方法 **/
		attribute.append("	public ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME)).append("(){ \n")
		         .append("		super() ; \n")
		         .append("	} \n");
		/** 构造方法**/
		attribute.append("	public ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME))
		         .append("(").append(FieldUtil.fieldType( fieldList.get(0).getData_type())).append("  ")
		                     .append(  fieldList.get(0).getColumn_name() )
		         .append(" ){\n")
		         .append("		super() ; \n")
		         .append("		this.").append( fieldList.get(0).getColumn_name()).append(" = ").append(fieldList.get(0).getColumn_name()).append(" ;\n")
		         .append("	} \n");
		attribute.append(methodStr)
		         .append("} //end class");
		//拼接为返回str
				StringBuffer resultStr = new StringBuffer();
							 resultStr.append(importStr)
							          .append(attribute);
		  return resultStr.toString();            
		         
	}
	/**
	 * 根据字段名称正价验证
	 * @param field_name
	 * @return
	 */
	
	public static String validatorLimit(MTableFieldPO filedPO){
		StringBuffer field_str = new StringBuffer();
		//@NotBlank(message="所属系统不能为空")
		//@Email(message="非法的电子邮箱")
		//@Length(min=11, max=11, message="非法的手机号码")
		//@Pattern(regexp="^[0-9]{1,2}$",message="年龄是整数")
		//@Range(min=,max=,message=) 
			switch(filedPO.getColumn_name()){
			case MNameConstants.FIELD_VALIDATE_EMAIL: 
				field_str.append("	@NotBlank(message = \"")
						 .append(filedPO.getColumn_name())
						 .append("不能为空\")")
				         .append("  \n ")
						 .append("	@Email(message=\"非法的电子邮箱\") \n");
				break;
			case MNameConstants.FIELD_VALIDATE_PHONE: 
				field_str.append("	@Length(min=11, max=11, message=\"非法的手机号码\") \n");
				
				break;
			
			case MNameConstants.FIELD_VALIDATE_MOBILE: 
				field_str.append("	@Length(min=11, max=11, message=\"非法的手机号码\") \n");
				break;
			
			case MNameConstants.FIELD_VALIDATE_MOBILE_TEL: 
				field_str.append("	@Length(min=11, max=11, message=\"非法的手机号码\") \n");
				break;
			case MNameConstants.FIELD_VALIDATE_TELEPHONE: 
				field_str.append("	@Length(min=11, max=11, message=\"非法的手机号码\") \n");
				break;
			case MNameConstants.FIELD_VALIDATE_CARD_NUM: 
				field_str.append("	@Length(min=11, max=11, message=\"非法的手机号码\") \n");
				break;
			case MNameConstants.FIELD_VALIDATE_AGE: 
				field_str.append("	@Range(min=15,max=65,message=\"请输入正确的年龄\")) \n");
				break;
			case MNameConstants.FIELD_VALIDATE_SALARY_DATE: 
				field_str.append("	@Length(min=1, max=31, message=\"请输入正确的日期\") \n");
				break;			
			}
			//不允许为空
			if(MNameConstants.FIELD_VALIDATE_IS_NULLABLE_NO.equals( filedPO.getIs_nullable()) ){ 
					field_str.append("   @NotEmpty(message=\"").append( filedPO.getColumn_comment()).append("不允许为空\") \n");											
			}
		return field_str.toString();
	}
}
