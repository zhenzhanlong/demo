package com.know.code.generation.service;

import java.util.List;

import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;

/**
 * 方法定义接口
 * @author lenovo
 *
 */
public interface IMTableService {
	/**
	 * 获取表字段集合
	 * @param paraMap
	 * @return
	 */
	public List<MTableFieldPO> queryTableFieldList(String table_schema,String table_name);
	/**
	 * 获取表名称
	 * @param paraMap
	 * @return
	 */
	public List<MTablePO> queryTableList(String table_schema);
}
