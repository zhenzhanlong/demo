/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package imitate.mybatis.code.generation.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.ReflectPermission;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.reflection.property.PropertyNamer;

import imitate.mybatis.code.generation.reflection.invoker.GetFieldInvoker;
import imitate.mybatis.code.generation.reflection.invoker.Invoker;
import imitate.mybatis.code.generation.reflection.invoker.MethodInvoker;
import imitate.mybatis.code.generation.reflection.invoker.SetFieldInvoker;

/**
 * This class represents a cached set of class definition information that
 * allows for easy mapping between property names and getter/setter methods.
 * 此类表示一组缓存的类定义信息，允许在属性名和getter/setter方法之间轻松映射。
 * 
 * @author Clinton Begin
 */
public class Reflector {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Class<?> type;// 对应class
	// 可写属性的名称集合，可写属性就是存在相应setter 方法的属性，初始值为空数纽
	private final String[] readablePropertyNames;// 可读属性名称集合
	// 可写属性的名称集合，可写属性就是存在相应setter 方法的属性，初始值为空数纽
	private final String[] writeablePropertyNames;// 可写属性名称集合
	// 记录了属性相应的setter方法 key 是属性名，value 是Invoke 对象
	private final Map<String, Invoker> setMethods = new HashMap<String, Invoker>();// set
	// 属性相应 的 getter 方法集合 ， key 是属 性名称， value 也是 Invoker 对象
	private final Map<String, Invoker> getMethods = new HashMap<String, Invoker>();//
	// 记录了属性相应的 setter 方法 的参数值类型， key 是属性名称， value 是 setter 方法的参数类型
	private final Map<String, Class<?>> setTypes = new HashMap<String, Class<?>>();//
	// 记录 了属性相应的 getter 方法的返回位类型， key是属性名称， value 是 getter 方法的返回位类型
	private final Map<String, Class<?>> getTypes = new HashMap<String, Class<?>>();//
	// 默认构造方法
	private Constructor<?> defaultConstructor;//
	// 所有属性名称的集合（可读、可写书写的合集。如果新增属性忘记了get、set方法。进入不到这里）
	private Map<String, String> caseInsensitivePropertyMap = new HashMap<String, String>();

	public Reflector(Class<?> clazz) {
		//logger.debug(" 构造 Reflector clazz={}",clazz);
		type = clazz;
		// 添加默认构造函数，对应属性defaultConstructor
		addDefaultConstructor(clazz);
		// 处理clazz 中的getter 方法，填充getMethods 集合和getTypes 集合
		addGetMethods(clazz);
		// 处理clazz 中的set ter 方法，填充setMethods 集合和set Types 集合
		addSetMethods(clazz);
		// 处理没有get/set的方法字段
		addFields(clazz);
		// 初始化可读写的名称集合
		// 所有可读的属性
		readablePropertyNames = getMethods.keySet().toArray(new String[getMethods.keySet().size()]);
		// 所有可写属性
		writeablePropertyNames = setMethods.keySet().toArray(new String[setMethods.keySet().size()]);
		// 初始化caseInsensitivePropertyMap ，记录了所有大写格式的属性名称
		for (String propName : readablePropertyNames) {
			caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
		}
		for (String propName : writeablePropertyNames) {
			caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
		}
	}

