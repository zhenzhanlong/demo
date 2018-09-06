package com.sx.controller.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.api.CityDubboService;
import com.demo.model.po.city.CityInfoPO;

@Controller
@RequestMapping(value = "/mybatis")   
public class MybatisController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="cityDubboServiceImpl")
	private CityDubboService cityDubboServiceImpl;
    /**
     * 处理 "/users/{id}" 的 GET 请求，用来删除 User 信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<CityInfoPO> deleteUser() {
    	log.info("查询list数据");
    	List<CityInfoPO> dataList = cityDubboServiceImpl.queryCityList(new CityInfoPO());
        return dataList;
    }
}
