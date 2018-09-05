package com.sx.manage.exception;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 异常处理器（捕获程序没有定义的，或者定义的异常。然后处理）
 * @author Administrator
 *
 */
@Component
public class HandlerExceptionResolverImpl extends SimpleMappingExceptionResolver {
	/** 日志 **/
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	public HandlerExceptionResolverImpl() {
		super.setDefaultErrorView("redirect:/entry/unknownException");
		super.setExceptionAttribute("ex");
		Properties exceptionMappings = new Properties();
		exceptionMappings.setProperty("MNotHaveLoginException", "redirect:/entry/reSign");
		exceptionMappings.setProperty("MNotHaveLoginExceptionAjax", "redirect:/entry/notHaveLogin");
		exceptionMappings.setProperty("MNestedServletException", "redirect:/entry/reSign");
		exceptionMappings.setProperty("MCommunicationsException", "redirect:/entry/communications");
		exceptionMappings.setProperty("MNullPointerException", "redirect:/entry/nullPointer");
		exceptionMappings.setProperty("MSQLInterceptException", "redirect:/base/sqlInjectException");
		exceptionMappings.setProperty("MLoginOnOtherException", "redirect:/entry/haveSign</prop>");
		exceptionMappings.setProperty("MViewAndModelException", "redirect:/entry/modelException");
		exceptionMappings.setProperty("MAjaxErrorException", "redirect:/entry/ajaxException");
		exceptionMappings.setProperty("MAjaxErrorRollbackException", "redirect:/entry/ajaxRollbackException");
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		if (StringUtils.isNotBlank(viewName)) {
			log.debug("异常配置了跳转，现在开始跳转viewName={}",viewName);
			request.setAttribute("error", ex.getMessage());
			ModelAndView modelView = getModelAndView(viewName, ex);
			modelView.addObject("error", ex.getMessage());
			return modelView;
		}else {
			viewName = "entry/m_entry";
		}
		return getModelAndView(viewName, ex);

	}
}