/**
 *    Copyright 2009-2016 the original author or authors.
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
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * 类型参数转换器、分解器
 * 
 * @author Iwao AVE!
 */
public class TypeParameterResolver {
	// private static Logger logger =
	// LoggerFactory.getLogger(TypeParameterResolver.class);

	/**
	 * @return The field type as {@link Type}. If it has type parameters in the
	 *         declaration,<br>
	 *         they will be resolved to the actual runtime {@link Type}s.
	 */
	public static Type resolveFieldType(Field field, Type srcType) {
		// 获取需要解析的字段类型
		Type fieldType = field.getGenericType();
		// 获取字段定义的类的class
		Class<?> declaringClass = field.getDeclaringClass();
		// logger.debug("解析字段类型 field={},srcType={},declaringClass={}", field, srcType,
		// declaringClass);
		return resolveType(fieldType, srcType, declaringClass);
	}

	/**
	 * @return The return type of the method as {@link Type}. If it has type
	 *         parameters in the declaration,<br>
	 *         they will be resolved to the actual runtime {@link Type}s.
	 */
	public static Type resolveReturnType(Method method, Type srcType) {
		// 获取方法的返回类型
		Type returnType = method.getGenericReturnType();
		// 获取方法定义的类的类型
		Class<?> declaringClass = method.getDeclaringClass();
		// logger.debug("解析方法参数类型 method={},srcType={},declaringClass={}", method,
		// srcType, declaringClass);
		return resolveType(returnType, srcType, declaringClass);
	}

	/**
	 * @return The parameter types of the method as an array of {@link Type}s. If
	 *         they have type parameters in the declaration,<br>
	 *         they will be resolved to the actual runtime {@link Type}s.
	 */
	public static Type[] resolveParamTypes(Method method, Type srcType) {
		// 获取方法所有参数类型
		Type[] paramTypes = method.getGenericParameterTypes();
		// 获取方法定义的类类型
		Class<?> declaringClass = method.getDeclaringClass();
		Type[] result = new Type[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			// 对每个参数的类型进行解析
			// logger.debug("解析方法参数类型
			// method={},paramTypes[i]={},srcType={},declaringClass={}", method,
			// paramTypes[i],
			// srcType, declaringClass);
			result[i] = resolveType(paramTypes[i], srcType, declaringClass);
		}
		return result;
	}

	private static Type resolveType(Type type, Type srcType, Class<?> declaringClass) {
		if (type instanceof TypeVariable) {// 类型变量解析
			// logger.debug("解析类型是通用类型 type={},srcType={},declaringClass={}", type, srcType,
			// declaringClass);
			return resolveTypeVar((TypeVariable<?>) type, srcType, declaringClass);
		} else if (type instanceof ParameterizedType) {// 参数化类型解析
			// logger.debug("解析类型是参数化类型 type={},srcType={},declaringClass={}", type,
			// srcType, declaringClass);
			return resolveParameterizedType((ParameterizedType) type, srcType, declaringClass);
		} else if (type instanceof GenericArrayType) {// 泛型数组解析
			// logger.debug("解析类型是参数化数组类型 type={},srcType={},declaringClass={}", type,
			// srcType, declaringClass);
			return resolveGenericArrayType((GenericArrayType) type, srcType, declaringClass);
		} else {
			// 如果为普通的Class类型就直接返回
			// logger.debug("普通的Classtype={}", type);
			return type;
		}
	}

	// 泛型数组类型
	private static Type resolveGenericArrayType(GenericArrayType genericArrayType, Type srcType,
			Class<?> declaringClass) {
		// logger.debug("解析 泛型数组 genericArrayType={},srcType={},declaringClass={}",
		// genericArrayType, srcType,
		// declaringClass);
		// 去掉一层[]后的泛型类型变量
		Type componentType = genericArrayType.getGenericComponentType();
		// logger.debug("去掉一层[]后的泛型类型变量 componentType={}", componentType);
		Type resolvedComponentType = null;
		// 根据去掉一维数组后的类型变量，在根据其类型递归解析
		if (componentType instanceof TypeVariable) {
			// logger.debug("去掉一层[]后的泛型类型变量 componentType={},srcType={},declaringClass={}",
			// componentType, srcType,
			// declaringClass);
			// 如果去掉后为TypeVariable类型，则调用resolveTypeVar方法
			resolvedComponentType = resolveTypeVar((TypeVariable<?>) componentType, srcType, declaringClass);
		} else if (componentType instanceof GenericArrayType) {
			// logger.debug("去掉一层[]后的泛型类型变量 componentType={},srcType={},declaringClass={}",
			// componentType, srcType,
			// declaringClass);
			// 如果去掉仍为GenericArrayType类型，则递归调用resolveGenericArrayType方法
			resolvedComponentType = resolveGenericArrayType((GenericArrayType) componentType, srcType, declaringClass);
		} else if (componentType instanceof ParameterizedType) {
			// logger.debug("去掉一层[]后的泛型类型变量 componentType={},srcType={},declaringClass={}",
			// componentType, srcType,
			// declaringClass);
			// 如果去掉后为ParameterizedType类型，则调用resolveParameterizedType方法处理
			resolvedComponentType = resolveParameterizedType((ParameterizedType) componentType, srcType,
					declaringClass);
		}
		if (resolvedComponentType instanceof Class) {
			// 如果处理后的结果为基本的Class类型，则返回对应的Class的数组类型（处理N[][]）。
			// logger.debug("如果处理后的结果为基本的Class类型，则返回对应的Class的数组类型（处理N[][]）。resolvedComponentType={}",
			/// resolvedComponentType);
			return Array.newInstance((Class<?>) resolvedComponentType, 0).getClass();
		} else {
			// 否则包装为自定义的GenericArrayTypeImpl类型
			// logger.debug("否则包装为自定义的GenericArrayTypeImpl类型resolvedComponentType={}",
			// resolvedComponentType);
			return new GenericArrayTypeImpl(resolvedComponentType);
		}
	}

