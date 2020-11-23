# 一、机器

## 1.虚拟机

hadoop：192.168.0.8

## 2.大数据环境

cdh5.7.0

## 3.Kafka环境

部署安装教程，参考[kafka（一）：单节点broker的部署和使用](https://blog.csdn.net/u010886217/article/details/82973573)

### （1）kafka版本和位置

版本：kafka_2.11-0.10.0.1

存储位置：/opt/modules/cdh5.7.0/kafka_2.11-0.10.0.1

### （2）broker配置

```sh
## 给定broker的id的值，在一个kafka集群中该参数必须唯一
broker.id=2

## 指定kafka存储磁盘的路径，可以使用","分割，给定多个磁盘路径；如果服务器挂载多个磁盘，可以将kafka的数据分布存储到不同的磁盘中(每个磁盘写一个路径)，对于Kafka的数据的读写效率有一定的提升（场景：高并发、大数据量的情况）
log.dirs=/opt/modules/cdh5.7.0/kafka_2.11-0.10.0.1/data/0 

## 配置kafka连接zk的相关信息，连接url以及kafka数据存储的zk根目录；这里的配置含义是：连接hadoop机器2181端口的zookeeper作为kafka的元数据管理zk，zk中使用/kafka08作为kafka元数据存储的根目录，默认kafka在zk中的根目录是zk的顶级目录("/")
zookeeper.connect=hadoop:2181/kafka10_01 

 
## 配置监听端口
listeners=PLAINTEXT://hadoop:9092

```

### （3）基本sh命令

#### 启动kafka

```
bin/kafka-server-start.sh config/server.properties
```

后台启动（亲测可以）

```
bin/kafka-server-start.sh config/server.properties 1>/dev/null 2>&1 &或者bin/kafka-server-start.sh -daemon config/server.properties &
```

#### 创建topic

```
kafka-topics.sh --create --zookeeper hadoop:2181/kafka10_01 --replication-factor 1 --partitions 1 --topic hello_topic
```

备注：针对kafka_2.11-0.10.1.1

```
# bin/kafka-topics.sh --zookeeper hadoop:2181/kafka10_01 --create --replication-factor 1 --partitions 1 --topic merchants-template
Created topic "merchants-template"
```

#### 查看所有topic

```
[root@hadoop bin]# ./kafka-topics.sh --list --zookeeper hadoop:2181/kafka10_01         
__consumer_offsets
dayu
```

#### 开启生产者和消费者



##### 针对kafka_2.11-0.10.1.1开启生产者和消费者

**创建topic**

```
bin/kafka-topics.sh --create --topic wc --zookeeper hadoop:2181/kafka10_01 --partitions 1 --replication-factor 1
```

（1）开启生产者

```
bin/kafka-console-producer.sh --broker-list hadoop:9092 --topic wc
```

（2）开启消费者

```
bin/kafka-console-consumer.sh --zookeeper hadoop:2181/kafka10_01 --topic wc --from-beginning
```

 

# 二、参考视频

## 1.[oldsheep Kafka入门-整合Spark Streaming-第二季](https://www.bilibili.com/video/BV114411G776?p=1)