package com.know.code.generation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class FileUtil {
	/**
	 * 生成文件
	 * @param path
	 * @param fileName
	 * @param content
	 * @return
	 */
	public static boolean createFile(String path,String fileName,String content){
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
						       out.write( content );
						   } catch (Exception e) {
						       e.printStackTrace();
						   } finally {
						       try {
						       out.close();
						       } catch (IOException e) {
						       e.printStackTrace();
						       }
						   }
						  }
					} catch (IOException e) {
						e.printStackTrace();
					}
		          return true;
	}
}