	private static ParameterizedType resolveParameterizedType(ParameterizedType parameterizedType, Type srcType,
			Class<?> declaringClass) {
		// logger.debug("解析 resolveParameterizedType 类型
		// parameterizedType={},srcType={},declaringClass={}",
		// parameterizedType, srcType, declaringClass);
		// 获取泛型的基本类型
		Class<?> rawType = (Class<?>) parameterizedType.getRawType();
		// //获取泛型中的类型实参
		Type[] typeArgs = parameterizedType.getActualTypeArguments();
		Type[] args = new Type[typeArgs.length];
		for (int i = 0; i < typeArgs.length; i++) {
			// 判断对应参数的类型，分别进行递归处理
			if (typeArgs[i] instanceof TypeVariable) {
				// 解析TypeVariable类型
				// logger.debug("析TypeVariable类型 类型
				// typeArgs[i]={},srcType={},declaringClass={}", typeArgs[i], srcType,
				// declaringClass);
				args[i] = resolveTypeVar((TypeVariable<?>) typeArgs[i], srcType, declaringClass);
			} else if (typeArgs[i] instanceof ParameterizedType) {
				// 解析ParameterizedType类型
				// logger.debug("析ParameterizedType类型 类型
				// typeArgs[i]={},srcType={},declaringClass={}", typeArgs[i],
				// srcType, declaringClass);
				args[i] = resolveParameterizedType((ParameterizedType) typeArgs[i], srcType, declaringClass);
			} else if (typeArgs[i] instanceof WildcardType) {
				// 是解析WildcardType类型（其类型实参是通配符表达式）
				// logger.debug("析ParameterizedType类型 类型
				// typeArgs[i]={},srcType={},declaringClass={}", typeArgs[i],
				// srcType, declaringClass);
				args[i] = resolveWildcardType((WildcardType) typeArgs[i], srcType, declaringClass);
			} else {
				// 普通Class类型，直接返回
				// logger.debug("解析类型为普通 typeArgs[i]={}", typeArgs[i]);
				args[i] = typeArgs[i];
			}
		}
		// 返回自定以类型
		return new ParameterizedTypeImpl(rawType, null, args);
	}

	// 解析调用类型 通配符表达式 类型解析
	private static Type resolveWildcardType(WildcardType wildcardType, Type srcType, Class<?> declaringClass) {
		// logger.debug("解析调用类型 resolveWildcardType
		// wildcardType={},srcType={},declaringClass={}", wildcardType,
		// srcType, declaringClass);
		// 获取下限
		Type[] lowerBounds = resolveWildcardTypeBounds(wildcardType.getLowerBounds(), srcType, declaringClass);
		// 获取上限
		Type[] upperBounds = resolveWildcardTypeBounds(wildcardType.getUpperBounds(), srcType, declaringClass);
		// 包装成自定义的WildcardTypeImpl类型返回
		// logger.debug("包装成自定义的WildcardTypeImpl类型返回 lowerBounds={},upperBounds={}",
		// lowerBounds, upperBounds);
		return new WildcardTypeImpl(lowerBounds, upperBounds);
	}

