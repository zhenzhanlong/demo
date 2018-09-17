package com.know.code.generation.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.mybatis.po.vo.MEnumVO;

/**
 * 读取消息配置工具类
 * 
 * @author lxh
 */
@Order(1)
public class PropertiesUtil {
	/** 配置解析为 properties **/
	private static Properties properties;
	/** 枚举字段 properties **/
	private static Properties propertiesEnum;
	/** #BasePO,BaseVO,BaseForm 已经存在字段在po，vo，form里面去掉 **/
	private static Set<String> hasExitsField = new HashSet<String>();
	/** 枚举类型字段配置 **/
	private static Map<String,MEnumVO> enumField = new HashMap<String,MEnumVO>();
	static {
		properties = new Properties();
		propertiesEnum = new Properties();
		try (InputStreamReader isr = new InputStreamReader(PropertiesUtil.class
				.getClassLoader().getResourceAsStream("know/code/generation/code.properties"),
				"utf-8")) {
			properties.load(isr);
			Object need = properties.get(MConstants.USE_CONFIG_BY_USER);
			//加载用户自己的配置文件
			if(null != need && MConstants.YES.equalsIgnoreCase((String)need)){
				InputStreamReader configIsr = new InputStreamReader(PropertiesUtil.class
						.getClassLoader().getResourceAsStream("know/code/generation/user_config_by_self.properties"),
						"utf-8");
				properties.load(configIsr);
				//需要过滤的字段
				String exitsField =(String) properties.get(MConstants.BASE_FIELD_HAS_EXITS);
				if( StringUtils.isNotBlank( exitsField )){
					String[] exitsFieldArray = exitsField.split(",");
					for(String fieldStr: exitsFieldArray){
						hasExitsField.add(fieldStr);
					}
				}
				
			}
			//加载枚举字段配置
			InputStream enumIs = PropertiesUtil.class.getClassLoader().getResourceAsStream("know/code/generation/enum_field.properties");
			if(null != enumIs){
				InputStreamReader enumIsr = new InputStreamReader(enumIs,"utf-8");
				propertiesEnum.load(enumIsr);
				Iterator<Object> it = propertiesEnum.keySet().iterator();
				while( it.hasNext()){
					String key = (String)it.next();
					MEnumVO enumVO = new MEnumVO();
							enumVO.setField_name(key);
							enumVO.setPath(propertiesEnum.getProperty(key));
					enumField.put(key, enumVO);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过key获取消息
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (key == null) {
			return key;
		}
		return properties.getProperty(key);
	}

	public static Set<String> getHasExitsField() {
		return hasExitsField;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		PropertiesUtil.properties = properties;
	}

	public static Properties getPropertiesEnum() {
		return propertiesEnum;
	}

	public static void setPropertiesEnum(Properties propertiesEnum) {
		PropertiesUtil.propertiesEnum = propertiesEnum;
	}

	public static Map<String, MEnumVO> getEnumField() {
		return enumField;
	}

	public static void setEnumField(Map<String, MEnumVO> enumField) {
		PropertiesUtil.enumField = enumField;
	}

	public static void setHasExitsField(Set<String> hasExitsField) {
		PropertiesUtil.hasExitsField = hasExitsField;
	}
}
