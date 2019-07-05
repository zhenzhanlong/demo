/**
 *    Copyright 2009-2016 the original author or authors.
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

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性分析器
 * 
 * @author Clinton Begin
 * @author Kazuki Shimizu
 */
public class PropertyParser {
	//private static Logger logger = LoggerFactory.getLogger(PropertyParser.class);
	// key 前缀
	private static final String KEY_PREFIX = "org.apache.ibatis.parsing.PropertyParser.";
	/**
	 * The special property key that indicate whether enable a default value on
	 * placeholder.
	 * <p>
	 * The default value is {@code false} (indicate disable a default value on
	 * placeholder) If you specify the {@code true}, you can specify key and default
	 * value on placeholder (e.g. {@code ${db.username:postgres}}).
	 * </p>
	 * 启用key默认值
	 * 
	 * @since 3.4.2
	 */
	public static final String KEY_ENABLE_DEFAULT_VALUE = KEY_PREFIX + "enable-default-value";

	/**
	 * The special property key that specify a separator for key and default value
	 * on placeholder.
	 * <p>
	 * The default separator is {@code ":"}.
	 * </p>
	 * 默认分词标识
	 * 
	 * @since 3.4.2
	 */
	public static final String KEY_DEFAULT_VALUE_SEPARATOR = KEY_PREFIX + "default-value-separator";
	// 默认值 不可用
	private static final String ENABLE_DEFAULT_VALUE = "false";
	// 默认分词标识
	private static final String DEFAULT_VALUE_SEPARATOR = ":";

	private PropertyParser() {
		// Prevent Instantiation
	}

	// 解析字符串
	public static String parse(String string, Properties variables) {
		// 自定义处理器
		VariableTokenHandler handler = new VariableTokenHandler(variables);
		// 在初始化GenericTokenParser对象，设置openToken为${,endToken为}
		// 有没有对${}比较熟悉，这个符号就是mybatis配置文件中的占位符，例如定义datasource时用到的 <property
		// name="driverClassName" value="${driver}" />
		// 同时也可以解释在VariableTokenHandler中的handleToken时，如果content在properties中不存在时，返回的内容要加上${}了。
		GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
		// 分析字符串
		return parser.parse(string);
	}

	// 自定义处理
	private static class VariableTokenHandler implements TokenHandler {
		//private static Logger logger1 = LoggerFactory.getLogger(VariableTokenHandler.class);
		// 属性集
		private final Properties variables;
		// 默认值可用
		private final boolean enableDefaultValue;
		// 默认分隔符
		private final String defaultValueSeparator;

		private VariableTokenHandler(Properties variables) {
			this.variables = variables;
			// 默认是查看配置文件是否有配置，有就用，没有用自定义的
			this.enableDefaultValue = Boolean
					.parseBoolean(getPropertyValue(KEY_ENABLE_DEFAULT_VALUE, ENABLE_DEFAULT_VALUE));
			this.defaultValueSeparator = getPropertyValue(KEY_DEFAULT_VALUE_SEPARATOR, DEFAULT_VALUE_SEPARATOR);
		}

		private String getPropertyValue(String key, String defaultValue) {
			//logger1.debug(" 属性解析 VariableTokenHandler key={},defaultValue={}", key, defaultValue);
			return (variables == null) ? defaultValue : variables.getProperty(key, defaultValue);
		}

		// 处理字符串
		@Override
		public String handleToken(String content) {
			//logger1.debug(" 处理字符串 handleToken content={}", content);
			// 属性不能为空
			if (variables != null) {
				String key = content;
				// 默认值可用
				if (enableDefaultValue) {
					final int separatorIndex = content.indexOf(defaultValueSeparator);
					String defaultValue = null;
					// 有分词标识，分词
					if (separatorIndex >= 0) {
						key = content.substring(0, separatorIndex);
						defaultValue = content.substring(separatorIndex + defaultValueSeparator.length());
					}
					// 默认值不为空，加入默认值
					if (defaultValue != null) {
						return variables.getProperty(key, defaultValue);
					}
				}
				// 返货进入 属性集里面的值
				if (variables.containsKey(key)) {
					return variables.getProperty(key);
				}
			}
			return "${" + content + "}";
		}
	}

}
