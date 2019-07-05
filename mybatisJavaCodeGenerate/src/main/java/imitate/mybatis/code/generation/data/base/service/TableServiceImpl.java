package imitate.mybatis.code.generation.data.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import imitate.mybatis.code.generation.data.base.dao.ITableDao;
import imitate.mybatis.code.generation.data.base.po.DataBaseTable;
import imitate.mybatis.code.generation.data.base.po.QueryTableField;

/**
 * service实现类
 * 
 * @author lenovo
 *
 */
@Service("tableServiceImpl")
public class TableServiceImpl implements ITableService {

	protected Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ITableDao tableDao;

	/** 获取表字段集合 */
	public List<QueryTableField> queryTableFieldList(String table_schema, String table_name) {
		List<QueryTableField> poList = null;
		QueryTableField queryPO = new QueryTableField();
		queryPO.setTable_schema(table_schema);// 数据库实例名
		queryPO.setTable_name(table_name);// 表明
		try {
			poList = tableDao.queryTableFieldList(queryPO);
		} catch (Exception ex) {
			logger.error("获取数据库表字段集合异常error={}", ex);
		}
		return poList;
	}

	/** 获取表名称 */
	public List<DataBaseTable> queryTableList(String table_schema) {
		List<DataBaseTable> tableList = null;
		DataBaseTable queryPO = new DataBaseTable();
		queryPO.setTable_schema(table_schema);// 数据库实例名
		try {
			tableList = tableDao.queryTableList(queryPO);
		} catch (Exception ex) {
			logger.error("获取数据库表集合异常error={}", ex);
		}
		return tableList;
	}
}
