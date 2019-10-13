**rocketMq**

1.几个概念：  
nameSever： 一个目录服务，主要提供1.broker管理; 2.路由信息管理，client(consumer)发送msg到broker之前，会从nameServer中获取路由信息，发送信息时根据路由信息发送msg  
broker： 负责消息的分发，存储，查询， 高可用(主从异步复制)  
client(consumer/producter)： 没什么好讲的。   

部署架构：refer from https://rocketmq.apache.org/docs/cluster-deployment/
name server 集群部署，broker 主备或主主， 包含同一个broker name的broker 又可以作为一个broker group



