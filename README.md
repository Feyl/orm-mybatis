# orm-mybatis

This is a simplified version of the MyBatis framework.

**achievement：**

* A single object /List collection query operation based on zero or single parameters of an XML configuration file
* Add, delete, update, and other operations based on individual parameters of the XML configuration file (which can be object types wrapped in primitive data types)
* Add, delete, update, query for a single object, etc., based on any parameters that are passed sequentially or out of order using @Param (return value is object type)

**technique：**

* Annotations, reflection (dynamic proxy), dom4j, regular expressions, database, singletons (lazy/double-check lock), JUnit, 

  JUC collection classes and mapping classes

Some improvements were made on a simplified version of the framework written by an upperclassman on GitHub.

See the code for details of specific improvements.

The repository records the UML class diagrams made in the process of learning the code of senior students, the notes written in the process of understanding, the websites searched when they encountered problems in the process of improving themselves, and the SQL files of the database used in testing the code.

If you want to learn more, it is recommended to read the original address of the senior's introduction to his framework, readme.md (which records the senior's thinking process of writing the framework), and then come back to the source code.

I hope it's helpful to you.

If you find any mistakes during your reading, please mention them. Thank you very much.

[primary address](https://github.com/chenxingxing6/myorm)

这是一个简化版的 Mybatis框架。

实现：

* 基于 XML配置文件的零个或单个参数的 单个对象/List集合查询 操作
* 基于 XML配置文件的单个参数（可以为对象类型/基本数据类型包装后的对象类型）的添加、删除、更新等操作
* 基于 注解的任意参数（按顺序对应传参或使用@Param可以不按顺序传参）（返回值为对象类型）的 添加、删除、更新、单个对象查询等操作

技术：

注解、反射（动态代理）、dom4j、正则表达式、数据库、单例模式（懒汉式/双重校验锁）、单元测试、JUC集合类和映射类

在GitHub上的一位学长手写的简化版框架之上做了部分改进。

具体改进的细节，详见代码。

仓库中记录了参考学习学长代码过程中所做的 UML类图、理解过程中写的笔记、自己改进过程中遇到问题时搜索的网站、测试代码时所用的数据库的 sql文件。

如果你想深入学习，建议阅读原版地址学长对其框架的介绍 README.md（记录了学长写框架的思考过程），再回来看这个源码。

希望对你有所帮助。

如果在阅读过程中发现错误，请您提出，非常感谢。

[原版地址连接](https://github.com/chenxingxing6/myorm)

