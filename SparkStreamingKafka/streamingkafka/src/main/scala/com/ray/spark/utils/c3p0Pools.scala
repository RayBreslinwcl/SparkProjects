package com.ray.spark.utils

import com.mchange.v2.c3p0.ComboPooledDataSource

object c3p0Pools {

  private val dataSource=new ComboPooledDataSource("c3p0.properties")

  def getDataSource=dataSource

  /**
    * 对外提供连接，实现事务
    * @return
    */
  def getConnection=dataSource.getConnection()


  def main(args: Array[String]): Unit = {

    val connection=c3p0Pools.getConnection

    val restSet= connection.prepareStatement("select * from DEPT")



  }
}
