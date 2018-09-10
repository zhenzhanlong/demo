package com.sx.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.sx.manage.service.ExportPersonService;

@Component
public class ExportRunner implements ApplicationRunner {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private ExportPersonService exportPersonService;
	
	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		log.info("项目启动，开始执行导入数据applicationArguments={}",applicationArguments);
	 	exportPersonService.exportPage();
	}
}
