package com.demo.api;

import java.util.List;

import com.demo.model.po.city.CityInfoPO;

/**
 * 城市业务 Dubbo 服务层
 *
 * Created by bysocket on 28/02/2017.
 */
public interface CityDubboService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName
     */
	CityInfoPO findCityByName(String cityName);

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
