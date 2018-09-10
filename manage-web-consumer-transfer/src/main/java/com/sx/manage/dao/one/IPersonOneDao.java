package com.sx.manage.dao.one;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sx.manage.mybatis.po.consumer.MPersonalConsumerInfoEntityPO;

/**
 * 一期 查询用户使用
 * @author Administrator
 *
 */
//@Mapper
public interface IPersonOneDao {

	/**
	 * 查询集合
	 * @param po
	 * @return
	 */
	public List<MPersonalConsumerInfoEntityPO> getListByPO(MPersonalConsumerInfoEntityPO po);
	/**
	 * 查询数量
	 * @param po
	 * @return
	 */
	public Long countPerson(MPersonalConsumerInfoEntityPO po);
}