	// 解析调用类型通配符
	private static Type[] resolveWildcardTypeBounds(Type[] bounds, Type srcType, Class<?> declaringClass) {
		// logger.debug("解析调用类型通配符 resolveWildcardTypeBounds
		// bounds={},srcType={},declaringClass={}", bounds, srcType,
		// declaringClass);
		Type[] result = new Type[bounds.length];
		for (int i = 0; i < bounds.length; i++) {
			// 根据上下限不同的类型，进行解析
			if (bounds[i] instanceof TypeVariable) {
				// logger.debug("根据上下限不同的类型，TypeVariable 进行解析
				// bounds[i]={},srcType={},declaringClass={}", bounds[i],
				// srcType, declaringClass);
				result[i] = resolveTypeVar((TypeVariable<?>) bounds[i], srcType, declaringClass);
			} else if (bounds[i] instanceof ParameterizedType) {
				// logger.debug("根据上下限不同的类型，ParameterizedType 进行解析
				// bounds[i]={},srcType={},declaringClass={}", bounds[i],
				// srcType, declaringClass);
				result[i] = resolveParameterizedType((ParameterizedType) bounds[i], srcType, declaringClass);
			} else if (bounds[i] instanceof WildcardType) {
				// logger.debug("根据上下限不同的类型，WildcardType 进行解析
				// bounds[i]={},srcType={},declaringClass={}", bounds[i],
				// srcType, declaringClass);
				result[i] = resolveWildcardType((WildcardType) bounds[i], srcType, declaringClass);
			} else {
				// logger.debug("根据上下限不同的类型，Class,普通 进行解析
				// bounds[i]={},srcType={},declaringClass={}", bounds[i], srcType,
				// declaringClass);
				result[i] = bounds[i];
			}
		}
		return result;
	}

