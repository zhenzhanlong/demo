package com.demo.constants;

/** 枚举使用实例**/
public enum DemoType {
	
	YES("是"),
	NO("否");
	/**说明*/
	private String description;

	private DemoType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
