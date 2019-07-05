package imitate.mybatis.code.generation.data.base.service;

import java.util.List;

import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.data.base.po.QueryTableField;

/**
 * 方法定义接口
 * 
 * @author lenovo
 *
 */
public interface ITableService {
	/**
	 * 获取表字段集合
	 * 
	 * @param paraMap
	 * @return
	 */
	public List<QueryTableField> queryTableFieldList(String table_schema, String table_name);

	/**
	 * 获取表名称
	 * 
	 * @param paraMap
	 * @return
	 */
	public List<DataBaseTable> queryTableList(String table_schema);
}
