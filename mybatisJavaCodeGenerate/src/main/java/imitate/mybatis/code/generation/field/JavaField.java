package imitate.mybatis.code.generation.field;

import java.io.Serializable;
import java.math.BigInteger;

import imitate.mybatis.code.generation.type.JdbcType;
import imitate.mybatis.code.generation.util.JackJsonUtil;
import imitate.mybatis.code.generation.util.NameUtil;
/**
 * java 字段类型
 * 
 * @author lenovo
 *
 */
public class JavaField implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;
	/**
	 * 属性类型
	 */
	private String javaType;
	/**
	 * 属性名称
	 */
	private String javaName;
	/**
	 * java已经存在类的引入路径
	 */
	private String importPath;

	private String tableCatalog;

	private String tableSchema;

	private String tableName;

	/*** 列英文名称 ***/
	private String columnName;
	/** 列 sno **/
	private BigInteger ordinalPosition;
	/** 列默认值 **/
	private String colunmDefault;
	/**** 是否为空 **/
	private String isNullable;
	/**** 数据类型（如：int） **/
	private String columnDataType;
	/**
	 * 字段长度 如varchar(32) 的32
	 */
	private BigInteger characterMaxinumLength;

	private BigInteger characterOctetLength;
	/** numeric 的数据精度 如numeric(14,4) 的10 **/
	private BigInteger numericPrecision;
	/** numeric 的数据精度 如numeric(14,4) 的4 **/
	private BigInteger numericScale;

	private BigInteger datatimePrecision;

	private String characterSetName;

	private String collationName;
	/**** 列类型（如 int(11)） **/
	private String columnType;
	/** 列 key (表示是主键，索引等 PRI，UNI) **/
	private String columnKey;

	private String extra;
	/** 列权限 （这一列是否允许增删改查） **/
	private String privileges;
	/**** 列备注 **/
	private String columnComment;

	/**** 类需要引入字段的类型 **/
	private boolean needImport;
	/**** 字段属性为 class 的class 路径 **/
	private String classPath;
	/** java 类型 **/
	private Class<?> classT;
	/** 数据库jdbc 类型 **/
	private JdbcType jdbcType;

	public JavaField() {
		super();
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	public Class<?> getClassT() {
		return classT;
	}

	public void setClassT(Class<?> classT) {
		this.classT = classT;
	}

	public JavaField(String javaType, String javaName) {
		super();
		this.javaType = javaType;
		this.javaName = javaName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getColumnDataType() {
		return columnDataType;
	}

	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}

	public String getImportPath() {
		return importPath;
	}

	public void setImportPath(String importPath) {
		this.importPath = importPath;
	}

	public String getMethod() {
		return "get" + NameUtil.firstCharacterToUpper(this.javaName);
	}

	public String setMethod() {
		return "set" + NameUtil.firstCharacterToUpper(this.javaName);
	}

	public String getTableCatalog() {
		return tableCatalog;
	}

	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public BigInteger getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(BigInteger ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColunmDefault() {
		return colunmDefault;
	}

	public void setColunmDefault(String colunmDefault) {
		this.colunmDefault = colunmDefault;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public BigInteger getCharacterMaxinumLength() {
		return characterMaxinumLength;
	}

	public void setCharacterMaxinumLength(BigInteger characterMaxinumLength) {
		this.characterMaxinumLength = characterMaxinumLength;
	}

	public BigInteger getCharacterOctetLength() {
		return characterOctetLength;
	}

	public void setCharacterOctetLength(BigInteger characterOctetLength) {
		this.characterOctetLength = characterOctetLength;
	}

	public BigInteger getNumericPrecision() {
		return numericPrecision;
	}

	public void setNumericPrecision(BigInteger numericPrecision) {
		this.numericPrecision = numericPrecision;
	}

	public BigInteger getNumericScale() {
		return numericScale;
	}

	public void setNumericScale(BigInteger numericScale) {
		this.numericScale = numericScale;
	}

	public BigInteger getDatatimePrecision() {
		return datatimePrecision;
	}

	public void setDatatimePrecision(BigInteger datatimePrecision) {
		this.datatimePrecision = datatimePrecision;
	}

	public String getCharacterSetName() {
		return characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	public String getCollationName() {
		return collationName;
	}

	public void setCollationName(String collationName) {
		this.collationName = collationName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
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

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public boolean isNeedImport() {
		return needImport;
	}

	public void setNeedImport(boolean needImport) {
		this.needImport = needImport;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String toString() {
		return "JavaField:" + JackJsonUtil.objToJson(this);
	}
}
