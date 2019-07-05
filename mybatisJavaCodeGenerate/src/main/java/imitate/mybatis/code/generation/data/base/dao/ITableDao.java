package imitate.mybatis.code.generation.data.base.dao;

import java.util.List;

import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.data.base.po.QueryTableField;
import imitate.mybatis.code.generation.data.base.po.TableField;

/***
 * 获取表明，表字段集合dao
 * 
 * @author lenovo
 *
 */
public interface ITableDao {
	/**
	 * 获取表字段集合
	 * 
	 * @param field
	 * @return
	 */
	public List<QueryTableField> queryTableFieldList(QueryTableField field);

	/**
	 * 获取表名称
	 * 
	 * @param table
	 * @return
	 */
	public List<DataBaseTable> queryTableList(DataBaseTable table);
}
