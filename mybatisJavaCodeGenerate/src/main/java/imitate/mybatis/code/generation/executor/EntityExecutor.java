package imitate.mybatis.code.generation.executor;

import imitate.mybatis.code.generation.session.Configuration;

/**
 * po,vo,form等实体类 文件生成器
 * @author Administrator
 *
 */
public class EntityExecutor extends BaseExecutor {

	protected EntityExecutor(Configuration configuration) {
		super(configuration);
	}
	protected  boolean createFile(String tableName) {
		return false;
	}
}
