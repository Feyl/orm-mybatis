### 程序运行流程

​		程序启动，将存放`.xml`配置文件的目录的地址传递给`SqlSessionFactoryBuilder`，通过`builder`创建`SqlSessionFactory`对象**（反射机制）**。

​		调用创建的会话工厂对象的 build方法，并将存放 `.xml`配置文件的目录地址作为参数传递给build方法，存储在会话工厂对象属性中，如果此时会话工厂对象的属性内的 classLoader未被赋值，那么将系统类加载器赋值给属性 classLoader。

​		通过调用工厂对象的 getMapper(Class clazz)方法，根据Mapper接口的Class对象获取 Mapper接口的实例对象**（动态代理）**。在此过程中，会调用工厂对象的 getSqlSession方法，getSqlSession 方法 如果其属性 Map<String, Function> functionMap为null，会调用 init 方法将属性 Map<String, Function> functionMap、List\<SqlSession> sqlSessions、List\<String> mapperxmls 初始化；如果不存在可用的 SqlSession对象，会再 List\<SqlSession> sqlSessions存储的 SqlSession对象数量少于最大数量值` maxConnects = 100`的情况下，调用 createSqlSession 方法创建`incrementCount = 10`或 `maxConnects - sqlSessions.size()` 数量的 SqlSession对象；返回可用的 SqlSession对象。并将返回的对象实例返回给 MapperProxy的构造方法作为属性值传入。

​		init方法中 同时会调用doScanClass 方法 来根据存放mapper.xml文件的目录 获取所有 mapper.xml的文件的路径，并添加到属性List\<String> mapperxmls中，和 initMapper方法 判断是否存在.xml配置文件，如果存在.xml配置文件，则调用getMapper(String xmlPath) 将.xml配置文件的信息封装到Function对象中并进行存储

​		将 MapperProxy作为动态代理方法的第三个 InvocationHandler实例传入 invoke方法。invoke方法中会调用其属性内 sqlSession的 run方法的返回值。

​		sqlSession的 run方法标记当前会话对象已在使用，并调用属性中存储 executor的run方法。

​		后续详细操作参见以下对应内容。

### 类及其功能

**SqlSessionFactoryBuilder：**

```java
public SqlSessionFactory build(String packageName)
```

* 创建会话工厂对象（最终返回）

* 通过调用工厂对象的build方法将存放`.xml`的目录地址传递给工厂对象（赋值给其属性）

  这个过程有可能给工厂对象存储类加载器的属性赋值（如果之前没赋值）

**SqlSessionFactory：**

```java
private SqlSessionFactory(){}
public static SqlSessionFactory getInstance()
private static synchronized SqlSessionFactory createInstance()//创建 SqlSessionFactory实例并维持实例单一
```

将 SqlSessionFactory的构造方法设为私有方法（在类外部不可以通过 new 来调用类的够着方法创建对象实例）

```java
public SqlSession getSqlSession()
```

* 如果其属性 Map<String, Function> functionMap为null，会调用 init方法将属性 Map<String, Function> functionMap、List\<SqlSession> sqlSessions、List\<String> mapperxmls 初始化
* 如果不存在可用的 SqlSession对象，会再 List\<SqlSession> sqlSessions存储的 SqlSession对象数量少于最大数量值` maxConnects = 100`的情况下，调用 createSqlSession 方法创建`incrementCount = 10`或 `maxConnects - sqlSessions.size()` 数量的 SqlSession对象
* 返回可用的 SqlSession对象

```java
private void init()
```

* 初始化属性 Map<String, Function> functionMap、List\<SqlSession> sqlSessions、List\<String> mapperxmls
* 调用 doScanClass(mapperPackage)方法 根据存放mapper.xml文件的目录 获取所有 mapper.xml的文件的路径，并添加到属性List\<String> mapperxmls中
* 调用 initMapper方法 判断是否存在.xml配置文件，如果存在.xml配置文件，则调用getMapper(String xmlPath) 将.xml配置文件的信息封装到Function对象中并进行存储

```java
private void doScanClass(String mapperPackage)
```

* 根据存放mapper.xml文件的目录 获取所有 mapper.xml的文件的路径，并添加到属性List\<String> mapperxmls中

```java
private void initMapper()
```

* 判断是否存在.xml配置文件，如果存在.xml配置文件，则调用getMapper(String xmlPath) 将.xml配置文件的信息封装到Function对象中并进行存储

```java
private void createSqlSession(int count)
```

* 若当前工厂属性 List\<SqlSession> sqlSessions存储的 SqlSession对象数量大于规定的最大数量值` maxConnects = 100`，则会抛出异常
* 根据传入的参数创建一定数量的 SqlSession对象 并存入 List\<SqlSession> sqlSessions中

```java
public Executor getExecutor(SqlSession sqlSession)
```

**MapperProxy：**

SqlSession类的代理类

```java
public class MapperProxy implements InvocationHandler
```

```java
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
```

