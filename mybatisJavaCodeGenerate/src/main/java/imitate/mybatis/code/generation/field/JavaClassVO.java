package imitate.mybatis.code.generation.field;

import java.io.Serializable;

/**
 * 表字段类
 * 
 * @author lenovo
 *
 */
public class JavaClassVO implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;
	/** 别名 **/
	public String aliasName;
	/** 类名 **/
	private String className;
	/** 类路径 **/
	private String classPath;
	
	public JavaClassVO() {
		super();
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	
}
