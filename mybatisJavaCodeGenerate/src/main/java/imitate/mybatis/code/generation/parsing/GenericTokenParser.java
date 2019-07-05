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

/**
 * 通用token解析器
 * 
 * @author Clinton Begin
 */
public class GenericTokenParser {
	// 开始标识
	private final String openToken;
	// 结束标识
	private final String closeToken;
	// token处理器
	private final TokenHandler handler;

	public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
		this.openToken = openToken;
		this.closeToken = closeToken;
		this.handler = handler;
	}

	// 解析字符串
	public String parse(String text) {
		if (text == null || text.isEmpty()) {
			return "";
		}
		// search open token 没有开始表示，返回
		// 判断openToken在text中的位置，注意indexOf函数的返回值-1表示不存在，0表示在在开头的位置
		int start = text.indexOf(openToken, 0);
		if (start == -1) {
			return text;
		}
		// 将字符串转为字符数组
		char[] src = text.toCharArray();
		int offset = 0;
		final StringBuilder builder = new StringBuilder();
		StringBuilder expression = null;
		while (start > -1) {
			if (start > 0 && src[start - 1] == '\\') {
				// this open token is escaped. remove the backslash and continue.
				// 如果text中在openToken前存在转义符就将转义符去掉。start的值必然大于0，最小也为1
				// 因为此时openToken是不需要进行处理的，所以也不需要处理endToken。接着查找下一个openToken
				builder.append(src, offset, start - offset - 1).append(openToken);
				offset = start + openToken.length();
			} else {
				// found open token. let's search close token.
				// 这里表示的是，开始表示在初始位置。expression 此时应该为空，所以new
				// 但是应该是有不为空情况，需要将长度置为0
				if (expression == null) {
					expression = new StringBuilder();
				} else {
					expression.setLength(0);
				}
				// 以前的版本不是这么写的，这么写应该是更合理？方便？
				// 则直接将offset位置后的字符添加到builder中
				// 将开始标识后的内容追加
				builder.append(src, offset, start - offset);
				//// 重设offset
				offset = start + openToken.length();
				// 判断结束标识符
				int end = text.indexOf(closeToken, offset);
				while (end > -1) {
					if (end > offset && src[end - 1] == '\\') {
						// this close token is escaped. remove the backslash and continue.
						// 结束标志前有数据，追加内容。最佳结束标识
						expression.append(src, offset, end - offset - 1).append(closeToken);
						offset = end + closeToken.length();
						end = text.indexOf(closeToken, offset);
					} else {
						expression.append(src, offset, end - offset);
						offset = end + closeToken.length();
						break;
					}
				}
				// 没有结束标识
				if (end == -1) {
					// close token was not found.
					// 继续追加
					builder.append(src, start, src.length - start);
					offset = src.length;
				} else {
					// 有结束标识，handler处理字符串
					builder.append(handler.handleToken(expression.toString()));
					// 重设offset
					offset = end + closeToken.length();
				}
			}
			start = text.indexOf(openToken, offset);
		}
		if (offset < src.length) {
			builder.append(src, offset, src.length - offset);
		}
		return builder.toString();
	}
}