	/**
	 * 解析具体的类型变量指代的类型。
	 * 1.如果srcType的Class类型和declaringClass为同一个类，表示获取该类型变量时被反射的类型就是其定义的类型，则取该类型变量定义是有没有上限，如果有则使用其上限代表其类型，否则就用Object。
	 * 2.如果不是，则代表declaringClass是srcType的父类或者实现的接口，则解析继承中有没有定义其代表的类型
	 */
	private static Type resolveTypeVar(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass) {
		Type result = null;
		Class<?> clazz = null;
		/**
		 * 判断srcType是否为Class/ParameterizedType类型 如果不是这两种类型这抛出异常
		 */
		if (srcType instanceof Class) {
			// logger.debug("srcType是普通类={}", srcType);
			clazz = (Class<?>) srcType;
		} else if (srcType instanceof ParameterizedType) {
			// logger.debug("srcType是 ParameterizedType ={}", srcType);
			ParameterizedType parameterizedType = (ParameterizedType) srcType;
			clazz = (Class<?>) parameterizedType.getRawType();
		} else {
			// logger.debug("The 2nd arg must be Class or ParameterizedType, but was:{}",
			// srcType.getClass());
			throw new IllegalArgumentException(
					"The 2nd arg must be Class or ParameterizedType, but was: " + srcType.getClass());
		}
		/**
		 * 如果declaringClass和srcType的实际类型一致。则表示无法获取其类型实参。
		 * 如果typeVar有上限限定则返回其上限，否则返回Object处理
		 */
		if (clazz == declaringClass) {
			Type[] bounds = typeVar.getBounds();
			if (bounds.length > 0) {
				return bounds[0];
			}
			return Object.class;
		}

		Type superclass = clazz.getGenericSuperclass();
		// logger.debug("获取父类(不是接口)，判断父类类型clazz={},superclass={}",clazz,superclass);
		result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superclass);
		if (result != null) {
			// logger.debug("父类类型不为空返回result={}",result);
			return result;
		}
		/**
		 * 如果父类的定义中没有，则处理其实现的接口。
		 */
		Type[] superInterfaces = clazz.getGenericInterfaces();
		// logger.debug("获取父接口(不是接口)，判断父接口类型clazz={},superclass={}",clazz,superclass);
		for (Type superInterface : superInterfaces) {
			// logger.debug("父接口，判断父接口类型clazz={},superInterface={}",clazz,superInterface);
			result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superInterface);
			if (result != null) {
				return result;
			}
		}
		// 如果父类或者实现的接口中都没有获取到形参对应的实参，则返回Object.class
		return Object.class;
	}

	/**
	 * 通过对父类/接口的扫描获取其typeVar指代的实际类型
	 */
	private static Type scanSuperTypes(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass, Class<?> clazz,
			Type superclass) {
		// logger.debug("通过对父类/接口的扫描获取其typeVar指代的实际类型typeVar={},srcType={},declaringClass={},clazz={},superclass={}",typeVar,srcType,declaringClass,clazz,superclass);
		Type result = null;
		/*
		 * 判断处理的父类superclass是否为参数化类型，如果不是则代表declaringClass和superclass的基本Class 类型不是同一个类。
		 */
		// logger.debug("判断处理的父类superclass是否为参数化类型，如果不是则代表declaringClass和superclass的基本Class
		// 类型不是同一个类。");
		if (superclass instanceof ParameterizedType) {
			// 如果为ParameterizedType，则获取它基本类型
			ParameterizedType parentAsType = (ParameterizedType) superclass;
			Class<?> parentAsClass = (Class<?>) parentAsType.getRawType();
			// 如果declaringClass和parentAsClass表示同一类型，则通过typeVar在declaringClass的泛型形参的index获取其在supperClass中定义的类型实参
			// logger.debug("父类是 ParameterizedType superclass={},
			// parentAsClass={}",superclass,parentAsClass);
			if (declaringClass == parentAsClass) {
				Type[] typeArgs = parentAsType.getActualTypeArguments();
				TypeVariable<?>[] declaredTypeVars = declaringClass.getTypeParameters();
				for (int i = 0; i < declaredTypeVars.length; i++) {
					// 循环判断当前处理的类型是否属于所属的类型描述符中的变量
					if (declaredTypeVars[i] == typeVar) {
						/*
						 * 如果supperClass中定义的类型形参还是类型变量则取srcType中的类型形参的定义 如果srcType中的类型形参还是类型变量则不处理。
						 */
						if (typeArgs[i] instanceof TypeVariable) {
							// 其子类中的所有泛型描述符
							TypeVariable<?>[] typeParams = clazz.getTypeParameters();
							for (int j = 0; j < typeParams.length; j++) {
								if (typeParams[j] == typeArgs[i]) {
									// 判断是否为ParameterizedType，则去实际代表的类型
									if (srcType instanceof ParameterizedType) {
										result = ((ParameterizedType) srcType).getActualTypeArguments()[j];
									}
									break;
								}
							}
						} else {
							// 如果不是TypeVariable，直接取对应的类型
							result = typeArgs[i];
						}
					}
				}
			} else if (declaringClass.isAssignableFrom(parentAsClass)) {
				// 通过判断superclass是否是declaringClass的子类（由于java类可以实现多个接口），进行递归解析
				result = resolveTypeVar(typeVar, parentAsType, declaringClass);
			}
		} else if (superclass instanceof Class) {
			// 如果superclass为Class类型，通过判断superclass是否是declaringClass的子类（由于java类可以实现多个接口），进行递归解析
			if (declaringClass.isAssignableFrom((Class<?>) superclass)) {
				result = resolveTypeVar(typeVar, superclass, declaringClass);
			}
		}
		return result;
	}

	private TypeParameterResolver() {
		super();
	}

	// 内部类的四种形式
	// 1、成员内部类，我自己经常用的
	// 2、匿名内部类
	// 3、局部内部类
	// 4、静态内部类
	static class ParameterizedTypeImpl implements ParameterizedType {
		private Class<?> rawType;

		private Type ownerType;

		private Type[] actualTypeArguments;

		public ParameterizedTypeImpl(Class<?> rawType, Type ownerType, Type[] actualTypeArguments) {
			super();
			this.rawType = rawType;
			this.ownerType = ownerType;
			this.actualTypeArguments = actualTypeArguments;
			//logger.debug("静态内部类 ParameterizedTypeImpl rawType={},ownerType={}，actualTypeArguments={}", rawType,
			//		ownerType, actualTypeArguments);
		}

		@Override
		public Type[] getActualTypeArguments() {
			return actualTypeArguments;
		}

		@Override
		public Type getOwnerType() {
			return ownerType;
		}

		@Override
		public Type getRawType() {
			return rawType;
		}

		@Override
		public String toString() {
			return "ParameterizedTypeImpl [rawType=" + rawType + ", ownerType=" + ownerType + ", actualTypeArguments="
					+ Arrays.toString(actualTypeArguments) + "]";
		}
	}

	static class WildcardTypeImpl implements WildcardType {
		private Type[] lowerBounds;

		private Type[] upperBounds;

		private WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
			super();
			this.lowerBounds = lowerBounds;
			this.upperBounds = upperBounds;
			// logger.debug("静态内部类 WildcardTypeImpl lowerBounds={},upperBounds={}",
			// lowerBounds, upperBounds);
		}

		@Override
		public Type[] getLowerBounds() {
			return lowerBounds;
		}

		@Override
		public Type[] getUpperBounds() {
			return upperBounds;
		}
	}

	static class GenericArrayTypeImpl implements GenericArrayType {
		private Type genericComponentType;

		private GenericArrayTypeImpl(Type genericComponentType) {
			super();
			this.genericComponentType = genericComponentType;
			// logger.debug("静态内部类 GenericArrayTypeImpl
			// genericComponentType={},upperBounds={}", genericComponentType);
		}

		@Override
		public Type getGenericComponentType() {
			return genericComponentType;
		}
	}
}
