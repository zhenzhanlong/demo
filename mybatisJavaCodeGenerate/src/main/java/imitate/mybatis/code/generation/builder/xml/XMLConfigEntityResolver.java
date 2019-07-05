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
package imitate.mybatis.code.generation.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Offline entity resolver for the MyBatis DTDs XML实体节点解析器 ，主要加载 mybatis
 * xml配置文件的 DTD定义
 * 
 * @author Clinton Begin
 * @author Eduardo Macarron
 */
public class XMLConfigEntityResolver implements EntityResolver {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-3-code-generation.dtd";

	//private static final String MYBATIS_CONFIG_DTD = "imitate/mybatis/code/generation/builder/xml/mybatis-3-code-generation.dtd";
	private static final String MYBATIS_CONFIG_DTD = "mybatis-3-code-generation.dtd";
	//private static final String MYBATIS_CONFIG_DTD = "org/apache/ibatis/builder/xml/mybatis-3-mapper.dtd";

	/*
	 * Converts a public DTD into a local one
	 * 
	 * @param publicId The public id that is what comes after "PUBLIC"
	 * 
	 * @param systemId The system id that is what comes after the public id.
	 * 
	 * @return The InputSource for the DTD
	 * 
	 * @throws org.xml.sax.SAXException If anything goes wrong
	 */
	// 加载 mybatis 的 dtd 文件
	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
		try {
			logger.debug("加载 mybatis 的 dtd  publicId={},systemId={}", publicId, systemId);
			if (systemId != null) {
				String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
				if (lowerCaseSystemId.contains(MYBATIS_CONFIG_SYSTEM)) {
					return getInputSource(MYBATIS_CONFIG_DTD, publicId, systemId);
				}
			}
			return null;
		} catch (Exception e) {
			throw new SAXException(e.toString());
		}
	}

	// 根据路径加载xml
	private InputSource getInputSource(String path, String publicId, String systemId) {
		logger.debug("根据路径加载xml  path={},publicId={},systemId={}", path, publicId, systemId);
		InputSource source = null;
		if (path != null) {
			try {
				InputStream in = Resources.getResourceAsStream(path);
				source = new InputSource(in);
				source.setPublicId(publicId);
				source.setSystemId(systemId);
			} catch (IOException e) {
				// ignore, null is ok
			}
		}
		return source;
	}

}