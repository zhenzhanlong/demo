package imitate.mybatis.code.generation.executor;

import imitate.mybatis.code.generation.session.Configuration;

/**
 * biz,service,dao 接口 文件生成器
 * @author Administrator
 *
 */
public class ApiExecutor extends BaseExecutor {

	protected ApiExecutor(Configuration configuration) {
		super(configuration);
	}
	protected  boolean createFile(String tableName) {
		return false;
	}
}
