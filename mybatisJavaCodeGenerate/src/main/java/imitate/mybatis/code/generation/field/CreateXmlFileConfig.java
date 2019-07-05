//package imitate.mybatis.code.generation.field;
//
//import imitate.mybatis.code.generation.parsing.XNode;
//import imitate.mybatis.code.generation.session.Configuration;
//import imitate.mybatis.code.generation.type.Constants;
//import imitate.mybatis.code.generation.type.FileNameEnumType;
//import imitate.mybatis.code.generation.util.NameUtil;
//
///**
// * 生成 xml 文件类
// * 
// * @author Administrator
// *
// */
//public class CreateXmlFileConfig extends CreateIApiFileConfig {
//
//
//	public CreateXmlFileConfig(Configuration configuration) {
//		super(configuration);
//	}
//
//	public CreateXmlFileConfig(XNode fileBody, Configuration configuration) {
//		super(fileBody, configuration);
//	}
//
//	/********************* mapper *********************/
//	public String getMapperName() {
//		return NameUtil.createClassName(this.configuration.getCalssPrefix(), getTableNameHandle(),
//				FileNameEnumType.MAPPER_NAME);
//	}
//
//	public String getMapperFileName() {
//		return getPoName() + Constants.MAPPER_FILE_SUFFIX;
//	}
//
//	public String getMapperPackage() {
//		StringBuilder str = new StringBuilder();
//		str.append(getPackagePath()).append(this.configuration.getPathMapper());
//		return str.toString();
//	}
//
//}
