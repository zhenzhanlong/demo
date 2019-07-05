package imitate.mybatis.code.generation.field;

import imitate.mybatis.code.generation.parsing.XNode;

/**
 * 字段方法（需要自己query、update、delete方法的字段）
 * 
 * @author Administrator
 *
 */
public class FieldOrderBy {
	private XNode node;

	// 需要的条件 如 condition="ById"
	private String condition;

	private String fieldName;
	
	public FieldOrderBy(XNode node,String condition) {
		super();
		this.node = node;
		this.condition = condition;
		this.fieldName=node.getStringBody();
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public XNode getNode() {
		return node;
	}

	public void setNode(XNode node) {
		this.node = node;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
