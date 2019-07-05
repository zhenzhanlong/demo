package imitate.mybatis.code.generation.field;

import imitate.mybatis.code.generation.util.JackJsonUtil;

/**
 * 属性类
 * 
 * @author Administrator
 *
 */
public class PropertiesVO {
	private String name;

	private String value;

	private String path;

	public PropertiesVO() {
		super();
	}

	public PropertiesVO(String name, String value, String path) {
		super();
		this.name = name;
		this.value = value;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String toString() {
		return "PropertiesVO:" + JackJsonUtil.objToJson(this);
	}
}
