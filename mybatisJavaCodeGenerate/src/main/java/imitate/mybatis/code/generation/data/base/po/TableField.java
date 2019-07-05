package imitate.mybatis.code.generation.data.base.po;

import java.io.Serializable;
/**
 * 表字段类
 * @author lenovo
 *
 */
public class TableField implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;
	/*** 列英文名称***/
	private String column_name;
	/**** 列的位置 **/
	private int ordinal_position;
	/**** 是否为空 **/
	private String is_nullable;
	/**** 数据类型（如：int） **/
	private String data_type;
	/**** 列类型（如 int(11)） **/
	private String column_type;
	/**** 列备注 **/
	private String column_comment;
	/**** 类需要引入字段的类型 **/
	private boolean needImport;
	/**** 字段属性为 class 的class 路径**/
	private String class_path;
	
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public int getOrdinal_position() {
		return ordinal_position;
	}
	public void setOrdinal_position(int ordinal_position) {
		this.ordinal_position = ordinal_position;
	}
	public String getIs_nullable() {
		return is_nullable;
	}
	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}
	public String getColumn_comment() {
		return column_comment;
	}
	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public TableField() {
		super();
	}
	public boolean isNeedImport() {
		return needImport;
	}
	public void setNeedImport(boolean needImport) {
		this.needImport = needImport;
	}
	public String getClass_path() {
		return class_path;
	}
	public void setClass_path(String class_path) {
		this.class_path = class_path;
	}
	
}
