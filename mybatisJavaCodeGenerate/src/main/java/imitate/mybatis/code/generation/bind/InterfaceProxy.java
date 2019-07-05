package imitate.mybatis.code.generation.bind;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 接口代理
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class InterfaceProxy<T> implements InvocationHandler,  Serializable {
	private static final long serialVersionUID = -6424540398559729838L;
	// 接口的类型对象
	private final String mapperInterface;
	// 缓存mapper文件里面所有 method 对应的 MapperMethod
	private final Map<String, InterfaceMethod> methodCache;

	public InterfaceProxy(String mapperInterface, Map<String, InterfaceMethod> methodCache) {
		this.mapperInterface = mapperInterface;
		this.methodCache = methodCache;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return null;
	}

	// 反射执行 这个方法
	// @Override
	// public Object invoke(Object proxy, Method method, Object[] args) throws
	// Throwable {
	// try {
	// // 声明类是基类
	// if (Object.class.equals(method.getDeclaringClass())) {
	// return method.invoke(this, args);
	// } else if (isDefaultMethod(method)) {// 是否是默认执行的方法（方法覆盖）
	// return invokeDefaultMethod(proxy, method, args);
	// }
	// } catch (Throwable t) {
	// throw ExceptionUtil.unwrapThrowable(t);
	// }
	// // 没有执行,缓存中寻找，然后执行（缓存中不存在会生成）
	// final MapperMethod mapperMethod = cachedMapperMethod(method);
	// return mapperMethod.execute(sqlSession, args);
	// }

	// private MapperMethod cachedMapperMethod(Method method) {
	// // 缓存中寻找，不存在会生成
	// InterfaceMethod interfaceMethod = methodCache.get(method);
	// if (interfaceMethod == null) {
	// //interfaceMethod = new InterfaceMethod(mapperInterface, method);
	// methodCache.put(method, mapperMethod);
	// }
	// return mapperMethod;
	// }

//	// 执行默认的 方法
//	@UsesJava7
//	private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
//		// 获取构造方法，设置权限
//		final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
//				.getDeclaredConstructor(Class.class, int.class);
//		if (!constructor.isAccessible()) {
//			constructor.setAccessible(true);
//		}
//		final Class<?> declaringClass = method.getDeclaringClass();
//		// 生成构造方法，然后使用代理器，-》执行方法
//		return constructor
//				.newInstance(declaringClass,
//						MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE
//								| MethodHandles.Lookup.PUBLIC)
//				.unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
//	}

//	/**
//	 * 方法是否是默认的方法 Backport of java.lang.reflect.Method#isDefault()
//	 */
//	private boolean isDefaultMethod(Method method) {
//		return (method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC
//				&& method.getDeclaringClass().isInterface();
//	}
}
