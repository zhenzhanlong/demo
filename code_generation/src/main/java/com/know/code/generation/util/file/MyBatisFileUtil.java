package com.know.code.generation.util.file;

import java.util.List;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.NameEnumType;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;
import com.know.code.generation.util.FieldUtil;
import com.know.code.generation.util.NameUtil;
import com.know.code.generation.util.PathUtil;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class MyBatisFileUtil {
	public static String content(MTablePO tablePO,List<MTableFieldPO> fieldList){
		//mapper的类名
		String mapper_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.MAPPER_NAME);
		//通用select sql Id
		String selectSqlId =mapper_name+"SelectSql";
		//通用where and sql Id
		String wherrAndSqlId =mapper_name+"WhereAndSql";
		//通用where or sql Id
		String wherrOrSqlId =mapper_name+"WhereOrSql";
		//po名称
		String po_name=NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME);
		//result Map 名称
		String result_map_name = NameUtil.createFileName(tablePO.getTable_name(),NameEnumType.MAPPER_MAP_NAME);
		
		StringBuffer attribute = new StringBuffer();
					 attribute.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n")
					          .append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> \n")
					          .append("<mapper namespace=\"").append(PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.MAPPER_NAME))
					          .append(".").append(mapper_name).append("\"> \n");
		//字段字符串
		StringBuffer selectStr = new StringBuffer();
		//insert values 字符串
		StringBuffer insertStr = new StringBuffer();
		//where and条件
		StringBuffer andStr = new StringBuffer();
					 andStr.append("<where> \n");
		//where or 条件
		StringBuffer orStr = new StringBuffer();
					 orStr.append("<where> \n");
		//set 不判空，没有id字段 
		StringBuffer setStr = new StringBuffer();
					 setStr.append("<set> \n");
		//set 判空，没有id字段 
		StringBuffer setStrByIf = new StringBuffer();
					 setStrByIf.append("<set> \n");
		//mapper 字符串			 
		StringBuffer mapperStr = new StringBuffer();
					 mapperStr.append("		<resultMap id=\"").append(result_map_name).append("\" type=\"").append(po_name).append("\"> \n");
		for( MTableFieldPO filedPO : fieldList){
			//字段名
			String fieldName = NameUtil.subFieldName( filedPO.getColumn_name());
			//and 条件
			andStr.append("				<if test=\"").append(fieldName).append(" !=null \"> \n ")
			      .append(" 					and ").append(filedPO.getColumn_name()).append(" = #{").append(fieldName).append("} \n")
			      .append("				</if> \n");
			//or 条件
			orStr.append("				<if test=\"").append(fieldName).append(" !=null \"> \n ")
		      	  .append(" 					or ").append(filedPO.getColumn_name()).append(" = #{").append(fieldName).append("} \n")
		          .append("				</if> \n");
			//select 字段
			if(selectStr.length()>0){
				selectStr.append(",").append(filedPO.getColumn_name());
				insertStr.append(",").append("#{").append(filedPO.getColumn_name()).append("}");
			}else{
				selectStr.append(filedPO.getColumn_name());
				insertStr.append("#{").append(filedPO.getColumn_name()).append("}");
			}
			//set 条件
			setStr.append("        ").append(filedPO.getColumn_name()).append("=#{").append(fieldName).append("},\n");
			//判断是否能更新(字段不在集合能就是能update)
			if( FieldUtil.notUpdateFieldList.indexOf(fieldName)<0){
				setStrByIf.append("				<if test=\"").append(fieldName).append(" !=null \"> \n ")
		      	  .append("        			").append(filedPO.getColumn_name()).append("=#{").append(fieldName).append("},\n")
		          .append("				</if> \n");
			}											
			mapperStr.append("			<result property=\"").append(filedPO.getColumn_name())
			.append("\" column=\"").append(fieldName).append("\" />\n");
		}
		setStr.append("		</set>");
		setStrByIf.append("		</set>");
		orStr.append("			</where>");
		andStr.append("			</where>");
