package imitate.mybatis.code.generation.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * json util
 * 
 * @author dwpeng
 */
public final class JackJsonUtil {
	private JackJsonUtil() {
	}

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger log = LoggerFactory.getLogger(JackJsonUtil.class);

	/**
	 * 把JavaBean转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String objToJson(Object object) {
		try {
			if (object == null) {
				return null;
			}
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("ObjToJSon error:{}", e.getMessage());
			return null;
		}

	}

	/**
	 * 把json转换为javabean字符串
	 * 
	 * @param objClass
	 *            对象类型
	 * @return
	 */
	public static <T> T jsonToJavaBean(String json, Class<T> objClass) {
		try {
			return readJson(json, objClass);
		} catch (Exception e) {
			log.error("jsonToJavaBean:{}", e.getMessage());
			return null;
		}

	}

	/**
	 * map 数据先转json 在转 成类
	 * 
	 * @param json
	 * @param classs
	 * @param classMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T jsonToJavaBean(String json, Class<?> classs, Map<String, Class> classMap) {
		if (json == null) {
			return null;
		}
		try {
			json = objectMapper.writeValueAsString(classMap);
			return readJson(json, classs);
		} catch (Exception e) {
			log.error("jsonToJavaBean error={}", e);
		}

		return null;

	}

	/**
	 * 将list转化为json
	 * 
	 * @param list
	 *            List
	 * @return String json字符串
	 */
	@SuppressWarnings("rawtypes")
	public static String listToJson(List list) {
		if (list == null) {
			return null;
		}
		return objToJson(list);
	}

	/**
	 * 将json转化为list
	 * 
	 * @param jsonlist
	 *            jsonlist字符串
	 * @return elementClasses list 里边的对象类型
	 */
	@SuppressWarnings("rawtypes")
	public static List jsonToList(String jsonlist, Class<?>... elementClasses) {
		if (jsonlist == null) {
			return null;
		}
		try {
			return readJson(jsonlist, List.class, elementClasses);
		} catch (Exception e) {
			log.error("jonToList:{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 将list转化为json
	 * 
	 * @param list
	 *            List
	 * @return String json字符串
	 */
	public static String mapToJson(@SuppressWarnings("rawtypes") Map map) {
		if (map == null) {
			return null;
		}
		return objToJson(map);
	}

	/**
	 * 将json转化为map
	 * 
	 * @param jsonmap
	 *            jsonmap字符串
	 * @return elementClasses map 里边的对象类型
	 */
	@SuppressWarnings("rawtypes")
	public static Map jsonToMap(String jsonmap, Class<?>... elementClasses) {
		if (jsonmap == null) {
			return null;
		}
		try {
			return readJson(jsonmap, Map.class, elementClasses);
		} catch (Exception e) {
			log.error("jonToMap:{}", e.getMessage());
			return null;
		}
	}

	/**
	 * 数组 转换为 Json
	 * 
	 * @param os
	 *            数组
	 * @return json
	 */
	public static String arrayToJson(Object[] os) {
		if (os == null) {
			return null;
		}
		return objToJson(os);
	}

	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类型
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
		try {
			if (elementClasses == null || elementClasses.length == 0) {
				return (T) objectMapper.readValue(jsonStr, collectionClass);
			} else {
				JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
				return objectMapper.readValue(jsonStr, javaType);
			}
		} catch (Exception e) {
			log.error("readJson error={}", e);
		}
		return null;
	}

	/**
	 * 创建一个objectNode对象
	 * 
	 * @return ObjectNode
	 */
	public static ObjectNode createObjectNode() {
		return objectMapper.createObjectNode();
	}

	/**
	 * jsonnode to string
	 * 
	 * @param jsonnode
	 * @return String
	 */
	public static String jsonnodeToString(JsonNode jsonnode) {
		return jsonnode.toString();
	}

	/**
	 * json字符串转换为JsonNode
	 * 
	 * @param jsonstr
	 *            json字符串
	 * @return JsonNode
	 */
	public static JsonNode parse(String jsonstr) {
		try {
			return objectMapper.readValue(jsonstr, JsonNode.class);
		} catch (Exception e) {
			log.error("parse error={}", e);
		}
		return null;
	}

	/**
	 * json 转 class
	 * 
	 * @param paramJsonNode
	 * @param paramClass
	 * @return
	 */
	public static <A> A fromJson(JsonNode paramJsonNode, Class<A> paramClass) {
		try {
			return objectMapper.treeToValue(paramJsonNode, paramClass);
		} catch (Exception e) {
			log.error("fromJson error={}", e);
		}
		return null;
	}

	/**
	 * json字符流转换为JsonNode
	 * 
	 * @param jsonstrtream
	 * @return JsonNode
	 */
	public static JsonNode parse(InputStream jsonstrtream) {
		try {
			return objectMapper.readValue(jsonstrtream, JsonNode.class);
		} catch (Exception e) {
			log.error("parse(InputStream jsonstrtream) error={}", e);
		}
		return null;
	}

}
