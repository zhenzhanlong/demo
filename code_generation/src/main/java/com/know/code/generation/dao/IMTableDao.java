package com.know.code.generation.dao;

import java.util.List;
import java.util.Map;

import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;

/***
 * 获取表明，表字段集合dao
 * @author lenovo
 *
 */
public interface IMTableDao {
	/**
	 * 获取表字段集合
	 * @param paraMap
	 * @return
	 */
	public List<MTableFieldPO> queryTableFieldList(Map<String,Object> paraMap);
	/**
	 * 获取表名称
	 * @param paraMap
	 * @return
	 */
	public List<MTablePO> queryTableList(Map<String,Object> paraMap);
}
