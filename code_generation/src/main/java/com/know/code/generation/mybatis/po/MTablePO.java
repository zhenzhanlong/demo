package com.know.code.generation.mybatis.po;

import java.io.Serializable;
/**
 * 表字段类
 * @author lenovo
 *
 */
public class MTablePO implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;
	/*** 表名称***/
	private String table_name;
	/*** 表介绍 **/
	private String table_comment;
	
	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_comment() {
		return table_comment;
	}

	public void setTable_comment(String table_comment) {
		this.table_comment = table_comment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MTablePO() {
		super();
	}
	
	
	        
	
}
