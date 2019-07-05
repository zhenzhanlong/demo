/**
 *    Copyright 2009-2017 the original author or authors.
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
package imitate.mybatis.code.generation.parsing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import imitate.mybatis.code.generation.bind.MethodType;
import imitate.mybatis.code.generation.field.FieldMethod;
import imitate.mybatis.code.generation.field.FieldOrderBy;
import imitate.mybatis.code.generation.field.JavaField;

/**
 * @author Clinton Begin
 */
public class XNode {
	// 节点
	private final Node node;
	// 节点名字
	private final String name;
	// 节点xml内容
	private final String body;
	// xml节点所有属性
	private final Properties attributes;
	// 外部带进来的性质
	private final Properties variables;
	// 路径解析器
	private final XPathParser xpathParser;

	public XNode(XPathParser xpathParser, Node node, Properties variables) {
		this.xpathParser = xpathParser;
		this.node = node;
		this.name = node.getNodeName();
		this.variables = variables;
		// 获取当前节点的所有属性
		this.attributes = parseAttributes(node);
		// 获取当前节点的文本节点内容，当然获取到的数据是已经经过TokenHandler处理过的
		this.body = parseBody(node);
	}

	public XNode newXNode(Node node) {
		return new XNode(xpathParser, node, variables);
	}

	public XNode getParent() {
		Node parent = node.getParentNode();
		if (parent == null || !(parent instanceof Element)) {
			return null;
		} else {
			return new XNode(xpathParser, parent, variables);
		}
	}

	public String getPath() {
		StringBuilder builder = new StringBuilder();
		Node current = node;
		while (current != null && current instanceof Element) {
			if (current != node) {
				builder.insert(0, "/");
			}
			builder.insert(0, current.getNodeName());
			current = current.getParentNode();
		}
		return builder.toString();
	}

	// 获取值 的基本标识符 转换为[id.value.property]
	public String getValueBasedIdentifier() {
		StringBuilder builder = new StringBuilder();
		XNode current = this;
		while (current != null) {
			if (current != this) {
				builder.insert(0, "_");
			}
			String value = current.getStringAttribute("id",
					current.getStringAttribute("value", current.getStringAttribute("property", null)));
			if (value != null) {
				value = value.replace('.', '_');
				builder.insert(0, "]");
				builder.insert(0, value);
				builder.insert(0, "[");
			}
			builder.insert(0, current.getName());
			current = current.getParent();
		}
		return builder.toString();
	}

	public String evalString(String expression) {
		return xpathParser.evalString(node, expression);
	}

	public Boolean evalBoolean(String expression) {
		return xpathParser.evalBoolean(node, expression);
	}

	public Double evalDouble(String expression) {
		return xpathParser.evalDouble(node, expression);
	}

	public List<XNode> evalNodes(String expression) {
		return xpathParser.evalNodes(node, expression);
	}

	public XNode evalNode(String expression) {
		return xpathParser.evalNode(node, expression);
	}

	public Node getNode() {
		return node;
	}

	public String getName() {
		return name;
	}

	public String getStringBody() {
		return getStringBody(null);
	}

	public String getStringBody(String def) {
		if (body == null) {
			return def;
		} else {
			return body;
		}
	}

	public Boolean getBooleanBody() {
		return getBooleanBody(null);
	}

	public Boolean getBooleanBody(Boolean def) {
		if (body == null) {
			return def;
		} else {
			return Boolean.valueOf(body);
		}
	}

	public Integer getIntBody() {
		return getIntBody(null);
	}

	public Integer getIntBody(Integer def) {
		if (body == null) {
			return def;
		} else {
			return Integer.parseInt(body);
		}
	}

	public Long getLongBody() {
		return getLongBody(null);
	}

	public Long getLongBody(Long def) {
		if (body == null) {
			return def;
		} else {
			return Long.parseLong(body);
		}
	}

	public Double getDoubleBody() {
		return getDoubleBody(null);
	}

	public Double getDoubleBody(Double def) {
		if (body == null) {
			return def;
		} else {
			return Double.parseDouble(body);
		}
	}

	public Float getFloatBody() {
		return getFloatBody(null);
	}

	public Float getFloatBody(Float def) {
		if (body == null) {
			return def;
		} else {
			return Float.parseFloat(body);
		}
	}

