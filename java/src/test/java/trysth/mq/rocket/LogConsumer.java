package trysth.mq.rocket;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import wk.clulog.example.Constrains;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: fei.he
 */
public class LogConsumer {


    public static void main(String[] args) throws MQClientException {
        ConcurrentHashMap<MessageQueue, List<MessageExt>> queueMsgMap = new ConcurrentHashMap<>();

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Constrains.group);
        consumer.setNamesrvAddr("192.168.103.3:9876");
        consumer.subscribe(Constrains.topic, "*");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                System.out.println(Thread.currentThread().getName());
                MessageQueue messageQueue = context.getMessageQueue();
                List<MessageExt> messageExts = queueMsgMap.get(messageQueue);
                if (messageExts == null) {
                    queueMsgMap.putIfAbsent(messageQueue, new ArrayList<>());
                } else {
                    messageExts.addAll(msgs);
                }
                //每100打印,观察同一个队列里面日志是否有序
                if (messageExts.size() > 100) {
                    System.out.println("队列:" + messageQueue.toString() + " 接收消息如下");
                    for (MessageExt messageExt : messageExts) {
                        System.out.println(new String(messageExt.getBody()));
                    }
                    queueMsgMap.remove(messageQueue);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }


}