	// 新增默认构造方法，默认构造只要 无参构造
	private void addDefaultConstructor(Class<?> clazz) {
		//logger.debug(" 新增默认构造方法，默认构造只要 无参构造 clazz={}",clazz);
		Constructor<?>[] consts = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : consts) {
			if (constructor.getParameterTypes().length == 0) {
				if (canAccessPrivateMethods()) {
					try {
						constructor.setAccessible(true);
					} catch (Exception e) {
						// Ignored. This is only a final precaution, nothing we can do.
					}
				}
				if (constructor.isAccessible()) {
					this.defaultConstructor = constructor;
				}
			}
		}
	}

	// 如果子类覆盖父类的gettter方法，并且返回值发生变化，就会生成2个签名。
	private void addGetMethods(Class<?> cls) {
		//logger.debug(" 如果子类覆盖父类的gettter方法，并且返回值发生变化，就会生成2个签名。 addGetMethods  clazz={}",cls);
		// 有冲突的 get方法嘉禾
		Map<String, List<Method>> conflictingGetters = new HashMap<String, List<Method>>();
		Method[] methods = getClassMethods(cls);// 返回这个类及其父类的所以methods
		for (Method method : methods) {
			if (method.getParameterTypes().length > 0) {// 过滤有参的method，get方法不需要参数
				continue;
			}
			String name = method.getName();
			if ((name.startsWith("get") && name.length() > 3) || (name.startsWith("is") && name.length() > 2)) {
				name = PropertyNamer.methodToProperty(name);// #方法的属性名
				// 所有的get方法都会加入冲突集合、后面处理
				addMethodConflict(conflictingGetters, name, method);// # 添加冲突的method
			}
		}
		// 处理冲突的集合
		resolveGetterConflicts(conflictingGetters);
	}

	// 对方法名称重复的情况进行处理，同时会将处理得到的 getter方法记录到 getMethods集合，并将其返回值类型填充到 getTypes集合 。
	// resolveGetterConflicts方法会对这种覆写的情况进行处理，同时将处理后的getter方法记录到getMethods集合中，将其返回值类型填充到
	// getTypes集合中。 内部实现主要是两个for循环，循环比较方法名称相同的情况下，返回值不同的情况下，拿第二个当最终想要的Method。
	private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
		//logger.debug(" resolveGetterConflicts  解决方法名字重复的问题 ");
		for (Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
			Method winner = null;
			String propName = entry.getKey();
			for (Method candidate : entry.getValue()) {
				if (winner == null) {
					winner = candidate;
					continue;
				}
				Class<?> winnerType = winner.getReturnType();
				Class<?> candidateType = candidate.getReturnType();
				// 名称重复的方法，返回类型也重复
				if (candidateType.equals(winnerType)) {
					if (!boolean.class.equals(candidateType)) {
						throw new ReflectionException(
								"Illegal overloaded getter method with ambiguous type for property " + propName
										+ " in class " + winner.getDeclaringClass()
										+ ". This breaks the JavaBeans specification and can cause unpredictable results.");
					} else if (candidate.getName().startsWith("is")) {
						winner = candidate;
					}
				} else if (candidateType.isAssignableFrom(winnerType)) {
					// OK getter type is descendant
				} else if (winnerType.isAssignableFrom(candidateType)) {
					winner = candidate;
				} else {
					throw new ReflectionException("Illegal overloaded getter method with ambiguous type for property "
							+ propName + " in class " + winner.getDeclaringClass()
							+ ". This breaks the JavaBeans specification and can cause unpredictable results.");
				}
			}
			addGetMethod(propName, winner);
		}
	}

	// 填充reflector属性 getMethods, getTypes
	private void addGetMethod(String name, Method method) {
		//logger.debug(" addGetMethod  name={},method={}",name,method);
		if (isValidPropertyName(name)) {
			getMethods.put(name, new MethodInvoker(method));
			Type returnType = TypeParameterResolver.resolveReturnType(method, type);
			getTypes.put(name, typeToClass(returnType));
		}
	}

	// 处理 set方法和
	private void addSetMethods(Class<?> cls) {
		//logger.debug(" addSetMethods  cls={}",cls);
		Map<String, List<Method>> conflictingSetters = new HashMap<String, List<Method>>();
		Method[] methods = getClassMethods(cls);
		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("set") && name.length() > 3) {
				if (method.getParameterTypes().length == 1) {
					// 方法名转换为属性名 如 getCode, 转换为code
					name = PropertyNamer.methodToProperty(name);
					// 所有方法都会加入重复集合中、后面会在处理，
					addMethodConflict(conflictingSetters, name, method);
				}
			}
		}
		// 处理set方法冲突
		resolveSetterConflicts(conflictingSetters);
	}

	// 新增method 冲突
	private void addMethodConflict(Map<String, List<Method>> conflictingMethods, String name, Method method) {
		//logger.debug("新增方法冲突处理 addMethodConflict name={},method={}",name,method);
		List<Method> list = conflictingMethods.get(name);
		if (list == null) {
			list = new ArrayList<Method>();
			conflictingMethods.put(name, list);
		}
		list.add(method);
	}

	// 处理签名重复的 set方法
	private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
		//logger.debug("处理签名重复的 set方法 resolveSetterConflicts ");
		// 签名重复的不止一个，
		for (String propName : conflictingSetters.keySet()) {
			// 获取签名重复的方法集合
			List<Method> setters = conflictingSetters.get(propName);
			// 获取方法名的 get参数？
			Class<?> getterType = getTypes.get(propName);
			Method match = null;
			ReflectionException exception = null;
			for (Method setter : setters) {
				Class<?> paramType = setter.getParameterTypes()[0];
				// 第一步
				// set 参数与get参数相同，就是最合适的方法，这个签名的set方法确定
				if (paramType.equals(getterType)) {
					// should be the best match
					match = setter;
					break;
				}
				if (exception == null) {
					try {
						// 第二步
						// 第一步没有找到最合适的，就说明返回类型都不是最合适的，比较两个方法哪个更合适
						match = pickBetterSetter(match, setter, propName);
					} catch (ReflectionException e) {
						// there could still be the 'best match'
						match = null;
						exception = e;
					}
				}
			}
			// 没有合适的set方法抛出异常
			if (match == null) {
				throw exception;
			} else {
				addSetMethod(propName, match);
			}
		}
	}

	/// 挑选最好、最合适的set方法
	private Method pickBetterSetter(Method setter1, Method setter2, String property) {
		//logger.debug("挑选最好、最合适的set方法 setter1={},setter2={},property={}",setter1,setter2,property);
		if (setter1 == null) {
			return setter2;
		}
		Class<?> paramType1 = setter1.getParameterTypes()[0];
		Class<?> paramType2 = setter2.getParameterTypes()[0];
		// paramType1 可转换成 paramType2 ，就是 paramType1是不是 paramType2的子类，是返回paramType2
		// 因为 1是2的子类，1能转换为2，但是2不一定能转换为1
		if (paramType1.isAssignableFrom(paramType2)) {
			return setter2;

			// paramType2 可转换成 paramType1
		} else if (paramType2.isAssignableFrom(paramType1)) {
			return setter1;
		}
		throw new ReflectionException(
				"Ambiguous setters defined for property '" + property + "' in class '" + setter2.getDeclaringClass()
						+ "' with types '" + paramType1.getName() + "' and '" + paramType2.getName() + "'.");
	}

	// 将set方法及参数类型加入集合
	private void addSetMethod(String name, Method method) {
		//logger.debug("将set方法及参数类型加入集合 name={},method={}",name,method);
		if (isValidPropertyName(name)) {
			setMethods.put(name, new MethodInvoker(method));
			Type[] paramTypes = TypeParameterResolver.resolveParamTypes(method, type);
			setTypes.put(name, typeToClass(paramTypes[0]));
		}
	}

	/// 类型转换为类，如int，可能是最终转换为Integer。这里有多种类型转换
	private Class<?> typeToClass(Type src) {
		//logger.debug("类型转换为类， src={}",src);
		Class<?> result = null;
		// 类型本身是类就直接反悔了
		if (src instanceof Class) {
			result = (Class<?>) src;
		} else if (src instanceof ParameterizedType) {// 参数化类型
			result = (Class<?>) ((ParameterizedType) src).getRawType();
		} else if (src instanceof GenericArrayType) {// 通用数组类型
			Type componentType = ((GenericArrayType) src).getGenericComponentType();
			// 数组是类具体类数组，转换
			if (componentType instanceof Class) {
				result = Array.newInstance((Class<?>) componentType, 0).getClass();
			} else {// 数组不是具体类型，递归。
				Class<?> componentClass = typeToClass(componentType);
				result = Array.newInstance(componentClass, 0).getClass();
			}
		}
		if (result == null) {
			result = Object.class;
		}
		return result;
	}

	//// 处理没有get/set的方法字段
	private void addFields(Class<?> clazz) {
		//logger.debug("addFields， clazz={}",clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 是否可以访问私有方法
			if (canAccessPrivateMethods()) {
				try {
					// 设置 字段权限
					field.setAccessible(true);
				} catch (Exception e) {
					// Ignored. This is only a final precaution, nothing we can do.
				}
			}
			if (field.isAccessible()) {
				// setMethods 中不包含
				if (!setMethods.containsKey(field.getName())) {
					// issue #379 - removed the check for final because JDK 1.5 allows
					// modification of final fields through reflection (JSR-133). (JGB)
					// pr #16 - final static can only be set by the classloader
					int modifiers = field.getModifiers();
					// 去除final 与 static 修饰的属性，因为这样的属性只能类加载器set值
					// final是这样定义的， 这是10位，0x 应该表示的是十六进制
					// 后八位，才是定义的数值 public static final int FINAL = 0x00000010;
					if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
						addSetField(field);
					}
				}
				// getMethods 中不包含
				if (!getMethods.containsKey(field.getName())) {
					addGetField(field);
				}
			}
		}
		// 如果有父类，递归处理
		if (clazz.getSuperclass() != null) {
			addFields(clazz.getSuperclass());
		}
	}

	private void addSetField(Field field) {
		//logger.debug("addSetField， field={}",field);
		// 有效属性才加入集合
		if (isValidPropertyName(field.getName())) {
			setMethods.put(field.getName(), new SetFieldInvoker(field));
			// 类型参数转换器、分解器
			Type fieldType = TypeParameterResolver.resolveFieldType(field, type);
			// 字段对应类型转换器关系集合
			setTypes.put(field.getName(), typeToClass(fieldType));
		}
	}

	private void addGetField(Field field) {
		//logger.debug("addGetField， field={}",field);
		if (isValidPropertyName(field.getName())) {
			getMethods.put(field.getName(), new GetFieldInvoker(field));
			Type fieldType = TypeParameterResolver.resolveFieldType(field, type);
			getTypes.put(field.getName(), typeToClass(fieldType));
		}
	}

	/** 判断是否是有效的属性 $ 是maybatis的一个保留字，所以这里属性名称不能以 $开始 **/
	private boolean isValidPropertyName(String name) {
		//logger.debug("isValidPropertyName， name={}",name);
		return !(name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name));
	}

	/*
	 * This method returns an array containing all methods declared in this class
	 * and any superclass. We use this method, instead of the simpler
	 * Class.getMethods(), because we want to look for private methods as well.
	 *
	 * @param cls The class //获取当前类以及父类中定义的所有方法的唯一签名以及相应的Method对象。
	 * 
	 * @return An array containing all methods in this class
	 */
	private Method[] getClassMethods(Class<?> cls) {
		//logger.debug(" 获取当前类以及父类中定义的所有方法的唯一签名以及相应的Method对象。 cls={}",cls);
		Map<String, Method> uniqueMethods = new HashMap<String, Method>();
		Class<?> currentClass = cls;
		while (currentClass != null && currentClass != Object.class) {
			// currentClass.getDeclaredMethods(),获取当前类的所有方法
			// addUniqueMethods 为每个方法生成唯一签名，并记录到uniqueMethods集合中
			addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());

			// we also need to look for interface methods -
			// because the class may be abstract
			// 获取所有的接口，因为类可能是 抽象类
			Class<?>[] interfaces = currentClass.getInterfaces();
			for (Class<?> anInterface : interfaces) {
				// 抽象接口存在也加入 唯一方法集合内
				addUniqueMethods(uniqueMethods, anInterface.getMethods());
			}

			currentClass = currentClass.getSuperclass();
		}
		// 将所有的唯一方法，生成新的Method数组内返回，.values()是关键 这样名称相同的方法会一起存在
		Collection<Method> methods = uniqueMethods.values();
		return methods.toArray(new Method[methods.size()]);
	}

	private void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
		//logger.debug( "addUniqueMethods ");
		for (Method currentMethod : methods) {
			/**
			 * 桥接方法是 JDK 1.5 引入泛型后，为了使Java的泛型方法生成的字节码和 1.5 版本前的字节码相兼容，
			 * 由编译器自动生成的方法。我们可以通过Method.isBridge()方法来判断一个方法是否是桥接方法。
			 */
			// 不是桥接方法才收录
			if (!currentMethod.isBridge()) {
				// 获取当前方法的签名
				String signature = getSignature(currentMethod);
				// check to see if the method is already known
				// if it is known, then an extended class must have
				// overridden a method
				// 方法还没有进入 集合中，加入
				if (!uniqueMethods.containsKey(signature)) {
					// 可否操作私有方法
					if (canAccessPrivateMethods()) {
						try {
							currentMethod.setAccessible(true);
						} catch (Exception e) {
							// Ignored. This is only a final precaution, nothing we can do.
						}
					}

					uniqueMethods.put(signature, currentMethod);
				}
			}
		}
	}

	/// //获取当前方法的签名
	private String getSignature(Method method) {
		//logger.debug( "getSignature method={}",method);
		StringBuilder sb = new StringBuilder();
		Class<?> returnType = method.getReturnType();
		// 有返回参数，加上
		if (returnType != null) {
			sb.append(returnType.getName()).append('#');
		}
		sb.append(method.getName());
		// 方法请求参数
		Class<?>[] parameters = method.getParameterTypes();
		for (int i = 0; i < parameters.length; i++) {
			if (i == 0) {
				sb.append(':');
			} else {
				sb.append(',');
			}
			sb.append(parameters[i].getName());
		}
		return sb.toString();
	}

	// 能否操作私有方法
	private static boolean canAccessPrivateMethods() {
		try {
			// java安全管理器具体细节，
			SecurityManager securityManager = System.getSecurityManager();
			if (null != securityManager) {
				securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
			}
		} catch (SecurityException e) {
			return false;
		}
		return true;
	}

	/*
	 * Gets the name of the class the instance provides information for
	 *
	 * @return The class name
	 */
	public Class<?> getType() {
		return type;
	}

	public Constructor<?> getDefaultConstructor() {
		if (defaultConstructor != null) {
			return defaultConstructor;
		} else {
			throw new ReflectionException("There is no default constructor for " + type);
		}
	}

	public boolean hasDefaultConstructor() {
		return defaultConstructor != null;
	}

	public Invoker getSetInvoker(String propertyName) {
		Invoker method = setMethods.get(propertyName);
		if (method == null) {
			throw new ReflectionException(
					"There is no setter for property named '" + propertyName + "' in '" + type + "'");
		}
		return method;
	}

	public Invoker getGetInvoker(String propertyName) {
		Invoker method = getMethods.get(propertyName);
		if (method == null) {
			throw new ReflectionException(
					"There is no getter for property named '" + propertyName + "' in '" + type + "'");
		}
		return method;
	}

	/*
	 * Gets the type for a property setter
	 *
	 * @param propertyName - the name of the property
	 * 
	 * @return The Class of the propery setter
	 */
	public Class<?> getSetterType(String propertyName) {
		Class<?> clazz = setTypes.get(propertyName);
		if (clazz == null) {
			throw new ReflectionException(
					"There is no setter for property named '" + propertyName + "' in '" + type + "'");
		}
		return clazz;
	}

	/*
	 * Gets the type for a property getter
	 *
	 * @param propertyName - the name of the property
	 * 
	 * @return The Class of the propery getter
	 */
	public Class<?> getGetterType(String propertyName) {
		Class<?> clazz = getTypes.get(propertyName);
		if (clazz == null) {
			throw new ReflectionException(
					"There is no getter for property named '" + propertyName + "' in '" + type + "'");
		}
		return clazz;
	}

	/*
	 * Gets an array of the readable properties for an object
	 *
	 * @return The array
	 */
	public String[] getGetablePropertyNames() {
		return readablePropertyNames;
	}

	/*
	 * Gets an array of the writeable properties for an object
	 *
	 * @return The array
	 */
	public String[] getSetablePropertyNames() {
		return writeablePropertyNames;
	}

	/*
	 * Check to see if a class has a writeable property by name
	 *
	 * @param propertyName - the name of the property to check
	 * 
	 * @return True if the object has a writeable property by the name
	 */
	public boolean hasSetter(String propertyName) {
		return setMethods.keySet().contains(propertyName);
	}

	/*
	 * Check to see if a class has a readable property by name
	 *
	 * @param propertyName - the name of the property to check
	 * 
	 * @return True if the object has a readable property by the name
	 */
	public boolean hasGetter(String propertyName) {
		return getMethods.keySet().contains(propertyName);
	}

	public String findPropertyName(String name) {
		return caseInsensitivePropertyMap.get(name.toUpperCase(Locale.ENGLISH));
	}
}
