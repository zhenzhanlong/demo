package com.know.code.generation.mybatis.po.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;


/**
 * 枚举类型解析使用
 * @author lenovo
 *
 */
public class MEnumVO implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 2418378149007051814L;
	/** 字段名称 **/
	private String field_name;
	/** 配置类名称**/ 
	private String class_name;
	/** 配置类完整路径**/
	private String path;
	
	public MEnumVO() {
		super();
	}
	
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getClass_name() {
		if(StringUtils.isBlank(class_name) && StringUtils.isNotBlank(this.path)){
			class_name = this.path.substring(this.path.lastIndexOf(".")+1);
		}
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
