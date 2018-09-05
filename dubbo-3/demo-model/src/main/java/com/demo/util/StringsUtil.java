/**
 * 
 */
package com.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * @author admin
 *
 */
public class StringsUtil {
		private StringsUtil() {
		
	}
	/**
	 * BufferedReader 转换成 String
	 * @param br
	 * @return
	 */
	 public static String getBodyString(BufferedReader br) {
		  String inputLine;
		  StringBuilder str = new StringBuilder();
		     try {
		       while ((inputLine = br.readLine()) != null) {
		    	   str.append(inputLine);
		       }
		       br.close();
		     } catch (IOException e) {
		     }
		     return str.toString();
		 }
	 
	 /**
	  * 字符串 转换 int 数组
	  * @param str
	  * @return
	  */
	 public static int[] stringtoInt(String str) {  
		  
		    int[] ret = new int[str.length()];   
		    StringTokenizer toKenizer = new StringTokenizer(str, ",");  
		    
		    int i = 0;  
		    while (toKenizer.hasMoreElements()) {   
		      ret[i++] = Integer.valueOf(toKenizer.nextToken());  
		    }   
		    
		   return ret;  
		  
		 }
	 /**
	  * 字符串 转换 long 数组
	  * @param str
	  * @return
	  */
	 public static Long[] stringtoLong(String str) {  
		  
		 Long[] ret = new Long[str.length()];   
		    StringTokenizer toKenizer = new StringTokenizer(str, ",");  
		    
		    int i = 0;  
		    while (toKenizer.hasMoreElements()) {   
		      ret[i++] = Long.valueOf(toKenizer.nextToken());  
		    }   
		    
		   return ret;  
		  
		 }
	 /**
	  * 字符串 转换 int 数组
	  * @param str
	  * @return
	  */
	 public static Integer[] stringtoInteger(String str) {  
		  
		 Integer[] ret = new Integer[str.length()];   
		    StringTokenizer toKenizer = new StringTokenizer(str, ",");  
		    
		    Integer i = 0;  
		    while (toKenizer.hasMoreElements()) {   
		      ret[i++] = Integer.valueOf(toKenizer.nextToken());  
		    }   
		    
		   return ret;  
		  
		 }
	 
	 /**
	  * 生成 4位数字-字符串
	  * @param intValue
	  * @return
	  */
	 public static String inttoString(int intValue){
		 String str = null;
		 if (intValue < 10 ) {
			str = "000"+intValue;
		}
		 if(intValue >10 && intValue<99){
			 str = "00"+intValue;
		 }
		 if (100 < intValue ) {
			 str = "0"+intValue;
		}
		 if (1000 < intValue) {
			 str = String.valueOf(intValue);
		}
		 return str;
	 }
	 
	 /**获取新的用户唯一编号*/
	public static String uniqueCode(String prefix){
			 Date date = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
			return prefix+sdf.format(date);
	}
		
	/**
	 * 字符数组转换为字符串
	 * @param strs
	 * @return
	 */
	public static String arrayToString(String[] strs){
		StringBuilder returnStr = new StringBuilder();
		 
		if( null != strs){
			//循环处理数据
			for( String str_ : strs){ 
				if( returnStr.length() >0 ){
					
					returnStr.append(",").append( str_ );
				}else{
					returnStr.append( str_ );
				}
				
			}
		}
		return returnStr.toString();
	}
	/**
	 * 字符数组转换为字符串
	 * @param strs
	 * @return
	 */
	public static String arrayToString(Long[] arrays){
		StringBuilder returnStr = new StringBuilder();
		 
		if( null != arrays){
			//循环处理数据
			for( Long param : arrays){ 
				if( returnStr.length() >0 ){
					
					returnStr.append(",").append( param );
				}else{
					returnStr.append( param );
				}
				
			}
		}
		return returnStr.toString();
	}
	/**
	 * 判断对象是否为字符串，是否为空
	 * @param obj
	 * @return
	 */
	public static  Object isNull(Object obj){
		if(obj instanceof String && StringUtils.isBlank((String)obj)){
				return null;
		}
		return obj;
	}
	/** 判断类是否相同**/
	public static boolean eq(Object before, Object after){
		boolean falg =false;
		if( ( before !=null && after !=null )
				&& before.equals(after)){
			return true;
		}
		return falg;
	}

}
