package imitate.mybatis.code.generation.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import imitate.mybatis.code.generation.field.PropertiesVO;

/**
 * xml解析成数据帮助类
 * 
 * @author Administrator
 *
 */
public class XNodeUtil {
	/**
	 * 解析result的定义
	 */
	public static List<PropertiesVO> resultVOs(XNode parentNode) {
		List<XNode> nodesList = parentNode.evalNodes("resultVO");
		if (null == nodesList || nodesList.isEmpty()) {
			return new ArrayList<>(0);
		}
		List<PropertiesVO> proList = new ArrayList<>();
		nodesList.stream().forEach(node->{
			proList.add(new PropertiesVO(node.getStringAttribute("name"),
					node.getStringAttribute("value"),node.getStringAttribute("path")));
		});
		return proList;
	}

	/**
	 * 解析dao层method的方法名定义
	 */
	public static Properties daoMethod(XNode parentNode) {
		Properties daoPro = new Properties();
		List<XNode> nodesList = parentNode.evalNodes("dao");
		if (null == nodesList || nodesList.isEmpty()) {
			return daoPro;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("method");
			daoPro.putAll(methodProperties(nodesTempList));
		}
		return daoPro;
	}

	/**
	 * 解析dao层method的方法名定义
	 */
	public static Properties serviceMethod(XNode parentNode) {
		Properties daoPro = new Properties();
		List<XNode> nodesList = parentNode.evalNodes("service");
		if (null == nodesList || nodesList.isEmpty()) {
			return daoPro;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("method");
			daoPro.putAll(methodProperties(nodesTempList));
		}
		return daoPro;
	}

	/**
	 * 解析dao层method的方法名定义
	 */
	public static Properties bizMethod(XNode parentNode) {
		Properties daoPro = new Properties();
		List<XNode> nodesList = parentNode.evalNodes("biz");
		if (null == nodesList || nodesList.isEmpty()) {
			return daoPro;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("method");
			daoPro.putAll(methodProperties(nodesTempList));
		}
		return daoPro;
	}

	/**
	 * 解析dao层method的方法名定义
	 */
	public static Properties webServiceMethod(XNode parentNode) {
		Properties daoPro = new Properties();
		List<XNode> nodesList = parentNode.evalNodes("webService");
		if (null == nodesList || nodesList.isEmpty()) {
			return daoPro;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("method");
			daoPro.putAll(methodProperties(nodesTempList));
		}
		return daoPro;
	}

	public static Properties methodProperties(List<XNode> nodesTempList) {
		Properties pro = new Properties();
		nodesTempList.stream().forEach(node -> {
			if (null != node.getStringAttribute("name")) {
				pro.put(node.getStringAttribute("name"), node.getStringAttribute("value"));
			}
		});
		return pro;
	}
}
