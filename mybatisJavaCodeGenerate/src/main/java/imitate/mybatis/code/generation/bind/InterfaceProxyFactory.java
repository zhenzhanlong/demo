package imitate.mybatis.code.generation.bind;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 接口代理创建工厂
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class InterfaceProxyFactory<T> {
	private final String interfaceName;
	private final Map<String, InterfaceMethod> methodCache = new ConcurrentHashMap<>();

	public InterfaceProxyFactory(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public Map<String, InterfaceMethod> getMethodCache() {
		return methodCache;
	}

	@SuppressWarnings("unchecked")
	protected T newInstance(InterfaceProxy<T> interfaceProxy) {
		// return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new
		// Class[] { interfaceName },
		// interfaceProxy);
		return null;
	}

	public T newInstance() {
		// final InterfaceProxy<T> mapperProxy = new InterfaceProxy<T>(interfaceName,
		// methodCache);
		// return newInstance(mapperProxy);
		return null;
	}
}
