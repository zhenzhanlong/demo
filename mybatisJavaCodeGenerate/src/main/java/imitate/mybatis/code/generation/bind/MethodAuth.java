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
package imitate.mybatis.code.generation.bind;

/**
 *  方法，访问限制等级
 * @author Administrator
 *
 */
public enum MethodAuth {
	PUBLIC(" public "), 
	PROTECTED(" protected "), 
	PRIVATE(" private "),
	DEFAULT(" default ");

	private String auth;

	private MethodAuth(String auth) {
		this.auth = auth;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
