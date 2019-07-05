package imitate.mybatis.code.generation.creating.entity.file;

import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.type.CreateFileType;
import imitate.mybatis.code.generation.util.FieldUtil;

/**
 * po类文件生成类
 * 
 * @author Administrator
 *
 */
public class VOFileCreating extends EntityCreatingFile {

	public VOFileCreating(Configuration configuration) {
		super(configuration);
	}

	// 文件生成
	@Override
	public void createFile() {
		// 生成文件
		super.createFile(fileConfig.getVoPackage(), fileConfig.getVoFileName(), content.toString());
	}

	// 类末位部分
	@Override
	public String classEnd() {
		return "";
	}

	// 文件头部分
	@Override
	public String classHead() {
		return headContent(fileConfig.getVoPackage(), CreateFileType.VO);
	}

	// 文件开始部分
	@Override
	public String classBegin() {
		return classBeginContent(fileConfig.getTable().getTable_comment(), fileConfig.getVoName(), "BaseVO");
	}

	// 构造函数部分
	public String constractor() {
		return constractorContent(fileConfig.getVoName(), fileConfig.getJavaFieldList().get(0));
	}

	// 字段信息转换为 属性声明信息
	public String attribute(JavaField fieldVO) {
		return FieldUtil.attribute(fieldVO, CreateFileType.VO);
	}

	// get、set方法
	public String getSetMethod(JavaField fieldVO) {
		return FieldUtil.getSetMethod(fieldVO, CreateFileType.VO);
	}

	// 构造函数部分
	public String toString() {
		return toStringContent(fileConfig.getVoName());
	}

	// class类后面追加内容
	@Override
	public String classAfterAppend() {
		return "";
	}

}
