package com.know.code.generation.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.know.code.generation.constants.MConstants;
import com.know.code.generation.constants.type.NameEnumType;
import com.know.code.generation.mybatis.po.MTableFieldPO;
import com.know.code.generation.mybatis.po.MTablePO;
import com.know.code.generation.mybatis.po.vo.MEnumVO;
import com.know.code.generation.service.IMTableService;
import com.know.code.generation.util.FileUtil;
import com.know.code.generation.util.NameUtil;
import com.know.code.generation.util.PropertiesUtil;
import com.know.code.generation.util.file.BizApiFileUtil;
import com.know.code.generation.util.file.BizFileUtil;
import com.know.code.generation.util.file.ControllerFileUtil;
import com.know.code.generation.util.file.DaoFileUtil;
import com.know.code.generation.util.file.FormFileUtil;
import com.know.code.generation.util.file.MyBatisFileUtil;
import com.know.code.generation.util.file.POFileUtil;
import com.know.code.generation.util.file.ServiceApiFileUtil;
import com.know.code.generation.util.file.ServiceFileUtil;
import com.know.code.generation.util.file.VOFileUtil;
import com.know.code.generation.util.file.WebServiceFileUtil;

/**
 * 逻辑业务层 代码实现类
 * 
 * @author lenovo
 *
 */
@Service("tableBizImpl")
public class MTableBizImpl implements IMTableBiz {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "tableServiceImpl")
	private IMTableService tableServiceImpl;

	/** 开始生成文件 */
	public void createFiles() {
		// 数据库实例名称
		String mysql_instance = String.valueOf(PropertiesUtil.getValue(MConstants.MYSQL_INSTANCE));
		logger.debug("数据库实例 mysql_instance=" + mysql_instance);
		// 查询数据库表集合
		List<MTablePO> tableList = this.tableServiceImpl.queryTableList(mysql_instance);
		List<MTableFieldPO> classFieldList = null;
		List<MTableFieldPO> myBatisFieldList = null;
		for (MTablePO tablePO : tableList) {
			myBatisFieldList = this.tableServiceImpl.queryTableFieldList(mysql_instance, tablePO.getTable_name());
			// 去除多余字段（如basePO,baseForm里面存在的字段），增加需要的枚举字段
			classFieldList = enumFieldAddAndRemoveBaseHasField(myBatisFieldList);
			logger.debug("生成表：" + tablePO.getTable_name() + ",数据");
			// 生成 po文件
			createPOFile(tablePO, classFieldList);
			// 生成 vo文件
			createVOFile(tablePO, classFieldList);
			// 生成 form 文件
			createFormFile(tablePO, classFieldList);
			// 生成dao文件
			createDaoFile(tablePO, null);
			// 生成 mapper 文件
			createMybatisFile(tablePO, myBatisFieldList);
			// 生成service api 文件
			createServiceApiFile(tablePO);
			// 生成service 文件
			createServiceFile(tablePO);
			// 生成biz api 文件
			createBizApiFile(tablePO);
			// 生成biz 文件
			createBizFile(tablePO);
			// 生成web端service 文件
			createWebService(tablePO);
			// 生成controller
			createControllerFile(tablePO);
		}
	}

	/** 生成po文件 ***/
	private boolean createPOFile(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		// 生成文件内容
		String content = POFileUtil.content(tablePO, fieldList);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_PO;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.PO_NAME) + ".java", content);

		return true;
	}

	/** 生成vo文件 ***/
	private boolean createVOFile(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		// 生成文件内容
		String content = VOFileUtil.content(tablePO, fieldList);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_VO;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.VO_NAME) + ".java", content);

		return true;
	}

	/** 生成form 文件 ***/
	private boolean createFormFile(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		// 生成文件内容
		String content = FormFileUtil.content(tablePO, fieldList);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_FORM;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.FORM_NAME) + ".java", content);

		return true;
	}

	/** 生成 dao 文件 **/
	private boolean createDaoFile(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		// 生成文件内容
		String content = DaoFileUtil.content(tablePO, fieldList);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_DAO_API;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.DAO_API_NAME) + ".java", content);

		return true;
	}

	/** 生成 mybatis 文件 **/
	private boolean createMybatisFile(MTablePO tablePO, List<MTableFieldPO> fieldList) {
		// 生成文件内容
		String content = MyBatisFileUtil.content(tablePO, fieldList);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_MAPPER;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.MAPPER_NAME) + ".xml", content);

		return true;
	}

	/** 生成service api 文件 ***/
	private boolean createServiceApiFile(MTablePO tablePO) {
		// 生成文件内容
		String content = ServiceApiFileUtil.content(tablePO, null);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_SERVICE_API;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_API_NAME) + ".java", content);

		return true;
	}

	/** 生成service 文件 ***/
	private boolean createServiceFile(MTablePO tablePO) {
		// 生成文件内容
		String content = ServiceFileUtil.content(tablePO, null);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_SERVICE;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.SERVICE_NAME) + ".java", content);

		return true;
	}

	/** 生成biz api 文件 ***/
	private boolean createBizApiFile(MTablePO tablePO) {
		// 生成文件内容
		String content = BizApiFileUtil.content(tablePO, null);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_BIZ_API;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.BIZ_API_NAME) + ".java", content);

		return true;
	}

	/** 生成biz 文件 ***/
	private boolean createBizFile(MTablePO tablePO) {
		// 生成文件内容
		String content = BizFileUtil.content(tablePO, null);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_BIZ;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.BIZ_NAME) + ".java", content);

		return true;
	}
	/** 生成web端service 文件 ***/
	private boolean createWebService(MTablePO tablePO) {
		// 生成文件内容
		String content = WebServiceFileUtil.content(tablePO);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_WEB_SERVICE;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.WEB_SERVICE_NAME) + ".java", content);

		return true;
	}
	/** 生成controller 文件 ***/
	private boolean createControllerFile(MTablePO tablePO) {
		// 生成文件内容
		String content = ControllerFileUtil.content(tablePO, null);
		// 生成文件
		String filePath = PropertiesUtil.getValue(MConstants.RELATIVE_PATH) + PropertiesUtil.getValue(MConstants.PACKAGE_PATH) + MConstants.DEFAULT_PACKAGE_PATH_CONTROLLER;
		FileUtil.createFile(filePath, NameUtil.createFileName(tablePO.getTable_name(), NameEnumType.CONTROLLER_NAME) + ".java", content);

		return true;
	}

	/**
	 * 加入或者替换掉 用户配置的 枚举类型的字段 如 data_status 实体类为枚举，数据库String， 需要将 String 替换为枚举类
	 * MDataStatusType 同时去掉 BasePO 里面已经存在的字段
	 * ***/
	private List<MTableFieldPO> enumFieldAddAndRemoveBaseHasField(final List<MTableFieldPO> fieldList) {
		List<MTableFieldPO> fieldListNew = new ArrayList<MTableFieldPO>();
		// 增加需要的枚举字段
		Map<String, MEnumVO> enumField = PropertiesUtil.getEnumField();
		// 去除多余的字段
		Set<String> fieldSet = PropertiesUtil.getHasExitsField();
		fieldList.forEach(field -> {
			if (!fieldSet.contains(field.getColumn_name())) {

				MEnumVO enumVO = enumField.get(field.getColumn_name());
				if (null != enumVO) {
					// 将数据库类型，替换为枚举类
				field.setNeedImport(true);
				field.setColumn_type(enumVO.getClass_name());
				field.setClass_path(enumVO.getPath());

			}
			fieldListNew.add(field);
		}
	})	;

		return fieldListNew;
	}
}
