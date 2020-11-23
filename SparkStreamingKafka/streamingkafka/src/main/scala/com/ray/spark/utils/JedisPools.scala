package com.ray.spark.utils

import redis.clients.jedis.{JedisPool, JedisPoolConfig}


/**
  * redis连接池
  *
  */
object JedisPools {

  private val jedisPoolConfig=new JedisPoolConfig()
  jedisPoolConfig.setMaxTotal(2000)
  jedisPoolConfig.setMaxIdle(1000)
  jedisPoolConfig.setMinIdle(100)
  jedisPoolConfig.setTestOnBorrow(true)
  jedisPoolConfig.setTestOnReturn(true)

  private val jedisPool=new JedisPool(jedisPoolConfig,"192.168.0.8") //hadoop

  //获取jedis链接
  def getJedis=jedisPool.getResource


  def main(args: Array[String]): Unit = {
    val jedis=getJedis

    jedis.set("key1","value1")

    jedis.close()
  }


}
