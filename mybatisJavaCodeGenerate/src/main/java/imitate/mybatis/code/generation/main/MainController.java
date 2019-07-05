//package imitate.mybatis.code.generation.main;
//
//import java.io.IOException;
//
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.spring.format.code.generation.biz.ITableBiz;
//
///**
// * 程序入口
// * @author lenovo
// *
// */
//public class MainController {
//	@SuppressWarnings("resource")
//	public static void main(String[] args) throws IOException{
//		
//		ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext(new String[]{"classpath:code.xml"});
//									   ac.start();
//		ITableBiz tableBizImpl = (ITableBiz) ac.getBean("tableBizImpl");
//				   tableBizImpl.createFiles();
////		final Reader reader = Resources.getResourceAsReader("imitate/mybatis/mybatis-3-code-generation.xml");
////		CreateSessionFactory sqlSessionFactory = new CreateSessionFactoryBuilder().build(reader);
////		reader.close();
////
////		//final SqlSession session = sqlSessionFactory.openSession();
////		final CreateSession session = sqlSessionFactory.openSession();
//		//session.createAction();
//	}
//}
//	
//	
//	
