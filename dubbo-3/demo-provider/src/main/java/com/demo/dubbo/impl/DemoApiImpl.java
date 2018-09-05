package com.demo.dubbo.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.api.IDemoApi;
import com.demo.model.form.DemoForm;
import com.demo.model.vo.DemoVO;

/**
 * provider 实现类
 * 
 * @author Administrator
 *
 */
@Service("demoApiImpl")
public class DemoApiImpl implements IDemoApi {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public DemoVO getDemoById(Long id) {
		log.info("根据id={}", id);
		return new DemoVO();
	}

	@Override
	public List<DemoVO> getList(DemoForm form) {
		log.info("根据form={}", form);
		return new ArrayList<>();
	}

	@Override
	public boolean saveDemo(DemoForm form) {
		log.info("根据form={}新增", form);
		return false;
	}

	@Override
	public boolean updateDemo(DemoForm form) {
		log.info("根据form={}更新", form);
		return false;
	}

	@Override
	public boolean deleteDemoById(Long id) {
		log.info("根据id={}删除", id);
		return false;
	}

}
