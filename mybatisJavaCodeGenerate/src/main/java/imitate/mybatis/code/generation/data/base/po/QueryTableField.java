package imitate.mybatis.code.generation.data.base.po;

import java.io.Serializable;
import java.math.BigInteger;
/**
 * 表字段类
 * @author lenovo
 *
 */
public class QueryTableField implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;
	
	private String table_catalog;
	
	private String table_schema;
	
	private String table_name;

	/*** 列英文名称***/
	private String column_name;
	/** 列 sno**/
	private BigInteger ordinal_position;
	/** 列默认值**/
	private String colunm_default;
	/**** 是否为空 **/
	private String is_nullable;
	/**** 数据类型（如：int） **/
	private String data_type;
	/**
	 * 字段长度 如varchar(32) 的32
	 * CHARACTER_MAXIMUM_LENGTH：以字符为单位的最大长度，适于二进制数据、
	 * 字符数据，或者文本和图像数据。否则，返回 NULL。
	 */
	private BigInteger character_maxinum_length;
	/**
	 * 以字节为单位的最大长度，适于二进制数据、字符数据，或者文本和图像数据。否则，返回 NULL。
	 */
	private BigInteger character_octet_length;
	/** numeric 的数据精度 如numeric(14,4) 的10**/
	private BigInteger numeric_precision;
	/** numeric 的数据精度 如numeric(14,4) 的4**/
	private BigInteger numeric_scale;
	/** 时间类型精度**/
	private BigInteger datatime_precision;
	/** 编码设置**/
	private String character_set_name;
	
	private String collation_name;
	/**** 列类型（如 int(11)） **/
	private String column_type;
	/** 列 key (表示是主键，索引等 PRI，UNI)**/
	private String column_key;
	
	private String extra;
	/** 列权限 （这一列是否允许增删改查）**/
	private String privileges;
	/**** 列备注 **/
	private String column_comment;
	
	public QueryTableField() {
		super();
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
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
	public String getTable_catalog() {
		return table_catalog;
	}
	public void setTable_catalog(String table_catalog) {
		this.table_catalog = table_catalog;
	}
	public String getTable_schema() {
		return table_schema;
	}
	public void setTable_schema(String table_schema) {
		this.table_schema = table_schema;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getColunm_default() {
		return colunm_default;
	}
	public void setColunm_default(String colunm_default) {
		this.colunm_default = colunm_default;
	}
	public BigInteger getCharacter_maxinum_length() {
		return character_maxinum_length;
	}
	public void setCharacter_maxinum_length(BigInteger character_maxinum_length) {
		this.character_maxinum_length = character_maxinum_length;
	}
	public BigInteger getCharacter_octet_length() {
		return character_octet_length;
	}
	public void setCharacter_octet_length(BigInteger character_octet_length) {
		this.character_octet_length = character_octet_length;
	}
	public BigInteger getNumeric_precision() {
		return numeric_precision;
	}
	public void setNumeric_precision(BigInteger numeric_precision) {
		this.numeric_precision = numeric_precision;
	}
	public BigInteger getNumeric_scale() {
		return numeric_scale;
	}
	public void setNumeric_scale(BigInteger numeric_scale) {
		this.numeric_scale = numeric_scale;
	}
	public BigInteger getDatatime_precision() {
		return datatime_precision;
	}
	public void setDatatime_precision(BigInteger datatime_precision) {
		this.datatime_precision = datatime_precision;
	}
	public String getCharacter_set_name() {
		return character_set_name;
	}
	public void setCharacter_set_name(String character_set_name) {
		this.character_set_name = character_set_name;
	}
	public String getCollation_name() {
		return collation_name;
	}
	public void setCollation_name(String collation_name) {
		this.collation_name = collation_name;
	}
	public String getColumn_key() {
		return column_key;
	}
	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	public void setOrdinal_position(BigInteger ordinal_position) {
		this.ordinal_position = ordinal_position;
	}
	public String getIs_nullable() {
		return is_nullable;
	}
	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}
	public BigInteger getOrdinal_position() {
		return ordinal_position;
	}
	
}
