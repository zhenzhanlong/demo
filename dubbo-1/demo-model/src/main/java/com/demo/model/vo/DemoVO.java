package com.demo.model.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.demo.model.po.DemoPO;

public class DemoVO extends DemoPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 163396813899888685L;
	/** 枚举实例**/
	private String enumTypeStr;
	
	public DemoVO() {
		super();
	}

	public String getEnumTypeStr() {
		//枚举有默认的中文，同时还可以主动修改成别的显示
		if(StringUtils.isNoneBlank(enumTypeStr)&&null!=this.getEnumType()) {
			enumTypeStr = this.getEnumType().getDescription();
		}
		return enumTypeStr;
	}

	public void setEnumTypeStr(String enumTypeStr) {
		this.enumTypeStr = enumTypeStr;
	}
	
	
}
