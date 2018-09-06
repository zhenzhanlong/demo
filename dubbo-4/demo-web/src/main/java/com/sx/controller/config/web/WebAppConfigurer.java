package com.sx.controller.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 通过代码，来设置原本xml 标签的配置**/
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
	/**
     * SpringBoot设置首页
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("base/loginIndex");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }	

}
