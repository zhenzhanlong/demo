package com.demo.dubbo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.api.CityDubboService;
import com.demo.dao.ICityDao;
import com.demo.model.po.city.CityInfoPO;

@Service("cityDubboServiceImpl")
public class CityDubboServiceImpl  implements CityDubboService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ICityDao cityDao;
	
	@Override
	public CityInfoPO findCityByName(String cityName) {
		CityInfoPO po= new CityInfoPO();
		po.setCity_name(cityName);
		log.info("查询条件po={}",po);
		return cityDao.queryCityOne(po);
	}

	@Override
	public List<CityInfoPO> queryCityList(CityInfoPO po) {
		log.info("查询条件po={}",po);
		return cityDao.queryCityList(new CityInfoPO());
	}

	@Override
	public CityInfoPO queryCityOne(CityInfoPO po) {
		log.info("查询条件po={}",po);
		return cityDao.queryCityOneDetail(po);
	}

	@Override
	public CityInfoPO queryCityOneDetail(CityInfoPO po) {
		log.info("查询条件po={}",po);
		return cityDao.queryCityOneDetail(po);
	}

	@Override
	public List<CityInfoPO> queryListForProvince(CityInfoPO po) {
		log.info("查询条件po={}",po);
		return cityDao.queryListForProvince(po);
	}

	@Override
	public CityInfoPO queryProvinceOne(CityInfoPO po) {
		log.info("查询条件po={}",po);
		return cityDao.queryProvinceOne(po);
	}

}