* 返回 调用其属性内 sqlSession的 run方法的返回值

  [InvocationHandler中invoke方法中的第一个参数proxy的用途](https://blog.csdn.net/bu2_int/article/details/60150319)

  [Java中InvocationHandler接口中第一个参数proxy详解](https://blog.csdn.net/yaomingyang/article/details/81040390)

  [Java - 动态代理机制讲解（Proxy.newProxyInstance）](https://www.shangmayuan.com/a/3e45af02f9274c5ab741ea41.html)

**SqlSession：**

```java
public SqlSession(SqlSessionFactory factory, Connection connection, boolean isUse) 
```

* 初始化 SqlSessionFactory sqlSessionFactory、Connection connection、Executor executor、boolean isUse 属性域
* 初始化 Executor实例时会调用属性 sqlSessionFactory的 getExecutor方法

```java
public <T> T run(Method method, Object[] args)
```

* 将当前会话对象标记为已在使用
* 返回 调用其属性内 executor的 run方法的返回值

**Executor：**

```java
public <T> T run(Method method, Object[] args, Connection connection)
```

* 对Mapper接口中的方法的注解进行判断（是否为@Select @Insert @Delete @Update）中的一个，注解多于一个会抛出异常

* 若方法中的注解个数为一个，根据注解的类型和值（@Select / @Insert / @Delete / @Update）以及被调用方法的信息（方法名、返回值类型）将mapper接口中方法的相关信息封装到 Function实例中，并将实例添加到 Map<String, Function> functionMap中

* 根据被调用的方法名，从 Map<String, Function> functionMap中获取 Function实例，若获取不到则手动抛出异常

* 调用 getParamsMap(method, args) 被调用方法的传入的参数，封装到 Map<String, Object> param中并返回添加到 封装方法Function实例中

* 根据传入的参数是对象还是基本数据类型**（正则表达式）**以及是否被@Param注解标注，来对 Function实例中属性 Map<String, Object> param存储的参数进行进一步处理并存入List\<Object> parseArgs中

* 对 Function实例中 sql属性 做进一步处理，将`#{.+?}`转换为`?`

* 调用 isQuery方法判断 Function实例中 sqlType属性调用 JdbcUtil中的 execute方法或query方法执行sql语句，并得到查询结果。

  如果是sql查询语句，需要根据Function实例中 resultType属性 调用handlerResult对查询出的结果集进一步封装，最后返回封装后的对象

```java
private Map<String, Object> getParamsMap(Method method, Object[] args)
```

* 将被调用方法的传入的参数，封装到 Map<String, Object> param中并返回

  > 如果@Param注解存在，键为@Param注解的值，值为传入的对应的参数值
  >
  > 如果@Param注解不存在，键为参数下标的字符串形式，值为传入的对应的参数值

  [反射中，Method.getParameterAnnotations(）用法](https://blog.csdn.net/qq_27397913/article/details/102475475)

```java
private boolean isQuery(String sqlType)
```

* 判断被调用方法注解的值（在 Function实例 sqlType属性中存储）是否为查询sql

```java
private Object handlerResult(ResultSet rs, Class<?> clazz)
```

* 根据Class类型对结果集数据进行处理封装

* 处理过程中会调用 lineToHump方法将数据库中字段名中的下划线命名格式转换为JavaBean中的驼峰命名格式

  [Jdbc系列六：ResultSetMetaData类](https://blog.csdn.net/lizhiqiang1217/article/details/90549424)

```java
public static String lineToHump(String str)
```

* 将下划线命名格式转换为驼峰命名格式**（正则表达式）**

  [Java Matcher类appendReplacement和appendTail方法的区别](https://blog.csdn.net/xyajia/article/details/80829163)

  [java正则表达式Matcher.appendReplacement与appendTail](https://blog.csdn.net/weixin_45566322/article/details/106289159)

**JdbcUtil:**

```java
public static ResultSet query(String sql, List<Object> params, Connection c)
```

* @Select
* 需要根据 Function实例中 resultType属性 调用handlerResult对查询出的结果集进一步封装，返回封装后的对象

```java
public static Integer execute(String sql, List<Object> params, Connection c)
```

* @Insert @Delete @Update
* 根据参数中传入的 sql语句和参数列表执行 sql语句，并返回执行 sql语句影响的行数
* 需要将 JDK中的数据类型转换为对应的数据库可以处理的数据类型，调用 typeof方法

> 传入参数通过PreparedStatement执行sql
> 没传入参数通过Statement执行sql

```java
private static Object typeof(Object o)
```

* 将 JDK中的数据类型转换为对应的数据库可以处理的数据类型

  例如：将 java.util.Date 转成 java.sql.Date

  ​			将 Character 或 char 变成 String

### 相关博客

[MyBatis 的源码中的核心类有哪些？如何实现框架功能的？](https://blog.csdn.net/meism5/article/details/109061644)

弱引用

[ResourceBundle和properties 读取配置文件区别](https://my.oschina.net/u/2349605/blog/670506)

[读取properties配置文件的几种方式总结](https://www.jianshu.com/p/f650cf1fe59f)

[单例模式](https://github.com/geekxh/hello-algorithm/blob/master/%E4%B8%93%E6%A0%8F/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F.md)

[编程变量命名规则及编程单词缩写字典](https://cloud.tencent.com/developer/article/1456096)

[isAnnotationPresent()方法](https://blog.csdn.net/qq_41084324/article/details/83787052)

[三.多线程JUC篇-3.26 ConcurrentSkipListMap](https://blog.csdn.net/weixin_42868638/article/details/112269387)

[jdbc中获取resultset的大小](https://blog.csdn.net/hacker_zhb/article/details/84240664)

[ResultSet: Exception: set type is TYPE_FORWARD_ONLY — why?](https://stackoverflow.com/questions/6367737)

[用Mybatis在mysql中,涉及sql语句中的基本运算报错的坑](https://blog.csdn.net/weixin_41907291/article/details/83509988)

[Java中 LocalDate、LocalTime、LocalDateTime三个时间工具类的使用介绍](https://blog.csdn.net/qq_24754061/article/details/95500209)
