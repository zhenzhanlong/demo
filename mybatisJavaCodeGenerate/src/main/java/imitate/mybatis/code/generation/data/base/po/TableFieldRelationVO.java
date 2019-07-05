package imitate.mybatis.code.generation.data.base.po;

import java.io.Serializable;
import java.util.List;

import imitate.mybatis.code.generation.field.JavaField;

/**
 * table表与字段的包装类
 * @author Administrator
 *
 */
public class TableFieldRelationVO implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;
	/** 表信息 **/
	private DataBaseTable table;
	/** 表对应字段信息 **/
	private List<JavaField> javaFieldList;

	public TableFieldRelationVO() {
		super();
	}

	public TableFieldRelationVO(DataBaseTable table, List<JavaField> javaFieldList) {
		super();
		this.table = table;
		this.javaFieldList = javaFieldList;
	}

	public String getTableName() {
		if (null != table) {
			return table.getTable_name();
		}
		return null;
	}

	public DataBaseTable getTable() {
		return table;
	}

	public void setTable(DataBaseTable table) {
		this.table = table;
	}

	public List<JavaField> getJavaFieldList() {
		return javaFieldList;
	}

	public void setJavaFieldList(List<JavaField> javaFieldList) {
		this.javaFieldList = javaFieldList;
	}

}
