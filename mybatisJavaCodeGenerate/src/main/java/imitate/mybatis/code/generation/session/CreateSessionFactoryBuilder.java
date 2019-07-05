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
package imitate.mybatis.code.generation.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.builder.xml.XMLConfigBuilder;
import imitate.mybatis.code.generation.session.defaults.DefaultCreateSessionFactory;

/**
 * 
 * （step one） mybatis 的启动类 Builds {@link SqlSession} instances.
 *
 * @author Clinton Begin
 */
public class CreateSessionFactoryBuilder {

	public CreateSessionFactory build(Reader reader) {
		return build(reader, null);
	}

	public CreateSessionFactory build(Reader reader, Properties properties) {
		try {
			XMLConfigBuilder parser = new XMLConfigBuilder(reader, properties);
			return build(parser.parse());
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error building SqlSession.", e);
		} finally {
			ErrorContext.instance().reset();
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				// Intentionally ignore. Prefer previous error.
			}
		}
	}

	public CreateSessionFactory build(InputStream inputStream) {
		return build(inputStream, null);
	}

	public CreateSessionFactory build(InputStream inputStream, Properties properties) {
		try {
			XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, properties);
			return build(parser.parse());
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error building SqlSession.", e);
		} finally {
			ErrorContext.instance().reset();
			try {
				inputStream.close();
			} catch (IOException e) {
				// Intentionally ignore. Prefer previous error.
			}
		}
	}

	public CreateSessionFactory build(Configuration config) {
		return new DefaultCreateSessionFactory(config);
	}

}
