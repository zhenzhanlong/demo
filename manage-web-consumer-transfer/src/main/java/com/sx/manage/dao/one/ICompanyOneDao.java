package com.sx.manage.dao.one;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sx.manage.mybatis.po.consumer.MCompanyEntityPO;

/**
 * 一期 查询用户使用
 * @author Administrator
 *
 */
//@Mapper
public interface ICompanyOneDao {

	/**
	 * 查询集合
	 * @param po
	 * @return
	 */
	public List<MCompanyEntityPO> getListAll();
	
}
