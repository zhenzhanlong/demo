/**
 *    Copyright 2009-2017 the original author or authors.
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imitate.mybatis.code.generation.reflection.invoker.GetFieldInvoker;
import imitate.mybatis.code.generation.reflection.invoker.Invoker;
import imitate.mybatis.code.generation.reflection.invoker.MethodInvoker;
import imitate.mybatis.code.generation.reflection.property.PropertyTokenizer;

/**
 * 元数据（解析Settings配置关联的时候使用了）
 * 
 * @author Clinton Begin MetaClass是一个保存对象定义比如getter/setter/构造器等的元数据类
 */
public class MetaClass {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 是mybatis提供的默认反射工厂实现,这个ReflectorFactory主要采用了工厂类，其内部使用的Reflector采用了facade设计模式，简化反射的使用
	 */
	private final ReflectorFactory reflectorFactory;
	// 反射器
	private final Reflector reflector;

	private MetaClass(Class<?> type, ReflectorFactory reflectorFactory) {
		this.reflectorFactory = reflectorFactory;
		this.reflector = reflectorFactory.findForClass(type);
		logger.debug(" MetaClass 构造 type={}",type);
	}

	public static MetaClass forClass(Class<?> type, ReflectorFactory reflectorFactory) {
		return new MetaClass(type, reflectorFactory);
	}

	/**
	 * 获取对象属性 name get 返回类型 的MetaClass
	 * 
	 * @param name
	 * @return
	 */
	public MetaClass metaClassForProperty(String name) {
		Class<?> propType = reflector.getGetterType(name);
		logger.debug(" metaClassForProperty  getGetterType  name={}",name);
		return MetaClass.forClass(propType, reflectorFactory);
	}

	/**
	 * 获取属性
	 * 
	 * @param name
	 * @return
	 */
	public String findProperty(String name) {
		logger.debug(" metaClassForProperty  findProperty  name={}",name);
		StringBuilder prop = buildProperty(name, new StringBuilder());
		return prop.length() > 0 ? prop.toString() : null;
	}

	/**
	 * 获取属性 属性名称映射
	 * 
	 * @param name
	 * @param useCamelCaseMapping
	 * @return
	 */
	public String findProperty(String name, boolean useCamelCaseMapping) {
		if (useCamelCaseMapping) {
			name = name.replace("_", "");
		}
		return findProperty(name);
	}

	public String[] getGetterNames() {
		return reflector.getGetablePropertyNames();
	}

	public String[] getSetterNames() {
		return reflector.getSetablePropertyNames();
	}

	public Class<?> getSetterType(String name) {
		logger.debug(" getSetterType  name={}",name);
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			MetaClass metaProp = metaClassForProperty(prop.getName());
			return metaProp.getSetterType(prop.getChildren());
		} else {
			return reflector.getSetterType(prop.getName());
		}
	}

	public Class<?> getGetterType(String name) {
		logger.debug(" getGetterType  name={}",name);
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			MetaClass metaProp = metaClassForProperty(prop);
			return metaProp.getGetterType(prop.getChildren());
		}
		// issue #506. Resolve the type inside a Collection Object
		return getGetterType(prop);
	}

	/**
	 * 获取对象分词器 get 返回类型 的MetaClass
	 * 
	 * @param name
	 * @return
	 */
	private MetaClass metaClassForProperty(PropertyTokenizer prop) {
		Class<?> propType = getGetterType(prop);
		return MetaClass.forClass(propType, reflectorFactory);
	}

	/**
	 * 获取 分词器 的返回值类型
	 * 
	 * @param prop
	 * @return
	 */
	private Class<?> getGetterType(PropertyTokenizer prop) {
		logger.debug(" getGetterType  prop={}",prop);
		Class<?> type = reflector.getGetterType(prop.getName());
		if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
			Type returnType = getGenericGetterType(prop.getName());
			if (returnType instanceof ParameterizedType) {
				Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
				if (actualTypeArguments != null && actualTypeArguments.length == 1) {
					returnType = actualTypeArguments[0];
					if (returnType instanceof Class) {
						type = (Class<?>) returnType;
					} else if (returnType instanceof ParameterizedType) {
						type = (Class<?>) ((ParameterizedType) returnType).getRawType();
					}
				}
			}
		}
		return type;
	}

	/**
	 * 获取属性名 通用 get type
	 * 
	 * @param propertyName
	 *            可能是 字段、也可能是方法
	 * @return
	 */
	private Type getGenericGetterType(String propertyName) {
		logger.debug(" getGenericGetterType  propertyName={}",propertyName);
		try {
			Invoker invoker = reflector.getGetInvoker(propertyName);
			if (invoker instanceof MethodInvoker) {
				Field _method = MethodInvoker.class.getDeclaredField("method");
				_method.setAccessible(true);
				Method method = (Method) _method.get(invoker);
				return TypeParameterResolver.resolveReturnType(method, reflector.getType());
			} else if (invoker instanceof GetFieldInvoker) {
				Field _field = GetFieldInvoker.class.getDeclaredField("field");
				_field.setAccessible(true);
				Field field = (Field) _field.get(invoker);
				return TypeParameterResolver.resolveFieldType(field, reflector.getType());
			}
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}

	public boolean hasSetter(String name) {
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			if (reflector.hasSetter(prop.getName())) {
				MetaClass metaProp = metaClassForProperty(prop.getName());
				return metaProp.hasSetter(prop.getChildren());
			} else {
				return false;
			}
		} else {
			return reflector.hasSetter(prop.getName());
		}
	}

	public boolean hasGetter(String name) {
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			if (reflector.hasGetter(prop.getName())) {
				MetaClass metaProp = metaClassForProperty(prop);
				return metaProp.hasGetter(prop.getChildren());
			} else {
				return false;
			}
		} else {
			return reflector.hasGetter(prop.getName());
		}
	}

	public Invoker getGetInvoker(String name) {
		return reflector.getGetInvoker(name);
	}

	public Invoker getSetInvoker(String name) {
		return reflector.getSetInvoker(name);
	}

	/**
	 * 创建属性名称 如果属性 分多级 person.bank.code会递归生成
	 * 
	 * @param name
	 * @param builder
	 * @return
	 */
	private StringBuilder buildProperty(String name, StringBuilder builder) {
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			String propertyName = reflector.findPropertyName(prop.getName());
			if (propertyName != null) {
				builder.append(propertyName);
				builder.append(".");
				MetaClass metaProp = metaClassForProperty(propertyName);
				metaProp.buildProperty(prop.getChildren(), builder);
			}
		} else {
			String propertyName = reflector.findPropertyName(name);
			if (propertyName != null) {
				builder.append(propertyName);
			}
		}
		return builder;
	}

	public boolean hasDefaultConstructor() {
		return reflector.hasDefaultConstructor();
	}

}
