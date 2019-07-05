package imitate.mybatis.code.generation.bind;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;

import imitate.mybatis.code.generation.session.Configuration;

/**
 * 接口注册器
 * 
 * @author Administrator
 *
 */
public class InterfaceRegistry {
	// 配置类
	private final Configuration config;
	// 接口 缓存
	private final Map<String, InterfaceProxyFactory<?>> knownInterface = new HashMap<>();

	public InterfaceRegistry(Configuration config) {
		this.config = config;
	}

	// 根据class 获取 MapperProxyFactory，没有报错，有，new 一个mapperProxy
	@SuppressWarnings("unchecked")
	public <T> T getMethod(String interfaceName) {
		final InterfaceProxyFactory<T> interfaceProxy = (InterfaceProxyFactory<T>) knownInterface.get(interfaceName);
		if (interfaceProxy == null) {
			throw new BindingException("Type " + interfaceName + " is not known to the MethodRegistry.");
		}
		try {
			return interfaceProxy.newInstance();
		} catch (Exception e) {
			throw new BindingException("Error getting Method instance. Cause: " + e, e);
		}
	}

	public <T> boolean hasMethod(String interfaceName) {
		return knownInterface.containsKey(interfaceName);
	}

	public <T> void addMethod(String interfaceName) {
		// 对于mybatis Method接口文件，必须是interface，不能是class
		// 判重，确保只会加载一次不会被覆盖
		if (hasMethod(interfaceName)) {
			throw new BindingException("Type " + interfaceName + " is already known to the MethodRegistry.");
		}
		boolean loadCompleted = false;
		try {
			// 为Method接口创建一个MethodProxyFactory代理
			knownInterface.put(interfaceName, new InterfaceProxyFactory<T>(interfaceName));
			// It's important that the type is added before the parser is run
			// otherwise the binding may automatically be attempted by the
			// Method parser. If the type is already known, it won't try.
			// 根据config 配置，生成 注解创建者
			loadCompleted = true;
		} finally {
			// 剔除解析出现异常的接口
			if (!loadCompleted) {
				knownInterface.remove(interfaceName);
			}
		}
	}

	/**
	 * 获取所有的接口key
	 * 
	 * @return
	 */
	public Collection<String> getInterfaces() {
		return Collections.unmodifiableCollection(knownInterface.keySet());
	}

	/**
	 * 配置的是路径的情况下，扫描包，将所有Method 加载进来
	 * 
	 * @since 3.2.2
	 */
	// public void addMethods(String packageName, Class<?> superType) {
	// //
	// mybatis框架提供的搜索classpath下指定package以及子package中符合条件(注解或者继承于某个类/接口)的类，默认使用Thread.currentThread().getContextClassLoader()返回的加载器,和spring的工具类殊途同归。
	// ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
	// // 无条件的加载所有的类,因为调用方传递了Object.class作为父类,这也给以后的指定Method接口预留了余地
	// resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
	// // 所有匹配的calss都被存储在ResolverUtil.matches字段中
	// Set<Class<? extends Class<?>>> MethodSet = resolverUtil.getClasses();
	// for (Class<?> MethodClass : MethodSet) {
	// // 调用addMethod方法进行具体的Method类/接口解析
	// addMethod(MethodClass);
	// }
	// }

	// /**
	// * @since 3.2.2
	// */
	// public void addMethods(String packageName) {
	// addMethods(packageName, Object.class);
	// }
}
