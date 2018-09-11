把这里是本人自己写的各种代码的实例

dubbo-1 是最开始的spring与dubbo的实例，使用的时spring boot集成dubbo，这里使用的dubbo是alibobo最开始自己开发的包，并不是有springboot写的集成开发包，
        springboot写的集成开发包有一个很大的问题，就是接口类只能定义在本项目中，就如：ICityDubbo必须在provider与client里面个存在一份。而且涉及到的VO         等都是每个项目自己一份，需要的时，vo的属性必须对应。个人感觉，springboot集成的不算好用
        
   zookeeper ：链接的公司测试环境的，需要自己安装zookeeper,链接自己的，才能使用
   
dubbo-2 是springboot自己写的与dubbo的集成开发包，问题是，接口，vo等需要在两个项目内都必须自己写。

dubbo-3 集成如下：
        1、spring 与 dubbo 2.5.3的集成
        2、springboot 方式启动
        3、通过代码，设置默认访问页  WebAppConfigurer
        4、集成freemarker、
        5、集成 @PropertySource  配置文件加载  application.properties是自动加载，需要加载别的文件就需要配置了。
        6、定义错误界面跳转 ErrorConfigurar
        7、定义异常处理器 HandlerExceptionResolverImpl
        8、集成 Swagger    resufull 文件
        9、集成 form 格式表单验证   UserController
        
dubbo-4 集成如下：
        1、spring 与 dubbo 2.5.3的集成
        2、springboot 方式启动
        3、通过代码，设置默认访问页  WebAppConfigurer
        4、集成freemarker
        5、集成 @PropertySource  配置文件加载
        6、定义错误界面跳转 ErrorConfigurar
        7、定义异常处理器 HandlerExceptionResolverImpl
        8、集成 Swagger    resufull 文件
        9、集成 form 格式表单验证   UserController
        10、集成logback 日志输出
        11、集成mysql，  demo-provider
        12、集成mybatis demo -provider
        13、集成alibaba  durid 连接池  demo-provider
        
manage-web-consumer-transfer：
        主要实现功能是从数据库1中查询数据，然后比对数据库2中的数据，判断是否需要生成sql
        1、springboot 方式启动
        2、集成logback 日志输出
        3、集成mysql，  demo-provider
        4、集成mybatis demo -provider  多数据源，java代码配置数据源（不是xml），java 配置Mapper文件等相关信息

        

        
