package imitate.mybatis.code.generation.session.defaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.session.Configuration;
import imitate.mybatis.code.generation.session.CreateSession;

/**
 * 默认创建 session
 * 
 * @author zhenzhanlong
 *
 */
public class DefaultCreateSession implements CreateSession {
	/*** 日志 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Configuration configuration;

	public DefaultCreateSession(Configuration config) {
		this.configuration = config;
	}

	/** **/
	@Override
	public void createDao() {

	}

	/** **/
	@Override
	public void createMapperXml() {

	}

	/** **/
	@Override
	public void createServiceInterfase() {

	}

	/** **/
	@Override
	public void createServiceImpl() {

	}

	/** **/
	@Override
	public void createBizInterface() {

	}

	/** **/
	@Override
	public void createBizImpl() {

	}

	/** **/
	@Override
	public void createWebServiceInterface() {

	}

	/** **/
	@Override
	public void createWebServiceImpl() {

	}

	/** **/
	@Override
	public void createController() {

	}

	/** **/
	@Override
	public void createAction() {

	}

}
