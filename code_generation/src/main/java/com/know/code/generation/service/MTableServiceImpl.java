package com.know.code.generation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.know.code.generation.dao.IMTableDao;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;

/**
 * service实现类
 * @author lenovo
 *
 */
@Service("tableServiceImpl")
public class MTableServiceImpl implements IMTableService{
	
	@Resource 
	private IMTableDao tableDao;
	/**获取表字段集合*/
	public List<MTableFieldPO> queryTableFieldList(String table_schema,String table_name){
		List<MTableFieldPO> poList= null;
		Map<String,Object> paraMap = new HashMap<String,Object>();
						   paraMap.put("table_schema", table_schema);//数据库实例名
						   paraMap.put("table_name", table_name);//表明
		try{
			poList = tableDao.queryTableFieldList(paraMap);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return poList;
	}
	/**获取表名称*/
	public List<MTablePO> queryTableList(String table_schema){
		 List<MTablePO> tableList = null;
		 Map<String,Object> paraMap = new HashMap<String,Object>();
							paraMap.put("table_schema", table_schema);//数据库实例名
		 try{	
			 tableList = tableDao.queryTableList(paraMap);
		 }catch(Exception ex){
			ex.printStackTrace();
		 }
		 return  tableList;
	}
}
