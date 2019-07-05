package imitate.mybatis.code.generation.data.base.po;

import java.io.Serializable;
/**
 * 表字段类
 * @author lenovo
 *
 */
import java.math.BigInteger;
import java.util.Date;

public class DataBaseTable implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3876443989823994850L;

	private String table_catalog;

	private String table_schema;
	/*** 表名称 ***/
	private String table_name;
	/** 表类型
	 * SYSTEM VIEW  系统视图
		BASE TABLE  基本数据表
		VIEW  视图
	 */
	private String table_type;
	/** 数据库引擎类型
	 * MEMORY
	 * InnoDB
	 */
	private String engine;
	/**
	 * 版本号
	 */
	private BigInteger version;

	private String row_format;

	private BigInteger table_rows;
	/**
	 * 平均每行数据长度
	 */
	private BigInteger avg_row_length;
	/**
	 * 表数据大小
	 */
	private BigInteger data_length;
	/**
	 * 表最大是数据大小
	 */
	private BigInteger max_data_length;
	/**
	 * 索引大小
	 */
	private BigInteger index_length;

	private BigInteger data_free;
	/**
	 * 主动增长主键 当前值
	 */
	private BigInteger auto_increment;
	/**
	 * 创建时间
	 */
	private Date create_time;
	/**
	 * 更新时间
	 */
	private Date update_time;

	private Date check_time;

	private String table_collation;

	private BigInteger checksum;

	private String create_options;
	/*** 表介绍 **/
	private String table_comment;

	public DataBaseTable() {
		super();
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

	public String getTable_type() {
		return table_type;
	}

	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public BigInteger getVersion() {
		return version;
	}

	public void setVersion(BigInteger version) {
		this.version = version;
	}

	public String getRow_format() {
		return row_format;
	}

	public void setRow_format(String row_format) {
		this.row_format = row_format;
	}

	public BigInteger getTable_rows() {
		return table_rows;
	}

	public void setTable_rows(BigInteger table_rows) {
		this.table_rows = table_rows;
	}

	public BigInteger getAvg_row_length() {
		return avg_row_length;
	}

	public void setAvg_row_length(BigInteger avg_row_length) {
		this.avg_row_length = avg_row_length;
	}

	public BigInteger getData_length() {
		return data_length;
	}

	public void setData_length(BigInteger data_length) {
		this.data_length = data_length;
	}

	public BigInteger getMax_data_length() {
		return max_data_length;
	}

	public void setMax_data_length(BigInteger max_data_length) {
		this.max_data_length = max_data_length;
	}

	public BigInteger getIndex_length() {
		return index_length;
	}

	public void setIndex_length(BigInteger index_length) {
		this.index_length = index_length;
	}

	public BigInteger getData_free() {
		return data_free;
	}

	public void setData_free(BigInteger data_free) {
		this.data_free = data_free;
	}

	public BigInteger getAuto_increment() {
		return auto_increment;
	}

	public void setAuto_increment(BigInteger auto_increment) {
		this.auto_increment = auto_increment;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

	public String getTable_collation() {
		return table_collation;
	}

	public void setTable_collation(String table_collation) {
		this.table_collation = table_collation;
	}

	public BigInteger getChecksum() {
		return checksum;
	}

	public void setChecksum(BigInteger checksum) {
		this.checksum = checksum;
	}

	public String getCreate_options() {
		return create_options;
	}

	public void setCreate_options(String create_options) {
		this.create_options = create_options;
	}

	public String getTable_comment() {
		return table_comment;
	}

	public void setTable_comment(String table_comment) {
		this.table_comment = table_comment;
	}

}
