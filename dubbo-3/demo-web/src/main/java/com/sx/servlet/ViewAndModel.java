package com.sx.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;



/**
 * 继承 spring 类
 * @author lenovo
 *
 */
@Component
@PropertySource(value= {"classpath:apalication_param.properties"},ignoreResourceNotFound=false,encoding="UTF-8")
public  class ViewAndModel extends ModelAndView {
	//@Value("${context}")
	private String context;
	//@Value("${site_root}")
	private String site_root;
	
	public ViewAndModel() {
		super();
		this.addObject("site_root", site_root);
		this.addObject("context", context);
	}
	public ViewAndModel(String viewName,HttpServletRequest req) {
		super();
		this.setViewName(viewName);
		this.addObject("site_root", site_root);
		this.addObject("context", context);
	}
	/*public ViewAndModel(HttpServletRequest req,Cache cache) {
		super();
		//记载缓存
		loadProperties(req,cache);
		//获取用户
		consumer(req,cache);
	}

	public ViewAndModel(String viewName,HttpServletRequest req,Cache cache) {
		super();
		this.setViewName(viewName);
		//记载缓存
		loadProperties(req,cache);
		//获取用户
		consumer(req,cache);
	}*/
	/**
	 * 加载缓存信息
	 * @param req
	 * @param cache
	
	private void loadProperties(HttpServletRequest req,Cache cache){
		Map<String, String> properMap = MCacheListener.getPeoperties();
		Iterator<String> its = properMap.keySet().iterator();
		String itKey = null;
		while(its.hasNext()){
			itKey = (String)its.next();
			this.addObject( itKey , properMap.get(itKey)  );
		}
	} */
	/**
	 * 获取登录用户信息
	 * @param req
	 * @param cache
	 
	private void consumer(HttpServletRequest req,Cache cache){
		Session session = SessionManager.instance(req, null, cache);
		// 判断当前的用户是否登陆
				if (!session.istokenexists()) {
					this.setViewName("entry/m_entry");
					this.addObject("error", MsgCodeUtil.getMsg("sj.m.login.over.time.or.not.login") );					
				} else {					
					 String userJson  =  session.get("user");
					   if( StringUtils.isBlank(userJson)){
						   this.setViewName("entry/m_entry");
						   this.addObject("error", MsgCodeUtil.getMsg("sj.m.login.over.time.or.not.login") );
					   }else{
						   session.expire(MSystemConstants.SESSION_TIME_SET_UP);
						   MConsumerLoginVO consumerLoginVO = JackJsonUtil.jsonToJavaBean(userJson, MConsumerLoginVO.class);
						   this.addObject("user", consumerLoginVO );
						   this.addObject("func_list", ServiceUtil.function_cache_query_function_vo(MSystemConstants.CACHE_TYPE_SYSTEM_CONSUMER_FUNCTION,  consumerLoginVO.getConsumer_id(),null , cache ));
					   }
				     
				}
	}*/
	/**
	 * 解决token超时问题（超时后点击菜单界面会报错但不会跳转到登录页）
	 */
	public void setViewName(String viewName) {
		if( StringUtils.isBlank( this.getViewName() )){
			super.setViewName(viewName);
		}
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getSite_root() {
		return site_root;
	}
	public void setSite_root(String site_root) {
		this.site_root = site_root;
	}
	
}
