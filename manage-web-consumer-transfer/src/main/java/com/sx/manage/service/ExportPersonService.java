package com.sx.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sx.commons.Cryptos;
import com.sx.manage.dao.one.ICompanyOneDao;
import com.sx.manage.dao.one.IPersonOneDao;
import com.sx.manage.dao.two.IPersonTwoDao;
import com.sx.manage.mybatis.po.consumer.MCompanyEntityPO;
import com.sx.manage.mybatis.po.consumer.MPersonalConsumerInfoEntityPO;
import com.sx.manage.util.FileUtil;
import com.sx.manage.util.PasswordUtil;
import com.sx.model.manage.mybatis.po.consumer.MPersonalConsumerNetworkPO;

/**
 * 导出用户service
 * 
 * @author Administrator
 *
 */
@Service("exportPersonService")
public class ExportPersonService {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ICompanyOneDao companyOneDao;
	@Autowired
	private IPersonOneDao personOneDao;
	@Autowired
	private IPersonTwoDao personTwoDao;
	//导出文件路径
	@Value("${export.path}")
	private String exportPath;
	//导出文件名称
	@Value("${export.file.name}")
	private String exportFileName;
	//导入公司名称
	@Value("${export.company.name}")
	private String exportCompanyName;
	public void exportPage() {
		log.info("开始查询数据exportCompanyName={}",exportCompanyName);
		List<String> companyNameList = null;
		if(StringUtils.isNotBlank(exportCompanyName)) {
			String[] exportCompanyNames = exportCompanyName.split(",");
			companyNameList = new ArrayList<>(exportCompanyNames.length);
			for(String companyName:exportCompanyNames) {
				log.info("表内存在公司companyName={}",companyName);
				companyNameList.add(companyName);
			}
		}
		// 查询全部公司
		List<MCompanyEntityPO> companyList = companyOneDao.getListAll();
		log.info("查询公司数据size={}", companyList.size());
		// 根据公司查询用户
		List<String> sqlList = new ArrayList<>();
		for (MCompanyEntityPO companyPO : companyList) {
			log.info("表内公司company_id={},company_name={} ", companyPO.getId(), companyPO.getCompany_name());
			if(null!=companyNameList) {
				if(companyNameList.indexOf(companyPO.getCompany_name())>=0) {
					log.info("需要导入的公司数据，开始导入 company_name={}",companyPO.getCompany_name());
					sqlList.addAll(personist(companyPO.getId()));
				}
			}else {
				log.info("需要导入的公司数据，开始导入 company_name={}",companyPO.getCompany_name());
				sqlList.addAll(personist(companyPO.getId()));
			}
			
		}
		log.info("生产sql文件");
		FileUtil.createFile(exportPath, exportFileName, sqlList);
	}

	public List<String> personist(Long company_id) {
		List<String> sqlList = new ArrayList<>();
		// 1、查询一期用户数据
		MPersonalConsumerInfoEntityPO consumerOnePO = new MPersonalConsumerInfoEntityPO();
		consumerOnePO.setCompany_id(company_id);
		log.info("查询个人用户集合company_id={}", consumerOnePO.getCompany_id());
		List<MPersonalConsumerInfoEntityPO> consumerOneList = personOneDao.getListByPO(consumerOnePO);
		log.info("查询个人用户数量company_id={},size={}", consumerOnePO.getCompany_id(), consumerOneList.size());
		// 2、生成 lawNoList
		for (MPersonalConsumerInfoEntityPO consumerPO : consumerOneList) {
			// 生成sql
			if(StringUtils.isNotBlank(consumerPO.getLaw_no())) {
				sqlList.addAll(createSql(consumerPO));
			}
		}
		//
		return sqlList;
	}

	public List<String> createSql(MPersonalConsumerInfoEntityPO consumerPO) {
		List<String> sqlList = new ArrayList<>();
		MPersonalConsumerNetworkPO networkQueryPO = new MPersonalConsumerNetworkPO();
		networkQueryPO.setLaw_no(consumerPO.getLaw_no());
		log.info("需要查询的用户consumerPO={},networkQueryPO={}",consumerPO,networkQueryPO);
		MPersonalConsumerNetworkPO networkPO = personTwoDao.getPO(networkQueryPO);
		// 1、生成 sx_t_m_consumer_operation_info 支付密码，错误次数
		if (null != networkPO) {
			log.info("需要生成sql的用户consumer_name={},law_no={}",networkPO.getConsumer_name(),consumerPO.getLaw_no());
			if(StringUtils.isNotBlank(consumerPO.getPayment_password())) {
				log.info("payment_password={}",consumerPO.getPayment_password());
				String paymentPassword = Cryptos.decode(consumerPO.getPayment_password());
				String[] paymentPasswords = paymentPassword.split("#");
				StringBuilder operStr = new StringBuilder();
				operStr.append("UPDATE sx_t_m_consumer_operation_info set payment_password='")
						.append(PasswordUtil.encodePassword(networkPO.getId(), paymentPasswords[0]))
						.append("',auto_withdraw='").append(consumerPO.getAuto_withdraw()).append("' WHERE consumer_id=")
						.append(networkPO.getId()).append("; \n ");
				// UPDATE sx_t_m_consumer_operation_info SET
				// payment_password='',auto_withdraw='' WHERE consumer_id=?
				sqlList.add(operStr.toString());
			}
			if(StringUtils.isNotBlank(consumerPO.getPassword())) {
				// 2、生成sx_t_m_login_consumer_info 登录密码
				String password = Cryptos.decode(consumerPO.getPassword());
				String[] passwords = password.split("#");
				StringBuilder loginStr = new StringBuilder();
				loginStr.append("UPDATE sx_t_m_login_consumer_info SET PASSWORD='")
						.append(PasswordUtil.encodePassword(networkPO.getId(), passwords[0])).append("'")
						.append(" WHERE consumer_id=").append(networkPO.getId()).append("; \n ");
				// UPDATE sx_t_m_login_consumer_info SET PASSWORD='' WHERE consumer_id=?
				sqlList.add(loginStr.toString());
			}

		}
		return sqlList;
	}

	public ICompanyOneDao getCompanyOneDao() {
		return companyOneDao;
	}

	public void setCompanyOneDao(ICompanyOneDao companyOneDao) {
		this.companyOneDao = companyOneDao;
	}

	public IPersonOneDao getPersonOneDao() {
		return personOneDao;
	}

	public void setPersonOneDao(IPersonOneDao personOneDao) {
		this.personOneDao = personOneDao;
	}

	public IPersonTwoDao getPersonTwoDao() {
		return personTwoDao;
	}

	public void setPersonTwoDao(IPersonTwoDao personTwoDao) {
		this.personTwoDao = personTwoDao;
	}

}
