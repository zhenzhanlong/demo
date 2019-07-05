package imitate.mybatis.code.generation.creating.entity.file;

import imitate.mybatis.code.generation.field.JavaField;
import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.type.CreateFileType;
import imitate.mybatis.code.generation.util.FieldUtil;

/**
 * form类文件生成类
 * 
 * @author Administrator
 *
 * 
 */
public class FormFileCreating extends EntityCreatingFile {

	public FormFileCreating(Configuration configuration) {
		super(configuration);
	}

	// 文件生成
	@Override
	public void createFile() {
		// 生成文件
		super.createFile(fileConfig.getFormPackage(), fileConfig.getFormFileName(), content.toString());
	}

	// 类末位部分
	@Override
	public String classEnd() {
		return convertToPO(fileConfig.getPoFileName());
	}

	// 文件头部分
	@Override
	public String classHead() {
		return headContent(fileConfig.getFormPackage(), CreateFileType.FORM);
	}

	// 文件开始部分
	@Override
	public String classBegin() {
		return classBeginContent(fileConfig.getTable().getTable_comment(), fileConfig.getFormName(), "BaseForm");
	}

	// 构造函数部分
	public String constractor() {
		return constractorContent(fileConfig.getFormName(), fileConfig.getJavaFieldList().get(0));
	}

	// 字段信息转换为 属性声明信息
	public String attribute(JavaField fieldVO) {
		return FieldUtil.attribute(fieldVO, CreateFileType.FORM);
	}

	// get、set方法
	public String getSetMethod(JavaField fieldVO) {
		return FieldUtil.getSetMethod(fieldVO, CreateFileType.FORM);
	}

	// 构造函数部分
	public String toString() {
		return toStringContent(fileConfig.getFormName());
	}

	// class类后面追加内容
	@Override
	public String classAfterAppend() {
		StringBuilder validateStr = new StringBuilder();
		validateStr.append(" // @Null   被注释的元素必须为 null \n ").append(" // @NotNull    被注释的元素必须不为 null  \n ")
				.append(" // @AssertTrue     被注释的元素必须为 true \n ").append(" // @AssertFalse    被注释的元素必须为 false    \n ")
				.append(" // @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值      \n ")
				.append(" // @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值     \n ")
				.append(" // @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值  \n    ")
				.append(" // @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值  \n  ")
				.append(" // @Size(max=, min=)   被注释的元素的大小必须在指定的范围内   \n   ")
				.append(" // @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内 \n ")
				.append(" // @Past   被注释的元素必须是一个过去的日期     \n ").append(" // @Future     被注释的元素必须是一个将来的日期 \n    ")
				.append(" // @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式   \n ")
				.append(" // @NotBlank(message =)   验证字符串非null，且长度必须大于0  \n ")
				.append(" // @Email  被注释的元素必须是电子邮箱地址  \n     ")
				.append(" // @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内   \n  ")
				.append(" // @NotEmpty   被注释的字符串的必须非空 \n    ")
				.append(" // @Range(min=,max=,message=)  被注释的元素必须在合适的范围内 \n  ");
		return validateStr.toString();
	}

	public String convertToPO(String poName) {
		StringBuilder str = new StringBuilder();
		str.append(" public ").append(poName).append(" convertToPO() { \n ").append("  ").append(poName)
				.append(" po = new ").append(poName).append("(); \n").append(" try { \n ")
				.append(" BeanUtils.copyProperties(this, po, false); \n ").append(" } catch (Exception e) { \n ")
				.append(" e.printStackTrace(); \n ").append(" } \n ").append("return po;\n ").append("} \n");
		return str.toString();
	}

	public static String addValidate(String formName) {
		StringBuilder str = new StringBuilder();
		str.append("public boolean addValidate(ResultVO<").append(formName).append("> result) { \n ")
				

				.append(" return true; \n ").append(" } \n ");
		return str.toString();
	}
}
