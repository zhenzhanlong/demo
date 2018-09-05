package com.sx.controller.entry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.model.BaseVO;
import com.sx.controller.entry.base.MBaseController;
import com.sx.servlet.ViewAndModel;


/**
 * 用户登录 控制器
 * 
 * @author zhenzhanlong
 *
 */
@Controller
@RequestMapping("/entry")
@SuppressWarnings({"rawtypes"})
public class MEntryController extends MBaseController {
	private Logger log = LoggerFactory.getLogger(this.getClass());


	/*** 重新登录 ***/
	@RequestMapping(value = "reSign")
	public ViewAndModel reSign(){
		log.info("跳转到重新登录界面");
		// 跳转到登录页
		ViewAndModel model = new ViewAndModel("entry/m_entry", request);
		return model;
	}

	/*** 重新登录 ***/
	@RequestMapping(value = "notHaveLogin")
	@ResponseBody
	public BaseVO notHaveLogin() {
		log.info("用户登录超时，请重新登录");
		return BaseVO.createErrorMsg("用户登录超时，请重新登录");
	}

	/**
	 * 用户已经登录提示
	 * 
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "haveSign")
	public ViewAndModel haveSign(){
		log.info("跳转到重新登录界面");
		// 跳转到首页登录页
		ViewAndModel model = new ViewAndModel("entry/m_entry", request);
		model.addObject("error", "当前用户已在其他设备登陆");
		return model;
	}


	
	/*** 数据库连接中断 ***/
	@RequestMapping(value = "communications")
	public ViewAndModel communications(){
		return this.error("数据库连接出现中断问题");
	}

	/*** 数据库连接中断 ***/
	@RequestMapping(value = "invalidReference")
	public ViewAndModel invalidReference(){
		return this.error("session已经失效，请重新登录");
	}

	/*** 数据库连接中断 ***/
	@RequestMapping(value = "uniqueValidate")
	public ViewAndModel uniqueValidate(){
		return this.error("程序出现未知错误，请重新登录");
	}

	/*** 数据库连接中断 ***/
	/**/@RequestMapping(value = "unknownException")
	@ResponseBody
	public ViewAndModel unknownException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		log.info("程序出现未知错误，跳转到重新登录界面");
		// 跳转到登录页
		ViewAndModel model = new ViewAndModel("entry/m_entry", request);
					 model.addObject("error", "程序出现未知错误，跳转到重新登录界面");
		return model;
	}

	/*** 界面跳转错误 ***/
	@RequestMapping(value = "modelException")
	public ViewAndModel modelException(){
		return this.error("您没有配置模块的权限");
	}

	/*** ajax 错误 ***/
	@RequestMapping(value = "ajaxException")
	@ResponseBody
	public BaseVO ajaxException(){
		return BaseVO.createErrorMsgBykey("sj.m.function.button.user.not.configure");
	}

	/*** ajax 错误 (抛出此异常是为了进行实物回滚) ***/
	@RequestMapping(value = "ajaxRollbackException")
	@ResponseBody
	public BaseVO ajaxRollbackException(String error){
		log.info("ajax异常error={}",error);
		return BaseVO.createErrorMsg(error);
	}

	/*** 界面跳转错误 ***/
	@RequestMapping(value = "modelNotCaughtException")
	public ViewAndModel modelNotCaughtException(){
		ViewAndModel model = new ViewAndModel("index", request);
		model.addObject("not_caught_exception", "程序出现错误，操作失败");
		return model;
	}

	/*** ajax 未捕获异常 错误 ***/
	@RequestMapping(value = "ajaxNotCaughtException")
	@ResponseBody
	public BaseVO ajaxNotCaughtException(){
		return BaseVO.createErrorMsgBykey("sj.m.not.caught.exception.ajax.exception");
	}

	/**
	 * 公共错误登录跳转
	 * 
	 * @param error
	 * @return
	 */
	private ViewAndModel error(String error) {
		ViewAndModel errorView = new ViewAndModel("entry/m_entry", request);
					 errorView.addObject("error", error);
		return errorView;
	}
	
	/** 获取ip **/
	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		log.debug("getIp ip1：" + ip);
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		log.debug("getIp ip2：" + ip);
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getRemoteAddr();
		log.debug("getIp ip3：" + ip);
		return ip;
	}

}
