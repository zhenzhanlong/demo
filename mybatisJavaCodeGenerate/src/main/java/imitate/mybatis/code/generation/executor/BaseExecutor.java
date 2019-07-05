package imitate.mybatis.code.generation.executor;

import imitate.mybatis.code.generation.session.Configuration;

public abstract class BaseExecutor {
	protected Configuration configuration;
	protected Executor wrapper;

	protected BaseExecutor(Configuration configuration) {
		this.configuration = configuration;
	}

	protected abstract boolean createFile(String tableName);
}
