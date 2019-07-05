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
package imitate.mybatis.code.generation.reflection.property;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分词器
 * 
 * @author Clinton Begin
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String name;
	private final String indexedName;
	private String index;
	private final String children;

	public PropertyTokenizer(String fullname) {
		// 对参数进行第一次处理，通过“.”分隔符将propertyName分作两部分
		//logger.debug("分词器 PropertyTokenizer fullname={}", fullname);
		int delim = fullname.indexOf('.');
		if (delim > -1) {
			name = fullname.substring(0, delim);
			children = fullname.substring(delim + 1);
		} else {
			name = fullname;
			children = null;
		}
		indexedName = name;
		// 对name进行二次处理,去除“[...]”，并将方括号内的内容赋给index属性，如果name属性中包含“[]”的话
		delim = name.indexOf('[');
		if (delim > -1) {
			// 先取index内容再截取name更为方便些，要不然还需要一个临时变量，需要三步才能实现
			// 这里包含了一个前提：传入的参数如果有[,则必然存在],并且是属性的最后一个字符
			index = name.substring(delim + 1, name.length() - 1);
			name = name.substring(0, delim);
		}
	}

	public String getName() {
		return name;
	}

	public String getIndex() {
		return index;
	}

	public String getIndexedName() {
		return indexedName;
	}

	public String getChildren() {
		return children;
	}

	@Override
	public boolean hasNext() {
		return children != null;
	}

	@Override
	public PropertyTokenizer next() {
		return new PropertyTokenizer(children);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"Remove is not supported, as it has no meaning in the context of properties.");
	}
}
