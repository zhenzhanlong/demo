package imitate.mybatis.code.generation.session.defaults;

import java.sql.Connection;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;

import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.session.CreateSession;
import imitate.mybatis.code.generation.session.CreateSessionFactory;

/**
 * 默认创建session 工厂实现类
 * 
 * @author zhenzhanlong
 *
 */
public class DefaultCreateSessionFactory implements CreateSessionFactory {

	private final Configuration configuration;

	public DefaultCreateSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	public CreateSession openSession() {
		return openSessionFromDataSource(configuration);
	}

	public CreateSession openSession(Connection connection) {
		return openSessionFromDataSource(configuration);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	private CreateSession openSessionFromDataSource(Configuration configuration) {
		try {
			return new DefaultCreateSession(configuration);
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
}
