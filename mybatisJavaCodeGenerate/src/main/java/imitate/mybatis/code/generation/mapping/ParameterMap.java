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
package imitate.mybatis.code.generation.mapping;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参数map
 * 
 * @author Clinton Begin
 */
public class ParameterMap {
	private String id;
	private Class<?> type;
	private List<ParameterMapping> parameterMappings;

	private ParameterMap() {
	}

	public static class Builder {
		private Logger logger1 = LoggerFactory.getLogger(this.getClass());
		private ParameterMap parameterMap = new ParameterMap();

		public Builder(Configuration configuration, String id, Class<?> type,
				List<ParameterMapping> parameterMappings) {
			parameterMap.id = id;
			parameterMap.type = type;
			parameterMap.parameterMappings = parameterMappings;
		}

		public Class<?> type() {
			return parameterMap.type;
		}

		// 生成不可修改的 集合
		public ParameterMap build() {
			// lock down collections
			parameterMap.parameterMappings = Collections.unmodifiableList(parameterMap.parameterMappings);
			return parameterMap;
		}
	}

	public String getId() {
		return id;
	}

	public Class<?> getType() {
		return type;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

}
