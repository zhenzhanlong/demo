package imitate.mybatis.code.generation.executor;

import imitate.mybatis.code.generation.session.Configuration;

/**
 * bizImpl,serviceImpl,daoImpl 接口 文件生成器
 * 
 * @author Administrator
 *
 */
public class ImplExecutor extends BaseExecutor {

	protected ImplExecutor(Configuration configuration) {
		super(configuration);
	}

	protected boolean createFile(String tableName) {
		return false;
	}
}
