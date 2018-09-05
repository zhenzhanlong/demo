把这里是本人自己写的各种代码的实例

dubbo-1 是最开始的spring与dubbo的实例，使用的时spring boot集成dubbo，这里使用的dubbo是alibobo最开始自己开发的包，并不是有springboot写的集成开发包，
        springboot写的集成开发包有一个很大的问题，就是接口类只能定义在本项目中，就如：ICityDubbo必须在provider与client里面个存在一份。而且涉及到的VO         等都是每个项目自己一份，需要的时，vo的属性必须对应。个人感觉，springboot集成的不算好用
        
dubbo-2 是springboot自己写的与dubbo的集成开发包，问题是，接口，vo等需要在两个项目内都不许自己写。

zookeeper ：链接的公司测试环境的，需要自己安装zookeeper,链接自己的，才能使用
        
