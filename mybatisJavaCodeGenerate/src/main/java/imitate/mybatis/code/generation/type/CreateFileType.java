package imitate.mybatis.code.generation.type;
/**
 * 需要创建的文件类型
 * @author Administrator
 *
 */
public enum CreateFileType {
	PO("po文件"),
	VO("vo问价"),
	FORM("form文件"),
	IDAO("dao文件"),
	ISEVICE("service文件"),
	SERVICE_IMPL("serviceImpl文件"),
	IBIZ("biz文件"),
	BIZ_IMPL("bizImpl文件"),
	CONTROLLER("controller文件"),
	IWEB_SERVICE("web端service文件"),
	WEB_SERVICE_IMPL("web端serviceImpl文件"),
	ACTION("action的文件"),
	MAPPER_MAP_NAME("mapper的Map的名称");
	/**说明*/
	private String description;
	
	private CreateFileType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
