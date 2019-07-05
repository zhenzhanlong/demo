package imitate.mybatis.code.generation.bind;

import org.apache.ibatis.session.Configuration;

/**
 * 接口方法定义
 * 
 * @author Administrator
 *
 */
public class InterfaceMethod {

	// 一个内部类 封装了方法的参数信息 返回类型信息等
	private final MethodSignature methodSig;

	// interfaceName：接口名、methodName：方法名
	public InterfaceMethod(String interfaceName, String methodName, Configuration config) {
		// 方法签名
		this.methodSig = new MethodSignature(config, interfaceName, methodName);
	}

	// 未完成
	public String createInterfaceMethodCode() {
		StringBuffer methodCode = new StringBuffer();
		methodCode.append(methodSig.getMetodAuth().getAuth())
		          .append(methodSig.returnTypeCode())
		          .append(methodSig.getMethodName())
		          .append(methodSig.paramCode())
		          .append(";");
		return methodCode.toString();
	}
	// 未完成
	public String createClassMethodCode(String methodBody) {
		StringBuffer methodCode = new StringBuffer();
		methodCode.append(methodSig.getMetodAuth().getAuth())
		          .append(methodSig.returnTypeCode())
		          .append(methodSig.getMethodName())
		          .append(methodSig.paramCode())
		          .append("{")
		          .append(methodBody)
		          .append("}");
		return methodCode.toString();
	}

	public static class MethodSignature {
		/** 方法类型 **/
		private MethodType methodType;
		/** 方法名称 **/
		private String methodName;
		/** 访问限制 **/
		private MethodAuth metodAuth;
		/** 方法返回类型 **/
		private ReturnType returnType;
		/** 是否返回多条结果 **/
		private boolean returnsMany;
		/** 返回值是否是MAP **/
		private boolean returnsMap;
		/** 返回值是否是void **/
		private boolean returnsVoid;
		/** 参数名称 **/
		private String[] paramNames;
		/** 参数类型 **/
		private String[] paramTypes;
		/** 方法体 **/
		private StringBuilder methodBody;
		/** 方法说明**/
		private StringBuilder methodExplain;
		// // resultHandler类型参数的位置
		// private Integer resultHandlerIndex;
		// // rowBound类型参数的位置 (分页)
		// private Integer rowBoundsIndex;

		// 方法签名
		public MethodSignature(Configuration configuration, String interfaceName, String methodName) {

		}

		public MethodType getMethodType() {
			return methodType;
		}

		public void setMethodType(MethodType methodType) {
			this.methodType = methodType;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public MethodAuth getMetodAuth() {
			return metodAuth;
		}

		public void setMetodAuth(MethodAuth metodAuth) {
			this.metodAuth = metodAuth;
		}

		public ReturnType getReturnType() {
			return returnType;
		}

		public void setReturnType(ReturnType returnType) {
			this.returnType = returnType;
		}

		public boolean isReturnsMany() {
			return returnsMany;
		}

		public void setReturnsMany(boolean returnsMany) {
			this.returnsMany = returnsMany;
		}

		public boolean isReturnsMap() {
			return returnsMap;
		}

		public void setReturnsMap(boolean returnsMap) {
			this.returnsMap = returnsMap;
		}

		public boolean isReturnsVoid() {
			return returnsVoid;
		}

		public void setReturnsVoid(boolean returnsVoid) {
			this.returnsVoid = returnsVoid;
		}

		public String[] getParamNames() {
			return paramNames;
		}

		public void setParamNames(String[] paramNames) {
			this.paramNames = paramNames;
		}

		public String[] getParamTypes() {
			return paramTypes;
		}

		public void setParamTypes(String[] paramTypes) {
			this.paramTypes = paramTypes;
		}
		
		public StringBuilder getMethodBody() {
			return methodBody;
		}

		public void setMethodBody(StringBuilder methodBody) {
			this.methodBody = methodBody;
		}
		
		public StringBuilder getMethodExplain() {
			return methodExplain;
		}

		public void setMethodExplain(StringBuilder methodExplain) {
			this.methodExplain = methodExplain;
		}

		public String returnTypeCode() {
			return "void";
		}
		public String paramCode() {
			return "( Form form  )";
		}
	}

}
