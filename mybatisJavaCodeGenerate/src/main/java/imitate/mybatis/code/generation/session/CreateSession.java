package imitate.mybatis.code.generation.session;

import java.util.List;

/**
 * 
 * @author zhenzhanlong
 *
 */
public interface CreateSession {
	/** **/
	public void createDao();
	/** **/
	public void createMapperXml();
	/** **/
	public void createServiceInterfase();
	/** **/
	public void createServiceImpl();
	/** **/
	public void createBizInterface();
	/** **/
	public void createBizImpl();
	/** **/
	public void createWebServiceInterface();
	/** **/
	public void createWebServiceImpl();
	/** **/
	public void createController();
	/** **/
	public void createAction();
}
