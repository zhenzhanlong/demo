/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package imitate.mybatis.code.generation.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Clinton Begin
 */
public enum JavaType {
	/*
	 * This is added to enable basic support for the ARRAY data type - but a custom
	 * type handler is still required
	 */
	ARRAY(Types.ARRAY, "Array"), 
	BIT(Types.BIT, "Boolean"), 
	TINYINT(Types.TINYINT, "byte"), 
	SMALLINT(Types.SMALLINT, "Short"), 
	INT(Types.INTEGER, "Integer"),
	INTEGER(Types.INTEGER, "Integer"), 
	BIGINT(Types.BIGINT, "Long"), 
	FLOAT(Types.FLOAT, "BigDecimal"), 
	REAL(Types.REAL, "BigDecimal"), 
	DOUBLE(Types.DOUBLE, "BigDecimal"), 
	NUMERIC(Types.NUMERIC, "BigDecimal"),
	DECIMAL(Types.DECIMAL, "BigDecimal"), 
	CHAR(Types.CHAR,"Character"), 
	VARCHAR(Types.VARCHAR, "String"), 
	LONGVARCHAR(Types.LONGVARCHAR, "String"), 
	DATE(Types.DATE,"Date"), 
	TIME(Types.TIME, "Date"), 
	DATETIME(Types.DATE, "Date"),
	TIMESTAMP(Types.TIMESTAMP, "Date"), 
	BINARY(Types.BINARY,"byte[]"), 
	VARBINARY(Types.VARBINARY, "byte[]"), 
	LONGVARBINARY(Types.LONGVARBINARY,"Byte[]"), 
	NULL(Types.NULL, "NULL"), 
	OTHER(Types.OTHER, "OTHER"), 
	BLOB(Types.BLOB,"BLOB"), 
	CLOB(Types.CLOB,"CLOB"), 
	BOOLEAN(Types.BOOLEAN, "Boolean"), 
	CURSOR(-10, "CURSOR"), // Oracle
	UNDEFINED(Integer.MIN_VALUE + 1000, "UNDEFINED"), 
	NVARCHAR(Types.NVARCHAR, "String"), // JDK6
	NCHAR(Types.NCHAR, "NCHAR"), // JDK6
	NCLOB(Types.NCLOB, "NCLOB"), // JDK6
	STRUCT(Types.STRUCT, "STRUCT"), 
	JAVA_OBJECT(Types.JAVA_OBJECT, "Object"), 
	DISTINCT(Types.DISTINCT, "DISTINCT"), 
	REF(Types.REF,"REF"), 
	DATALINK(Types.DATALINK, "DATALINK"), 
	ROWID(Types.ROWID, "ROWID"), // JDK6
	LONGNVARCHAR(Types.LONGNVARCHAR, "String"), // JDK6
	SQLXML(Types.SQLXML, "String"), // JDK6
	DATETIMEOFFSET(-155, "DATETIMEOFFSET"); // SQL Server 2008

	public final int TYPE_CODE;
	public final String TYPE_NAME;
	private static Map<Integer, JavaType> codeLookup = new HashMap<>();
	private static Map<String, JavaType> javaNameLookup = new HashMap<>();

	static {
		for (JavaType type : JavaType.values()) {
			codeLookup.put(type.TYPE_CODE, type);
			javaNameLookup.put(type.toString(), type);
		}
	}

	JavaType(int code, String javaName) {
		this.TYPE_CODE = code;
		this.TYPE_NAME = javaName;
	}

	public static JavaType forCode(int code) {
		return codeLookup.get(code);
	}

	public static JavaType forJavaName(String jdbcTypeName) {
		JavaType returnType = javaNameLookup.get(jdbcTypeName.toUpperCase());
		//找不到就默认返回字符串的
		if(null==returnType) {
			returnType= JavaType.forJavaName("varchar");
		}
		return returnType;
	}
	public static void main(String[] args) {
		System.out.println(JavaType.forJavaName("varchar").toString());
		System.out.println(JavaType.forJavaName("varchar").TYPE_NAME);
		System.out.println(JavaType.forJavaName("int").toString());
		System.out.println(JavaType.forJavaName("int").TYPE_NAME);
	}
}
