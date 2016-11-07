# 快速开发MVC项目的样板工程
spring mvc 4 + spring  data for mongodb +spring data  for redis + shiro + log4j + sitemesh + Admin LTE(html template) 搭建的样板工程，前台实现基础的权限管理功能，本地文件存ftp服务器，支持存储文件到七牛云
todo： redis集成，数据库初始化脚本


generated-sources为querydsl生成的java源码文件，使用eclipse时，需要将这个文件夹加入源码管理，运行插件
mvn eclipse:eclipse即可

在tomcat8运行不起来，可能是新的tomcat对el表达式校验更严格，因为项目中我是用了static这个关键字作为表达式如${static}，要工作起来，catalina.properties中添加如下两行
org.apache.jasper.compiler.Parser.STRICT_QUOTE_ESCAPING=false
org.apache.el.parser.SKIP_IDENTIFIER_CHECK=true

本项目不再维护，迁移至码市了。
