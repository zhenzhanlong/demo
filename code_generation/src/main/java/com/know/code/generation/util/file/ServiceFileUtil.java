package com.know.code.generation.util.file;

import java.util.Date;
import java.util.List;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.NameEnumType;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;
import com.know.code.generation.util.DateUtil;
import com.know.code.generation.util.FieldUtil;
import com.know.code.generation.util.NameUtil;
import com.know.code.generation.util.PathUtil;

/**
 * 文件生成帮助类
 * @author lenovo
 *
 */
public class ServiceFileUtil {
	public static String content(MTablePO tablePO,List<MTableFieldPO> fieldList){
		//po的类名
		String po_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME);
		//vo的类名
		String vo_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME);
		//form的类名
		String form_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME);
		//dao api名称
		String dao_api_name = NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.DAO_API_NAME);
		//dao impl 名称
		String dao_impl_name = NameUtil.createPrivateName(tablePO.getTable_name(), NameEnumType.DAO_API_NAME);
		StringBuilder attribute = new StringBuilder();
					 attribute.append("package ").append( PathUtil.createPackagePath(tablePO.getTable_name(), NameEnumType.SERVICE_NAME))
					 		  .append("import java.util.List; \n")
					 		  .append("import java.util.Date; \n")
					 		  .append("import com.sx.cache.Cache; \n")
					 		  .append("import com.sx.manage.constants.MDataStatusType; \n")
					 		  .append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.VO_NAME))
					 		  .append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.FORM_NAME))
					 		  .append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.PO_NAME)).append(";\n")
					 		  .append(PathUtil.createImportClassPath(tablePO.getTable_name(), NameEnumType.SERVICE_API_NAME))
					 		  .append("import com.sx.common.util.json.JackJsonUtil; \n")
					 		  //.append("import com.sx.manage.mybatis.vo.consumer.login.MConsumerLoginVO;\n")
					 		  .append("import com.sx.common.util.BeanUtils;\n")
					 		  .append("import com.sx.common.util.NumberUtil;\n")
					 		  .append("import org.slf4j.Logger;\n")
					 		  .append("import org.slf4j.LoggerFactory;\n")
					 		  .append(MConstants.CONSUMER_LOGIN_VO_PATH).append("\n")
					 		  .append(MConstants.RESULT_LIST_VO_PATH).append("\n")
					 		  .append(MConstants.RESULT_OBJECT_VO_PATH).append("\n")
					 		  .append(MConstants.RESULT_BOOLEAN_VO_PATH).append("\n")


			                  .append("/**\n")
			                  .append("  *").append( tablePO.getTable_comment() ).append("\n")
			                  .append("  *@author ").append(System.getProperties().getProperty("user.name")).append("\n")
			                  .append("  *@version 1.0.0 ").append(DateUtil.dateFormat(new Date(),DateUtil.DATE_FORMAT)).append(" \n")
			                  .append("  */\n")
			                  .append(" @Service(\"").append(NameUtil.createSpringServiceName(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_NAME))).append("\") \n")
			                  .append("public class ").append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_NAME)).append("  implements ")
			                  .append(NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_API_NAME)).append("{ \n")
					 		  .append("		/*** 日志*/ \n")
					 		  .append("		private Logger log = LoggerFactory.getLogger(this.getClass());\n \n")
					 		  .append("		@Resource\n")
					 		  .append("		private ").append(dao_api_name).append(" ").append(dao_impl_name).append(";\n\n")
					 		  .append("     @Resource(name=\"redisCache\") \n")
					 		  .append("		protected Cache cache; \n")
					 		  .append("		public ").append(dao_api_name).append(" ").append(FieldUtil.getMethod(dao_impl_name)).append("(){ \n")
					 		  .append("			return this.").append(dao_impl_name).append(";\n")
					 		  .append("		}\n\n")
					 		  .append("		public void ").append(FieldUtil.setMethod(dao_impl_name)).append("(").append( dao_api_name ).append(" ").append(dao_impl_name).append("){ \n")
					 		  .append("			 this.").append(dao_impl_name).append(" = ").append(dao_impl_name) .append(";\n")
					 		  .append("		}\n \n \n");
					 		  
		StringBuilder  methodStr = new StringBuilder(); 

				      //删除数据	 
				      methodStr.append("	/** 1根据id删除数据 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultBooleanVO  ").append(MConstants.SERVICE_METHOD_DELETE_BY_ID).append("( Long id){ \n ")
			 		   .append("    		log.debug(\"根据id删除数据id={}\",id); \n")
			 		   .append("  		if( null == id  ){ \n ")
			 		   .append("    		log.debug(\"ids 参数为空，直接返回\"); \n")
			 		   .append("  			return new ResultBooleanVO(\"m.parameter.is.null\");  \n")
			 		   .append("  		} \n ")
			 		   .append("  		ResultBooleanVO result = null; \n")
			 		   .append("  		").append(po_name).append(" po_ = new ").append(po_name).append("(); \n")
			 		   .append("  				po_.setId( id ); \n")
			 		   .append("  		Long number = Long.valueOf(0);   \n")
			 		   .append("  		try{ \n ")
			 		   .append("				log.debug(\"开始删除数据\"); \n")
			 		   .append("				number = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_DELETE_BY_ID).append("(po_); \n")
			 		   .append("				return ServiceUtil.returlBoolean(number, \"m.delete.data.is.not.exits\"); \n")
			 		   .append("			}catch(Exception ex){ \n")
			 		   .append("				log.error(\"error={}\",ex); \n")
			 		   .append("				result = new ResultBooleanVO(\"m.delete.data.is.error\"); \n")
			 		   .append("			}\n")
			 		   .append("		return result; \n")
			 		   .append("	}//end delete \n \n \n");
				      
			 		  
				      //修改数据	 
				      methodStr.append("	/** 2更新数据信息 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultBooleanVO update( ").append( form_name).append("  form ){ \n ")
			 		   .append("		ResultBooleanVO result = null; \n")
				       .append("			").append(po_name).append(" po_ = createPO( form) ;\n")
			 		   .append(" 		Long number = Long.valueOf(0);  \n")
			 		   .append("		try{ \n")
			 		   .append("				number = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_UPDATE).append("(po_); \n")
			 		   .append("				return ServiceUtil.returlBoolean(number, \"m.edit.data.is.error\"); \n")
			 		   .append("			}catch(Exception ex){ \n")
			 		   .append("				result = new ResultBooleanVO(\"m.edit.data.is.error\"); \n")
			 		   .append("				log.error(\"error={}\",ex); \n")
			 		   .append("    		}\n")
			 		   .append(" 		return result;\n")	
			 		   .append("	} //updatePO \n \n \n");
				      //修改数据	 
				      methodStr.append("	/** 3 新增数据信息 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultBooleanVO insert( ").append( form_name).append("  form){ \n ")
			 		   .append("		ResultBooleanVO result = addValidate(form); \n")
				       .append("         if( !result.isSuccess() ){ \n")
				       .append("          	log.debug(\"数据重复直接返回 \");\n")
				       .append("          	return result;\n")
				       .append("         } // end if \n ")
				       .append("			").append(po_name).append(" po_ = createPO( form) ;\n")
			 		   .append(" 		Long number = Long.valueOf(0);   \n")
			 		   .append("		try{ \n")
			 		   .append("                Long id = Long.valueOf(SquenceUtil.makePrimarykey(PrimarykeyClass.SX_SHOPPING_CATEGORY,\"\", cache) );\n")
			 		   .append("                po_.setId(id); \n")		
			 		   .append("                po_.setCreate_time( new Date()); \n")
			 		   .append("				number = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_INSERT).append("(po_); \n")
			 		   .append("				return ServiceUtil.returlBoolean(number, \"m.edit.data.is.error\"); \n")
			 		   .append("			}catch(Exception ex){ \n")
			 		   .append("				result = new ResultBooleanVO(\"m.edit.data.is.error\"); \n")
			 		   .append("				log.error(\"error={}\",ex); \n")
			 		   .append("    		}\n")
			 		   .append(" 		return result;\n")	
			 		   .append("	} //updatePO \n \n \n");
						//根据id查询	 
				      methodStr.append("	/** 4根据id查询数据*/ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultObjectVO<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_BY_ID).append("( Long id ){ \n ")
					   .append(" 	ResultObjectVO<").append(vo_name).append(">  result = null; \n")	
					   .append("		").append(po_name).append(" po = new ").append(po_name).append("();\n")
					   .append("                         po.setId(id);")
					   .append(" 			try{ //查询数据库\n")
					   .append(" 				").append(po_name).append(" po_ = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_QUERY_BY_ID).append("( po );\n")
					   .append(" 				result = new ResultObjectVO<>(this.createVO(po_));\n")
					   .append(" 			}catch(Exception ex){\n")
					   .append(" 				log.error(\"error={}\",ex); \n")
					   .append(" 				return new ResultObjectVO<>(\"m.select.data.error\"); \n")
					   .append(" 			}\n")
					   .append("             if( null == result.getObjVo()){             \n")
					   .append("                result.setSuccess(false);            \n")
					   .append("                result.setMsg(\"数据不存在\");          \n")
					   .append("             }              \n")
					   .append(" 		return result; \n")
					   .append(" }// end byId \n \n \n");
						
							
							
						
						
				      //查询分页	 
				      methodStr.append("	/** 5查询分页*/ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultListVO<").append(vo_name).append(">  ").append(MConstants.SERVICE_METHOD_QUERY_PAGE).append("( ").append( form_name).append("  form ){ \n ")
					   .append(" 		ResultListVO<").append(vo_name).append(">  result  = null; \n")
					   .append(" 		try{ \n")
					   .append(" 				//生成po \n")
					   .append("				").append(po_name).append(" po_ = createPO( form) ;\n")
					   .append(" 				//查询数量  \n")
					   .append(" 				Long totalNum = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_QUERY_COUNT_NUM).append("( po_ );\n")
					   .append(" 				//查询你全部数据  \n")
					   .append(" 				List<").append(po_name).append("> poList_ = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_QUERY_PAGE).append("( po_ );\n")
					   .append(" 				result = new ResultListVO<>(this.createVOList( poList_),totalNum,totalNum); \n")
					   .append(" 			}catch(Exception ex){\n")
					   .append(" 				log.error(\"error={}\",ex); \n")
					   .append(" 				return new ResultListVO<>(\"m.select.data.error\");\n")
					   .append(" 			} \n")
					   .append(" 		return result; \n")
					   .append("	}// end page \n \n \n");
				      
						//更改数据状态	 
				      methodStr.append("	/** 6 根据ids 更改数据状态 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultBooleanVO  ").append(MConstants.SERVICE_METHOD_EDIT_STATUS_BATCH).append("( MDataStatusType data_status,Long[] ids){ \n ")
					   .append("    	String idStr = NumberUtil.arrayToStr(ids);\n")
					   .append("   	 	log.debug(\"更新数据状态信息：data_status：\" +data_status+\",ids=\"+idStr );\n")
					   .append("   		ResultBooleanVO result = null ;\n")
					   .append("    	try{\n")
					   .append("  				").append(po_name).append(" po_ = new ").append(po_name).append("(); \n")
			 		   .append("  					 			po_.setIds_( ids ); \n")
					   .append("   								po_.setData_status( data_status ); \n")
					   .append(" 				this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_EDIT_STATUS_BATCH).append("( po_ );\n")
					   .append("    			result = new ResultBooleanVO();\n")
					   .append("    		}catch(Exception ex){ \n")
					   .append("    			result = new ResultBooleanVO(\"m.edit.data.status.error\");\n")
					   .append("    			log.error(\"error={}\",ex); \n")
					   .append("    		} \n")
					   .append("    	return result; \n")
					   .append("    } // end data_status\n \n \n");

				      //根据or条件获取唯一数据 
				      methodStr.append("	/** 7 根据or条件获取唯一数据*/ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultObjectVO<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_UNIQUE_BY_OR).append("( ").append( form_name ).append("  form ){ \n ")
			 		   .append(" 		ResultObjectVO<").append(vo_name).append(">  result = new ResultObjectVO<>(); \n")
			 		   .append("		try{  \n ")
			 		   .append("				").append(po_name).append(" po = createPO( form) ;\n")
			 		   .append(" 				").append(po_name).append(" po_ = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_QUERY_UNIQUE_BY_OR).append("( po );\n")
			 		   .append("				if( null != po_ ){\n ")
			 		   .append("					result.setObjVo( this.createVO(po_) ); \n ")
			 		   .append("					return result;\n ")
			 		   .append("				}\n ")
			 		   .append("			}catch(Exception ex){ \n ")
			 		   .append("    			log.error(\"error={}\",ex); \n")
			 		   .append("				return new ResultObjectVO<>(\"m.select.data.error\");\n ")
			 		   .append("			} \n ")
			 		   .append("		return new ResultObjectVO<>(\"m.data.is.null\");\n ")
			 		   .append("	}\n \n \n ");
				      //根据or条件获取数据集合	 
				      methodStr.append("	/** 8 根据 or 条件获取数据集合 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultListVO<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_LIST_BY_OR).append("( ").append( form_name).append("  form ){ \n ")
				       .append(" 		ResultListVO<").append(vo_name).append(">  result =  new ResultListVO<>(); \n")
			 		   .append("		try{  \n ")
			 		   .append("				").append(po_name).append(" po_ = createPO( form) ;\n")
			 		   .append(" 				List<").append(po_name).append("> poList = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_QUERY_LIST_BY_OR).append("( po_ );\n")
			 		   .append("				if( null != poList && !poList.isEmpty() ){\n ")
			 		   .append("					result.setData( this.createVOList(poList ) ); \n ")
			 		   .append("					return result;\n ")
			 		   .append("				}\n ")
			 		   .append("			}catch(Exception ex){ \n ")
			 		   .append("    			log.error(\"error={}\",ex); \n")
			 		   .append("				return new ResultListVO<>(\"m.select.data.error\");\n ")
			 		   .append("			} \n ")
			 		   .append("		return new ResultListVO<>(\"m.data.is.null\");\n ")
			 		   .append("	}\n \n \n ");
				      //根据条件获取数据集合	 
				      methodStr.append("	/** 9 条件获取数据集合 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultListVO<").append(vo_name).append("> ").append(MConstants.SERVICE_METHOD_QUERY_LIST).append("( ").append( form_name).append("  form ){ \n ")
				       .append(" 		ResultListVO<").append(vo_name).append(">  result =  new ResultListVO<>(); \n")
			 		   .append("		try{  \n ")
			 		   .append("				").append(po_name).append(" po_ = createPO( form) ;\n")
			 		   .append(" 				List<").append(po_name).append("> poList = this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_QUERY_LIST).append("( po_ );\n")
			 		   .append("				if( null != poList && !poList.isEmpty() ){\n ")
			 		   .append("					result.setData( this.createVOList(poList ) ); \n ")
			 		   .append("					return result;\n ")
			 		   .append("				}\n ")
			 		   .append("			}catch(Exception ex){ \n ")
			 		   .append("    			log.error(\"error={}\",ex); \n")
			 		   .append("				return new ResultListVO<>(\"m.select.data.error\");\n ")
			 		   .append("			} \n ")
			 		   .append("		return new ResultListVO<>(\"m.data.is.null\");\n ")
			 		   .append("	}\n \n \n ");
				      
				    //更改数据状态	 
				      methodStr.append("	/** 10 根据ids 更改数据状态 */ \n ")
				       .append("    @Override   \n")
			 		   .append("	public ResultBooleanVO  ").append(MConstants.SERVICE_METHOD_EDIT_STATUS).append("( MDataStatusType data_status,Long id){ \n ")
					   .append("   	 	log.debug(\"更新数据状态信息：data_status={},ids={}\",data_status,id);\n")
					   .append("   		ResultBooleanVO result = null ;\n")
					   .append("    	try{\n")
					   .append("  				").append(po_name).append(" po_ = new ").append(po_name).append("(); \n")
			 		   .append("  					 			po_.setId( id ); \n")
					   .append("   								po_.setData_status( data_status ); \n")
					   .append(" 				this.").append(dao_impl_name).append(".").append(MConstants.DAO_METHOD_EDIT_STATUS).append("( po_ );\n")
					   .append("    			result = new ResultBooleanVO();\n")
					   .append("    		}catch(Exception ex){ \n")
					   .append("    			result = new ResultBooleanVO(\"m.edit.data.status.error\");\n")
					   .append("    			log.error(\"error={}\",ex); \n")
					   .append("    		} \n")
					   .append("    	return result; \n")
					   .append("    } // end data_status\n \n \n");
				      
				      //createPO方法
				      methodStr.append("	/**  from to po **/ \n")
				      		   .append("	private ").append( po_name ).append(" createPO(").append(form_name).append(" form ){ \n")
				      		    .append(" 		").append(po_name).append(" po = new ").append(po_name).append("(); \n")
				               .append(" 			if( null == form){ \n")
				               .append(" 				return po;\n")
				      		   .append(" 			}\n")
				      		  
				               .append("			try { //属性复制 \n")
				               .append("				BeanUtils.copyProperties(form,po,false); \n")
				               .append("			}catch (Exception ex) { \n")
				               .append("				log.error(\"error={}\",ex); \n")
				               .append("			}\n")
				               .append("		return po;  \n ")
				               .append("	}//end createPO  \n \n \n ");
				      
				      //
				      methodStr.append("	/**  formList 2 poList 类  **/	\n")
				      		   .append("	private List<").append(po_name).append("> createPOList( List<").append(form_name).append("> formList ){ \n")
				      		   .append("		if( null == formList || formList.isEmpty()){ \n ")
				      		   .append("			return  new ArrayList<>();\n")
				      		   .append("		} \n")				      			
				      		   .append("		//返回的集合\n")
				      		   .append("		List<").append(po_name).append("> poList_ = new ArrayList<>( formList.size() ); \n")
				      		   .append("		//批量数据复制 \n")
				      		   .append("		for(").append(form_name).append(" form : formList){\n")
				      		   .append("			//类型转换 \n")
				      		   .append("			poList_.add( this.createPO( form) );\n")
				      		   .append("		}\n")
				      		   .append("		return poList_; \n")
				      		   .append("	}\n \n \n");
				      //createVO方法						
				      methodStr.append("	/**  po to vo **/ \n")
				      		   .append("	private ").append( vo_name ).append(" createVO(").append(po_name).append(" po_ ){ \n")
				      		   .append("            if( null == po_){  \n")
				      		   .append("                return null; \n  ")
				      		   .append("             } \n")
				               .append(" 		").append(vo_name).append(" vo_ = new ").append(vo_name).append("(); \n")
				               .append("			try { //属性复制 \n")
				               .append("				BeanUtils.copyProperties(po_,vo_,false); \n")
				               .append("			}catch (Exception ex) { \n")
				               .append("				log.error(\"error={}\",ex); \n")
				               .append("			}\n")
				               .append("		return vo_ ; \n ")
				               .append("	} //end createVO  \n \n \n ");	
				 
				      //
				      methodStr.append("	/**  poList 2 voList 类  **/	\n")
				      		   .append("	private List<").append(vo_name).append("> createVOList( List<").append(po_name).append("> poList_ ){ \n")
				      		   .append("		if( null == poList_ || poList_.isEmpty()){ \n ")
				      		   .append("			return new ArrayList<>();\n")
				      		   .append("		} \n")				      			
				      		   .append("		//返回的集合\n")
				      		   .append("		List<").append(vo_name).append("> voList_ = new ArrayList<>( poList_.size() ); \n")
				      		   .append("		//批量数据复制 \n")
				      		   .append("		for(").append(po_name).append(" po_ : poList_){\n")
				      		   .append("			//类型转换 \n")
				      		   .append("			voList_.add( this.createVO(po_) );\n")
				      		   .append("		}\n")
				      		   .append("		return voList_; \n")
				      		   .append("	}\n \n \n");
				     
				      //添加时唯一性的验证
				      methodStr.append("/** 添加时唯一性的验证**/ \n")
				      		   .append(" private ResultBooleanVO addValidate(").append(form_name).append(" form){ \n")
				      		   .append(" 	log.debug(\"").append(vo_name).append("添加时唯一性验证\"); \n") 
				      		   .append(" 	ResultBooleanVO unique_validate = new ResultBooleanVO(); \n") 
				      		   .append("        ").append(po_name).append(" po = new ").append(po_name).append("();\n \n \n") 
				      		   .append(" 	log.debug(\"检查重复数据\");\n") 
				      		   .append(" 	List<").append(po_name).append("> poList =  this.").append(dao_impl_name).append(".queryList(po);\n") 
				      		   .append(" 	StringBuilder errorHead = new StringBuilder(); \n") 
				      		   .append(" 	StringBuilder errorEnd = new StringBuilder(); \n\n") 
				      		   .append(" 	if( !poList.isEmpty()){ \n") 
				      		   .append(" 		log.debug(\"数据重复\"); \n") 
				      		   .append(" 		errorEnd.append(\"数据重复\");\n")
				      		   .append("    } // end if \n \n") 
				      		   .append("    log.debug(\"检查数据重复（其它参数重复）\"); \n") 
				      		   .append(" 		poList =  this.").append(dao_impl_name).append(".queryList(po);\n") 
				      		   .append("    if( !poList.isEmpty()){\n") 
				      		   .append(" 		log.debug(\"数据重复\");\n")
				      		   .append("    	if(errorEnd.length()>0){ \n")
				      		   .append("    	   errorEnd.append(\",\").append(\"分类名称重复\");\n")
				      		   .append(" 		}else{ \n")
				      		   .append(" 			errorEnd.append(\"分类名称重复\");\n")
				      		   .append("    	}// end if errorEnd \n")
				      		   .append(" 	} // end if poList \n")
				      		   .append("    if( errorEnd.length()>0){\n")
				      		   .append(" 		errorHead.append(errorEnd);\n")
				      		   .append("        unique_validate.setSuccess(false); \n")
				      		   .append("        unique_validate.setMsg(errorHead.toString());\n")
				      		   .append("    }\n")
				      		   .append("    return unique_validate;\n")
				      		   .append(" } \n");
				      
				      //编辑时唯一性的验证
				      methodStr.append("/** 编辑时唯一性的验证**/ \n")
				      		   .append(" private ResultBooleanVO updateValidate(").append(form_name).append(" form){ \n")
				      		   .append(" 	log.debug(\"").append(vo_name).append("添加时唯一性验证\"); \n") 
				      		   .append(" 	ResultBooleanVO unique_validate = new ResultBooleanVO(); \n") 
				      		   .append("        ").append(po_name).append(" po = new ").append(po_name).append("();\n \n \n") 
				      		   .append(" 	log.debug(\"检查重复数据\");\n") 
				      		   .append(" 	List<").append(po_name).append("> poList =  this.").append(dao_impl_name).append(".queryList(po);\n") 
				      		   .append(" 	StringBuilder errorHead = new StringBuilder(); \n") 
				      		   .append(" 	StringBuilder errorEnd = new StringBuilder(); \n\n")				      		 
				      		   .append(" 	if( !poList.isEmpty()){ \n") 
				      		   .append("  	  for(").append(po_name).append(" po_: poList){\n")
				      		   .append("        //是数据自己，跳出本次循环验证 \n")	
				      		   .append("        if( po_.getId().equals(form.getId())){ \n")
				      		   .append("            continue; \n")
				      		   .append("         }  \n")
				      		   .append(" 		log.debug(\"数据重复\"); \n") 
				      		   .append(" 		errorEnd.append(\"数据重复\");\n")
				      		   .append("      }// end for \n")
				      		   .append("    } // end if \n \n") 
				      		   .append("    log.debug(\"检查数据重复（其它参数重复）\"); \n") 
				      		   .append(" 		poList =  this.").append(dao_impl_name).append(".queryList(po);\n") 
				      		   .append("    if( !poList.isEmpty()){\n") 
				      		   .append("  	  for(").append(po_name).append(" po_: poList){\n")
				      		   .append("        //是数据自己，跳出本次循环验证 \n")	
				      		   .append("        if( po_.getId().equals(form.getId())){ \n")
				      		   .append("            continue; \n")
				      		   .append("         }  ")
				      		   .append(" 		log.debug(\"数据重复\");\n")
				      		   .append("    	if(errorEnd.length()>0){ \n")
				      		   .append("    	   errorEnd.append(\",\").append(\"分类名称重复\");\n")
				      		   .append(" 		}else{ \n")
				      		   .append(" 			errorEnd.append(\"分类名称重复\");\n")
				      		   .append("    	}// end if errorEnd \n")
				      		   .append("       }// end for \n")
				      		   .append(" 	} // end if poList \n")
				      		   .append("    if( errorEnd.length()>0){\n")
				      		   .append(" 		errorHead.append(errorEnd);\n")
				      		   .append("        unique_validate.setSuccess(false); \n")
				      		   .append("        unique_validate.setMsg(errorHead.toString());\n")
				      		   .append("    }\n")
				      		   .append("    return unique_validate;\n")
				      		   .append(" } \n");
			
				
		attribute.append(methodStr)
		         .append("} //end class");
		
		  return attribute.toString();            
		         
	}
}
