package com.sx.manage.dao.two;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sx.model.manage.mybatis.po.consumer.MPersonalConsumerNetworkPO;
/**
 * 二期 查询用户使用
 * @author Administrator
 *
 */
@Mapper
public interface IPersonTwoDao {


	/**
	 * 查询集合
	 * @param po
	 * @return
	 */
	public List<MPersonalConsumerNetworkPO> getListByPO(MPersonalConsumerNetworkPO po);
	/**
	 * 查询数量
	 * @param po
	 * @return
	 */
	public Long countPerson(MPersonalConsumerNetworkPO po);
	/**
	 * 查询集合
	 * @param po
	 * @return
	 */
	public List<MPersonalConsumerNetworkPO> getListByLawNo(List<String> lawNoList);
	/**
	 * 单条数据
	 * @param po
	 * @return
	 */
	public MPersonalConsumerNetworkPO getPO(MPersonalConsumerNetworkPO po);
}
