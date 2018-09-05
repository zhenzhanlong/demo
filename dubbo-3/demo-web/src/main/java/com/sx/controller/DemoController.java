package com.sx.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.api.IDemoApi;
import com.demo.model.vo.DemoVO;

@Controller
@RequestMapping("/demo")
public class DemoController {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="demoApiImpl")
	private IDemoApi demoApiImpl;
	
	@RequestMapping("/getById")
	@ResponseBody
	public DemoVO getById(Long id) {
		log.info("根据id={}",id);
		return demoApiImpl.getDemoById(id);
	}
}
