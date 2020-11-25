# 一、实现数据库连接



# 二、相关依赖

```xml
<!--    数据库连接池-->
    <dependency>
      <groupId>c3p0</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.1.2</version>
    </dependency>
<!--操作数据库封装-->
    <dependency>
      <groupId>commons-dbutils</groupId>
      <artifactId>commons-dbutils</artifactId>
      <version>1.7</version>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.47</version>
    </dependency>
```



# 三、c3p0相关配置文件c3p0.properties

参考[连接](https://blog.csdn.net/jdq928/article/details/84519141)，或者官网

```properties
c3p0.JDBC.url=jdbc:mysql://192.168.0.8:3306/sparkProjects?characterEncoding=utf8 
c3p0.DriverClass=com.mysql.jdbc.Driver 
c3p0.user=root 
c3p0.pwd=123456

c3p0.acquireIncrement=3 
c3p0.idleConnectionTestPeriod=60 
c3p0.initialPoolSize=10 
c3p0.maxIdleTime=60 
c3p0.maxPoolSize=20 
c3p0.maxStatements=100 
c3p0.minPoolSize=5 
```



# 三、代码



```scala
package com.ray.spark.utils

import com.mchange.v2.c3p0.ComboPooledDataSource

object c3p0Pools {

  private val dataSource=new ComboPooledDataSource()

  def getDataSource=dataSource

  /**
    * 对外提供连接，实现事务
    * @return
    */
  def getConnection=dataSource.getConnection()


  def main(args: Array[String]): Unit = {

    val connection=getConnection

    val restSet= connection.prepareStatement("select * from DEPT")



  }
}

```

