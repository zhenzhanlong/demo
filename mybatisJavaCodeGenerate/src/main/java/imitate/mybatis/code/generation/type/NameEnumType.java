package imitate.mybatis.code.generation.type;
/**
 * 名字类型枚举类
 * @author lenovo
 * https://www.gaoxingongzi.com/manage/test/threeElements?threadNum=100
 */
public enum NameEnumType {
	PO_NAME("po的名称"),
	VO_NAME("vo的名称"),
	FORM_NAME("form的名称"),
	DAO_API_NAME("dao的名称"),
	MAPPER_NAME("mapper的名称"),
	SERVICE_NAME("service的名称"),
	SERVICE_API_NAME("service的api名称"),
	BIZ_NAME("biz的名称"),
	BIZ_API_NAME("biz的api名称"),
	CONTROLLER_NAME("controller的名称"),
	WEB_SERVICE_NAME("web端service名称"),
	MAPPER_MAP_NAME("mapper的Map的名称");
	/**说明*/
	private String description;
	
	private NameEnumType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
