package imitate.mybatis.code.generation.session;

import java.sql.Connection;

/**
 * 
 * @author zhenzhanlong
 *
 */
public interface CreateSessionFactory {
	CreateSession openSession();

	CreateSession openSession(Connection connection);

	Configuration getConfiguration();

}
