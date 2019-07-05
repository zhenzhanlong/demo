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
public class POFileCreating extends EntityCreatingFile {

	public POFileCreating(Configuration configuration) {
		super(configuration);
	}


	// 文件生成
	@Override
	public void createFile() {
		// 生成文件
		super.createFile(fileConfig.getPoPackage(), fileConfig.getPoFileName(), content.toString());
	}

	// 类末位部分
	@Override
	public String classEnd() {
		return "";
	}

	// 文件头部分
	@Override
	public String classHead() {
		return headContent(fileConfig.getPoPackage(), CreateFileType.PO);
	}

	// 文件开始部分
	@Override
	public String classBegin() {
		return classBeginContent(fileConfig.getTable().getTable_comment(), fileConfig.getPoName(), "BasePO");
	}

	// 构造函数部分
	public String constractor() {
		return constractorContent(fileConfig.getPoName(), fileConfig.getJavaFieldList().get(0));
	}

	// 字段信息转换为 属性声明信息
	public String attribute(JavaField fieldVO) {
		return FieldUtil.attribute(fieldVO, CreateFileType.PO);
	}

	// get、set方法
	public String getSetMethod(JavaField fieldVO) {
		return FieldUtil.getSetMethod(fieldVO, CreateFileType.PO);
	}

	// 构造函数部分
	public String toString() {
		return toStringContent(fileConfig.getPoName());
	}

	// class类后面追加内容
	@Override
	public String classAfterAppend() {
		return "";
	}

}
