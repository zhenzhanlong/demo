package imitate.mybatis.code.generation.creating;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.session.Configuration;

/**
 * po类文件生成类
 * 
 * @author Administrator
 *
 */
public class BaseCreatingFile {

	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/** 路径分隔符 **/
	public static final String PATH_SEPARATOR = "/";

	// 配置类
	protected Configuration configuration;

	// 属性
	protected final Properties pro = new Properties();

	// 反射执行，无参数
	protected static final Object[] NO_ARGUMENTS = new Object[0];

	protected final StringBuilder content = new StringBuilder();

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public static String getPathSeparator() {
		return PATH_SEPARATOR;
	}

	public Properties getPro() {
		return pro;
	}

	public static Object[] getNoArguments() {
		return NO_ARGUMENTS;
	}

	public StringBuilder getContent() {
		return content;
	}

	public BaseCreatingFile(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 生成文件
	 * 
	 * @param path
	 * @param fileName
	 * @param content
	 * @return
	 */
	private void createFile(String fileName, String content) {
		// 生成文件
		File file = new File(fileName);
		boolean deleteFile = false;
		if (file.exists()) {
			deleteFile = file.delete();
		}
		if (!deleteFile) {
			log.info("删除文件失败,此文件不存在直接生成fileName={}", fileName);
		}
		try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)))) {
			// 生成文件，成功就继续添加内容
			// if (file.createNewFile()) {
			out.write(content);
			// }
		} catch (IOException e) {
			log.error("生成文件失败fileName={},io异常,error={}", fileName, e);
		}
	}
	
	/**
	 * 生成文件
	 * 
	 * @param path
	 * @param fileName
	 * @param content
	 * @return
	 */
	public void createFile(String packagePath, String entityName, String content) {
		String path = configuration.getRelativePath();
		if (!path.endsWith(PATH_SEPARATOR)) {
			path = path + PATH_SEPARATOR;
		}
		path = path + packagePath;
		path = path.replace(".", PATH_SEPARATOR);
		// 生成目录
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String fileName = path + PATH_SEPARATOR + entityName;
		// 生成文件
		this.createFile(fileName, content);
	}
}
