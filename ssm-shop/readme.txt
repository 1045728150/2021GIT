搭建SSM项目的步骤：
        ①新建MAVEN的web项目（使用/2021/student表,其中id自增）,改造目录（包括web.xml版本，新建一些文件夹，index.jsp）
        ②加入依赖：springmvc，spring，mybatis
             mybatis-spring
             spring-tx，spring-jdbc（事务依赖）
             jackson-databind，jackson-core（jackson依赖 ）
             mysql驱动
             druid连接池
             jsp,servlet
             （配置文件信息：放在pom.txt中）
        ③配置web.xml
              <1>注册DispatcherServlet：创建SpringMVC容器对象，才能创建Controller对象；接受用户请求
              <2>注册Spring监听器ContextLoaderListener：创建Spring容器对象，才能创建service，dao等对象
              <3>注册字符集过滤器，解决post请求乱码的问题
        ④创建包：Controller包，Service，dao，实体类包名创建出来
        ⑤写springmvc，spring，mybatis的配置文件
              <1>springmvc配置文件（springmvc.xml）
              <2>spring配置文件 (applicationContext_dao.xml和applicationContext_service.xml)
              <3>mybatis配置文件
              <4>数据库的属性配置文件（jdbc.properties）
              <5>新建SqlMapConfig.xml进行分页插件的配置
        ⑥使用【逆向工程】生成pojo和mapper文件
        ⑦开发业务逻辑层，实现登陆判断
        ⑧开发控制器类AdminAction,完成登陆处理
        ⑨改造页面，发送登录请求，验证登录请求