/* 		        
        		 
        
         		<association property="bank" javaType="MConsumerBankMappingEntityPO" 
	 		column="consumer_id = id , consumer_type = consumer_nature  "
	 		select="com.sx.manage.dao.bank.IMConsumerBankMapDaoMapper.queryOne" >
 		</association>
        */
		mapperStr.append("			<!-- 单个属性数据  \n")
				 .append(" 			<association property=\"bank\" javaType=\"MConsumerBankMappingEntityPO\" \n")
				 .append(" 				column=\"consumer_id = id , consumer_type = consumer_nature  \" \n")
				 .append("				select=\"com.sx.manage.dao.bank.IMConsumerBankMapDaoMapper.queryOne\" > \n")
				 .append("  			</association> --> \n")
				 .append(" 			<!-- 集合属性数据	\n")	
				 .append(" 			<collection property=\"function_list\" ofType=\"MFunctionEntityPO\" 	\n")	
				 .append(" 				column=\"pid = id , data_status = data_status \"  select=\"get_function_list\" >	\n")	
				 .append(" 			</collection> --> \n");
		mapperStr.append(" 		</resultMap> \n");
		
		//通用sql查询	
		attribute.append("		<!-- 通用查询sql --> \n")
		         .append("		<sql id=\"").append(selectSqlId).append("\">\n")
		         .append("			select \n ")
		         .append("      			").append(selectStr).append(" \n")
		         .append(" 			from ").append( tablePO.getTable_name() ).append(" \n")
				 .append("		</sql> \n");
		//通用where and sql
		attribute.append("		<!-- 通用where and sql --> \n")
		         .append("		<sql id=\"").append(wherrAndSqlId).append("\"> \n")
		         .append("    		").append(andStr).append(" \n ")
		         .append("		</sql> \n");
		//通用where or sql
		attribute.append("		<!-- 通用where or sql --> \n")
		         .append("		<sql id=\"").append(wherrOrSqlId).append("\"> \n")
		         .append("    		").append(orStr).append(" \n ")
		         .append("		</sql> \n");  
		//增加数据
		attribute.append("	<!-- 增加信息 --> \n ")
				 .append("	<insert id=\"").append(MConstants.DAO_METHOD_INSERT).append("\" parameterType=\"").append(po_name).append("\"> \n ")
				 .append("		insert into ").append( tablePO.getTable_name() ).append("\n")
				 .append("  		(").append(selectStr) .append(")").append("\n")
				 .append("			values \n		(").append(insertStr).append(") \n")
				 .append("	</insert> \n");
		//根据id删除
		attribute.append("	<!-- 根据id删除 --> \n")
				 .append("	<delete id=\"").append(MConstants.DAO_METHOD_DELETE_BY_ID).append("\" parameterType=\"").append(po_name).append("\"> \n ")
				 .append("		delete from  ").append( tablePO.getTable_name() )
				 .append("  	where id = #{id} \n" )
				 .append("	</delete>\n");
		//更新信息
		attribute.append("	<!-- 更新信息 --> \n")
				 .append("	<update id=\"").append(MConstants.DAO_METHOD_UPDATE).append("\" parameterType=\"").append(po_name).append("\"> \n ")
				 .append("		update   ").append( tablePO.getTable_name() ).append(" \n")
				 .append("		").append( setStrByIf ).append("\n")
				 .append("  		where id = #{id} \n" )
				 .append(" 	</update> \n");
		//根据id查询
		attribute.append("	<!-- 根据id查询 --> \n")
				 .append("	<select id=\"").append(MConstants.DAO_METHOD_QUERY_BY_ID).append("\" parameterType=\"").append(po_name).append("\" ")
				 .append(" 		resultMap=\"").append(result_map_name).append("\" > \n ")
				 .append("			<include refid=\"").append(selectSqlId).append("\"></include> \n")
				 .append("  		where id = #{id} \n" )
				 .append(" 	</select>\n");
		//查询满足条件的数量
		attribute.append("	<!-- 查询满足条件的数量 --> \n")
				 .append("	<select id=\"").append(MConstants.DAO_METHOD_QUERY_COUNT_NUM).append("\" parameterType=\"").append(po_name).append("\" ")
				 .append(" 	resultType=\"java.lang.Long\" > \n ")
				 .append("		select count(id) from \n		").append( tablePO.getTable_name() ).append("\n")
				 .append(" 		<include refid=\"").append(wherrAndSqlId).append("\"></include> \n")
				 .append(" 	</select> \n");
		//查询分页
		attribute.append("	<!-- 查询分页 --> \n")
				 .append("	<select id=\"").append(MConstants.DAO_METHOD_QUERY_PAGE).append("\" parameterType=\"").append(po_name).append("\" ")
				 .append(" 		resultMap=\"").append(result_map_name).append("\" > \n ")
				 .append("		<include refid=\"").append(selectSqlId).append("\"></include> \n")
				 .append(" 		<include refid=\"").append(wherrAndSqlId).append("\"></include> \n")
				 .append("		order by id DESC \n")
				 .append("		limit #{start},#{length} \n")
				 .append(" 	</select> \n");	
		//查询集合
				attribute.append(queryList( po_name, result_map_name, selectSqlId, wherrAndSqlId));
		//更改状态
				attribute.append(edit_status(po_name, tablePO.getTable_name()));
		//批量更改状态
				attribute.append(edit_status_batch(po_name, tablePO.getTable_name()));	
		//根据or条件获取唯一数据 
				attribute.append(byOrForUnique(po_name,orStr.toString(),result_map_name,selectSqlId));
		//根据or条件获取数据集合
				attribute.append(byOr(po_name,orStr.toString(),result_map_name,selectSqlId));		
		attribute.append(mapperStr)
		         .append("</mapper> \n");
		
		  return attribute.toString();            
		         
	}
	//查询集合
	public static String queryList(String po_name,String result_map_name,String selectSqlId,String wherrAndSqlId){
		StringBuffer attribute = new StringBuffer();
					attribute.append("	<!-- 查询集合 --> \n")
					 .append("	<select id=\"").append(MConstants.DAO_METHOD_QUERY_LIST).append("\" parameterType=\"").append(po_name).append("\" ")
					 .append(" 		resultMap=\"").append(result_map_name).append("\" > \n ")
					 .append("		<include refid=\"").append(selectSqlId).append("\"></include> \n")
					 .append(" 		<include refid=\"").append(wherrAndSqlId).append("\"></include> \n")
					 .append("		order by id DESC \n")
					 .append(" 	</select> \n");
		return attribute.toString();
	}
	//批量修改状态
	public static String edit_status_batch(String po_name,String table_name){		
		StringBuffer attribute = new StringBuffer();
		attribute.append("	<!-- 批量更改状态 --> \n")
		 .append("	<update id=\"").append(MConstants.DAO_METHOD_EDIT_STATUS_BATCH).append("\" parameterType=\"").append(po_name).append("\"> \n")
		 .append("		update  ").append( table_name ).append("\n")
		 .append(" 		set ").append(MConstants.DB_FIELD_DATA_STATUS).append("= #{").append(NameUtil.subFieldName( MConstants.DB_FIELD_DATA_STATUS)).append("} \n")
		 .append(" 		<where>  \n")
		 .append("        id in   \n")
		 .append("          <foreach collection=\"ids_\" item=\"id_\" index=\"index\" open=\"(\" separator=\",\" close=\")\" > \n")
		 .append("              #{id_}\n")
		 .append("           </foreach>\n")
		 .append("      </where>\n")
		 .append(" 	</update> \n");		
		return attribute.toString();	    
	}
	
	//更新状态
	public static String edit_status(String po_name,String table_name){
		StringBuffer attribute = new StringBuffer();
		attribute.append("	<!-- 更改状态 --> \n")
		 .append("	<update id=\"").append(MConstants.DAO_METHOD_EDIT_STATUS).append("\" parameterType=\"").append(po_name).append("\"> \n")
		 .append("		update  ").append( table_name ).append("\n")
		 .append(" 		set ").append(MConstants.DB_FIELD_DATA_STATUS).append("= #{").append(NameUtil.subFieldName( MConstants.DB_FIELD_DATA_STATUS)).append("} \n")
		 .append(" 		where id =  #{id}\n")
		 .append(" 	</update> \n");		
		return attribute.toString();
	}
	//根据or条件获取唯一数据 
	public static String byOrForUnique(String po_name,String orStr,String result_map_name,String selectSqlId){
		//根据or条件获取数据集合
		StringBuffer attribute = new StringBuffer();
		attribute.append("	<!-- 根据or条件获取唯一数据  --> \n")
		 .append("	<select id=\"").append(MConstants.DAO_METHOD_QUERY_UNIQUE_BY_OR).append("\" parameterType=\"").append(po_name).append("\" ")
		 .append(" 		resultMap=\"").append(result_map_name).append("\" > \n ")
		 .append("		<include refid=\"").append(selectSqlId).append("\"></include> \n")
		 .append(" 	 	").append(orStr).append(" \n")
		 .append(" 	</select> \n");
		return attribute.toString();
	}
	//根据or条件查询数据
	public static String byOr(String po_name,String orStr,String result_map_name,String selectSqlId){
		//根据or条件获取数据集合
		StringBuffer attribute = new StringBuffer();
				attribute.append("	<!-- 根据or条件获取数据集合  --> \n")
						 .append("	<select id=\"").append(MConstants.DAO_METHOD_QUERY_LIST_BY_OR).append("\" parameterType=\"").append(po_name).append("\" ")
						 .append(" 		resultMap=\"").append(result_map_name).append("\" > \n ")
						 .append("		<include refid=\"").append(selectSqlId).append("\"></include> \n")
						 .append(" 	 	").append(orStr).append(" \n")
						 .append("		order by id DESC \n")
						 .append(" 	</select> \n");
		return attribute.toString();
	}
}