	public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name) {
		return getEnumAttribute(enumType, name, null);
	}

	public <T extends Enum<T>> T getEnumAttribute(Class<T> enumType, String name, T def) {
		String value = getStringAttribute(name);
		if (value == null) {
			return def;
		} else {
			return Enum.valueOf(enumType, value);
		}
	}

	public String getStringAttribute(String name) {
		return getStringAttribute(name, null);
	}

	public String getStringAttribute(String name, String def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return value;
		}
	}

	public Boolean getBooleanAttribute(String name) {
		return getBooleanAttribute(name, null);
	}

	public Boolean getBooleanAttribute(String name, Boolean def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Boolean.valueOf(value);
		}
	}

	public Integer getIntAttribute(String name) {
		return getIntAttribute(name, null);
	}

	public Integer getIntAttribute(String name, Integer def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Integer.parseInt(value);
		}
	}

	public Long getLongAttribute(String name) {
		return getLongAttribute(name, null);
	}

	public Long getLongAttribute(String name, Long def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Long.parseLong(value);
		}
	}

	public Double getDoubleAttribute(String name) {
		return getDoubleAttribute(name, null);
	}

	public Double getDoubleAttribute(String name, Double def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Double.parseDouble(value);
		}
	}

	public Float getFloatAttribute(String name) {
		return getFloatAttribute(name, null);
	}

	public Float getFloatAttribute(String name, Float def) {
		String value = attributes.getProperty(name);
		if (value == null) {
			return def;
		} else {
			return Float.parseFloat(value);
		}
	}

	public List<XNode> getChildren() {
		List<XNode> children = new ArrayList<XNode>();
		// 获取所有子节点
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0, n = nodeList.getLength(); i < n; i++) {
				Node node = nodeList.item(i);
				// 如果子节点类型是元素节点，就添加到list中
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					children.add(new XNode(xpathParser, node, variables));
				}
			}
		}
		return children;
	}

	public Properties getChildrenAsProperties() {
		Properties properties = new Properties();
		for (XNode child : getChildren()) {
			String name = child.getStringAttribute("name");
			String value = child.getStringAttribute("value");
			// 只有当节点同时具有name和value属性才会添加到properties中
			if (name != null && value != null) {
				properties.setProperty(name, value);
			}
		}
		return properties;
	}

	/**
	 * 获取 有自己查询方法的字段
	 */
	public List<FieldMethod> getQueryField() {
		List<FieldMethod> fieldList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("querys/query/fieldList/value");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldList;
		}
		fieldList.addAll((getFieldMethodList(nodesList, MethodType.SELECT, null)));
		return fieldList;
	}

	/** 获取需要生成代码的表名 **/
	public List<String> getCreateTableNameList() {
		List<String> tableNameList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("table");
		if (null == nodesList || nodesList.isEmpty()) {
			return tableNameList;
		}
		nodesList.stream().forEach(nodes -> {
			tableNameList.add(nodes.getStringBody());
		});
		return tableNameList;

	}

	/**
	 * 解密不能update方法的字段
	 */
	public List<FieldMethod> getUnUpdateField() {
		List<FieldMethod> fieldList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("updates/cannot");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldList;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("fieldList/value");
			fieldList.addAll((getFieldMethodList(nodesTempList, MethodType.UPDATE, null)));
		}
		return fieldList;
	}

	/** XNode 转 FieldMethod **/
	private List<FieldMethod> getFieldMethodList(List<XNode> nodes, MethodType methodType, String condition) {
		List<FieldMethod> fieldList = new ArrayList<>();
		if (null == nodes || nodes.isEmpty()) {
			return fieldList;
		}
		nodes.stream().forEach(node -> {
			fieldList.add(new FieldMethod(node, methodType, condition));
		});
		return fieldList;
	}

	/** XNode 转 FieldMethod **/
	private List<FieldOrderBy> getFieldOrderByList(List<XNode> nodes, String condition) {
		List<FieldOrderBy> fieldList = new ArrayList<>();
		if (null == nodes || nodes.isEmpty()) {
			return fieldList;
		}
		nodes.stream().forEach(node -> {
			fieldList.add(new FieldOrderBy(node, condition));
		});
		return fieldList;
	}

	/**
	 * 解密需要有自己update的字段
	 */
	public List<FieldMethod> getNeewUpdateMethodField() {
		List<FieldMethod> fieldList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("updates/update");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldList;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("fieldList/value");
			fieldList.addAll(
					(getFieldMethodList(nodesTempList, MethodType.UPDATE, child.getStringAttribute("condition"))));
		}
		return fieldList;
	}

	/**
	 * 解密需要有自己delete的字段
	 */
	public List<FieldMethod> getNeewDeleteMethodField() {
		List<FieldMethod> fieldList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("deletes/delete/fieldList/value");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldList;
		}
		fieldList.addAll((getFieldMethodList(nodesList, MethodType.DELETE, null)));
		return fieldList;
	}

	/**
	 * 获取可以排序的字段
	 */
	public List<FieldOrderBy> getCanOrderByField() {
		List<FieldOrderBy> fieldList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("orderBys/orderBy");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldList;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("fieldList/value");
			fieldList.addAll(getFieldOrderByList(nodesTempList, child.getStringAttribute("condition")));
		}
		return fieldList;
	}

	/**
	 * 获取使用枚举类型的字段
	 */
	public List<JavaField> getEnumFieldList() {
		List<JavaField> fieldList = new ArrayList<>();
		List<XNode> nodesList = this.evalNodes("enums/enum");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldList;
		}
		for (XNode child : nodesList) {
			List<XNode> nodesTempList = child.evalNodes("fieldList/value");
			fieldList.addAll(getJavaEnumFieldList(nodesTempList, child.getStringAttribute("condition")));
		}
		return fieldList;
	}
	/**
	 * 获取po、vo、form、父类中已经存在的字段，子类不用再生成 get、set方法，不用再声明属性
	 */
	public Set<String> getBaseClassExitsField() {
		Set<String> fieldSet = new HashSet<>();
		List<XNode> nodesList = this.evalNodes("bases/base");
		if (null == nodesList || nodesList.isEmpty()) {
			return fieldSet;
		}
		//for (XNode child : nodesList) {
		nodesList.stream().forEach(nodesOne->{
			List<XNode> nodesTempList = nodesOne.evalNodes("fieldList/value");
			nodesTempList.stream().forEach(nodeTwo -> {
				fieldSet.add(trim(nodeTwo.getStringBody()));
			});
		});
		return fieldSet;
	}
	public String trim(String stringBody) {
		if(StringUtils.isNotBlank(stringBody)) {
			return stringBody.trim();
		}else {
			return stringBody;
		}
	}
	/** XNode 转 JavaField **/
	private List<JavaField> getJavaEnumFieldList(List<XNode> nodes, String condition) {
		List<JavaField> fieldList = new ArrayList<>();
		if (null == nodes || nodes.isEmpty()) {
			return fieldList;
		}
		// 枚举类型
		String dataType = condition.substring(condition.lastIndexOf(".")+1);;
		// 枚举字段名称
		// String fieldName = NameUtil.firstCharacterToLower(dataType);
		nodes.stream().forEach(node -> {
			JavaField field = new JavaField();

			field.setClassPath(condition);
			field.setColumnDataType(dataType);
			field.setColumnName(node.getStringBody());

			fieldList.add(field);
		});
		return fieldList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(name);
		for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
			builder.append(" ");
			builder.append(entry.getKey());
			builder.append("=\"");
			builder.append(entry.getValue());
			builder.append("\"");
		}
		List<XNode> children = getChildren();
		if (!children.isEmpty()) {
			builder.append(">\n");
			for (XNode node : children) {
				builder.append(node.toString());
			}
			builder.append("</");
			builder.append(name);
			builder.append(">");
		} else if (body != null) {
			builder.append(">");
			builder.append(body);
			builder.append("</");
			builder.append(name);
			builder.append(">");
		} else {
			builder.append("/>");
		}
		builder.append("\n");
		return builder.toString();
	}

	// 解析属性 node
	private Properties parseAttributes(Node n) {
		Properties attributes = new Properties();
		NamedNodeMap attributeNodes = n.getAttributes();
		if (attributeNodes != null) {
			for (int i = 0; i < attributeNodes.getLength(); i++) {
				Node attribute = attributeNodes.item(i);
				String value = PropertyParser.parse(attribute.getNodeValue(), variables);
				attributes.put(attribute.getNodeName(), value);
			}
		}
		return attributes;
	}

	// 解析整个 xml 配置文件
	private String parseBody(Node node) {
		String data = getBodyData(node);
		if (data == null) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				data = getBodyData(child);
				if (data != null) {
					break;
				}
			}
		}
		return data;
	}

	// 获取 xml 文件的 文件体
	private String getBodyData(Node child) {
		if (child.getNodeType() == Node.CDATA_SECTION_NODE || child.getNodeType() == Node.TEXT_NODE) {
			String data = ((CharacterData) child).getData();
			data = PropertyParser.parse(data, variables);
			return data;
		}
		return null;
	}

}