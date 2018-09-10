package com.sx.manage.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class FileUtil {
	/**
	 * 日志
	 */
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 生成文件
	 * @param path
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static boolean createFile(String path,String fileName,List<String> sqlList){
					path = path.replace(".", "/");
					//生成目录
		             File filePath = new File(path);
		             if( !filePath.exists()){
		            	 filePath.mkdirs();
		             }
		             if( path.endsWith("/")){
		            	 fileName = path+fileName;
		             }else{
		            	 fileName = path+"/"+fileName; 
		             }
		             //生成文件
		             File file = new File(fileName);
		             if( file.exists() ){
		            	   file.delete();
		             }
		              try {
		            	  //生成文件，成功就继续添加内容
						if( file.createNewFile()){
						   BufferedWriter out = null;
						   try {
						       out = new BufferedWriter(new OutputStreamWriter(
						       new FileOutputStream(file, true)));
						       for(String sqlStr:sqlList) {
						    	   out.write( sqlStr );
						       }
						   } catch (Exception e) {
						     log.error("生成文件异常error={}",e);
						   } finally {
						       try {
						       out.close();
						       } catch (IOException e) {
						    	   log.error("关闭文件流异常error={}",e);
						       }
						   }
						  }
					} catch (IOException e) {
						 log.error("生成文件异常error={}",e);
					}
		          return true;
	}
}
