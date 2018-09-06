package com.demo.dao;

import java.util.List;

import com.demo.model.po.city.CityInfoPO;

public interface ICityDao {

	/**
	 * 获取市
	 * @param upCodeId
	 * @return
	 */
	public List<CityInfoPO> queryCityList(CityInfoPO po);
	/**
	 * 获取市
	 * @param cityInfoVO
	 * @return
	 */
	public CityInfoPO queryCityOne(CityInfoPO po);
	/**
	 * 获取市（包含上级省）
	 * @param cityInfoVO
	 * @return
	 */
	public CityInfoPO queryCityOneDetail(CityInfoPO po);
	
	/**
	 * 获取省份列表
	 * @param cityInfoVO
	 * @return
	 */
	public List<CityInfoPO> queryListForProvince(CityInfoPO po);
	/**
	 * 获取省份对象
	 * @param po
	 * @return
	 */
	public CityInfoPO queryProvinceOne(CityInfoPO po); 
}
