package com.sx.controller.entry.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sx.servlet.ViewAndModel;

/**
 * spring 基础控制器
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/base")
//@PropertySource(value= {"classpath:apalication_param.properties"},ignoreResourceNotFound=false,encoding="UTF-8")
public class MBaseController {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * token超时时间
	 */
	private static final Integer LOGIN_TOKEN_VOER_TIME = 604800;
	@Value("${context}")
	private String context;
	@Value("${site_root}")
	private String site_root;
	
	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Autowired
	protected Validator validator;

	/**
	 * 使用spring 自带的类封装时，日期格式需要特殊处理
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 系统登录页
	 */
	@RequestMapping(value = "loginIndex")
	public ViewAndModel loginIndex() {
		ViewAndModel model =  new ViewAndModel("entry/m_entry", request);
		model.addObject("site_root", site_root);
		model.addObject("context", context);
		return model;
	}
	
	
	
	/*** 错误信息提示处理 ***/
	public String errorsMessage(org.springframework.validation.Errors errors) {
		if (errors.hasErrors() || errors.hasGlobalErrors()) {
			StringBuilder errInfo = new StringBuilder();
			List<FieldError> ferrors = errors.getFieldErrors();
			if (!ferrors.isEmpty()) {
				for (FieldError error : ferrors) {
					errInfo.append(error.getField() + ":" + error.getDefaultMessage() + ";");
					//log.error(error.getDefaultMessage());
				}
			}
			List<ObjectError> gerrors = errors.getGlobalErrors();
			if (!gerrors.isEmpty()) {
				for (ObjectError error : gerrors) {
					errInfo.append(error.getDefaultMessage() + ";;");
				}
			}
			errors.reject(null, errInfo.toString());
			return errInfo.toString();
		}
		return "";
	}
	
	
	public Logger getLog() {
		return log;
	}
	public void setLog(Logger log) {
		this.log = log;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	public static Integer getLoginTokenVoerTime() {
		return LOGIN_TOKEN_VOER_TIME;
	}
	
}
