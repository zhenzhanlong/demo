package com.know.code.generation.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.know.code.generation.biz.IMTableBiz;

/**
 * 程序入口
 * @author lenovo
 *
 */
public class MainController {
	@SuppressWarnings("resource")
	public static void main(String[] args){
		
		ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"classpath:know/code/generation/code.xml"});
									   ac.start();
		IMTableBiz tableBizImpl = (IMTableBiz) ac.getBean("tableBizImpl");
				   tableBizImpl.createFiles();
				   
	}
}
	
	
	
