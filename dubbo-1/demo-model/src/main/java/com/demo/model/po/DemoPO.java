package com.demo.model.po;

import java.io.Serializable;

import com.demo.constants.DemoType;

/** po类**/
public class DemoPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5423231375639789848L;
	/** 主键**/
	private Long id;
	/** 上级主键**/
	private Long pid;
	/** 名称**/
	private String name;
	/** 枚举实例**/
	private DemoType enumType;
	public DemoPO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DemoType getEnumType() {
		return enumType;
	}
	public void setEnumType(DemoType enumType) {
		this.enumType = enumType;
	}
	@Override
	public String toString() {
		return "DemoPO [id=" + id + ", pid=" + pid + ", name=" + name + ", enumType=" + enumType + "]";
	}
}
