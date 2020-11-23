# 一、相关依赖

## 1.kafka

```xml
    <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka-clients</artifactId>
      <version>0.10.0.0</version>
    </dependency>
    <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka_2.11</artifactId>
      <version>1.1.0</version>
    </dependency>
```





# 二、kafka生产者和消费者

https://www.bilibili.com/video/BV16t411G7jZ/?spm_id_from=333.788.recommend_more_video.0

## 1.scala代码

参考：[Scala调用Kafka的生产者和消费者Demo，以及一些配置参数整理](https://www.cnblogs.com/lys_013/p/11957479.html)

相关依赖

```xml
    <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka-clients</artifactId>
      <version>0.10.0.0</version>
    </dependency>
    <dependency>
      <groupId>org.apache.kafka</groupId>
      <artifactId>kafka_2.11</artifactId>
      <version>1.1.0</version>
    </dependency>
```



### 1.1 producer

针对topic是wc

```scala
package com.ray.spark.kafkaClient

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

/**
  * 实现producer
  */
object KafkaProducerDemo {
  def main(args: Array[String]): Unit = {
    val prop = new Properties
    // 指定请求的kafka集群列表
    prop.put("bootstrap.servers", "hadoop:9092")// 指定响应方式
    //prop.put("acks", "0")
    prop.put("acks", "all")
    // 请求失败重试次数
    //prop.put("retries", "3")
    // 指定key的序列化方式, key是用于存放数据对应的offset
    prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // 指定value的序列化方式
    prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // 配置超时时间
    prop.put("request.timeout.ms", "60000")
    //prop.put("batch.size", "16384")
    //prop.put("linger.ms", "1")
    //prop.put("buffer.memory", "33554432")

    // 得到生产者的实例
    val producer = new KafkaProducer[String, String](prop)

    // 模拟一些数据并发送给kafka
    for (i <- 1 to 10) {
      val msg = s"${i}: this is a linys ${i} kafka data"
      println("send -->" + msg)
      // 得到返回值
      val rmd: RecordMetadata = producer.send(new ProducerRecord[String, String]("wc", msg)).get()
      println(rmd.toString)
      Thread.sleep(500)
    }

    producer.close()
  }
}
```



### 1.2 consumer

consumer针对wc

```scala
package com.ray.spark.kafkaClient

import java.util.{Collections, Properties}
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
/**
  * 实现consumer
  */
object KafkaConsumerDemo {
  def main(args: Array[String]): Unit = {
    // 配置信息
    val prop = new Properties
    prop.put("bootstrap.servers", "hadoop:9092")
    // 指定消费者组
    prop.put("group.id", "group01")
    // 指定消费位置: earliest/latest/none
    prop.put("auto.offset.reset", "earliest")
    // 指定消费的key的反序列化方式
    prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    // 指定消费的value的反序列化方式
    prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    prop.put("enable.auto.commit", "true")
    prop.put("session.timeout.ms", "30000")
    // 得到Consumer实例
    val kafkaConsumer = new KafkaConsumer[String, String](prop)
    // 首先需要订阅topic
    kafkaConsumer.subscribe(Collections.singletonList("wc"))
    // 开始消费数据
    while (true) {
      // 如果Kafak中没有消息，会隔timeout这个值读一次。比如上面代码设置了2秒，也是就2秒后会查一次。
      // 如果Kafka中还有消息没有消费的话，会马上去读，而不需要等待。
      val msgs: ConsumerRecords[String, String] = kafkaConsumer.poll(2000)
      // println(msgs.count())
      val it = msgs.iterator()
      while (it.hasNext) {
        val msg = it.next()
        println(s"partition: ${msg.partition()}, offset: ${msg.offset()}, key: ${msg.key()}, value: ${msg.value()}")
      }
    }
  }
}
```

### 1.3 kafka开启消费者shell显示

```sh
[root@hadoop kafka_2.11-0.10.0.1]# bin/kafka-console-consumer.sh --zookeeper hadoop:2181/kafka10_01 --topic wc --from-beginning

fsd sdf sdf er
sdfs dfsdf
1: this is a linys 1 kafka data
2: this is a linys 2 kafka data
...
```



## 2.java代码

