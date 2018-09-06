package com.demo.api;

import java.util.List;

import com.demo.model.form.DemoForm;
import com.demo.model.vo.DemoVO;

/** **/
public interface IDemoApi {
	
	/** 根据id查询**/
	public DemoVO getDemoById(Long id);
	/** 获取列表**/
	public List<DemoVO> getList(DemoForm form);
	/** 保存**/
	public boolean saveDemo(DemoForm form);
	/** 更新**/
	public boolean updateDemo(DemoForm form);
	/** 根据id删除**/
	public boolean deleteDemoById(Long id);
	
}
