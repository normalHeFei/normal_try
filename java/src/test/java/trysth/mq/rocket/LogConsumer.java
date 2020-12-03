package trysth.mq.rocket;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import wk.clulog.example.Constrains;

import java.util.List;

/**
 * @author: fei.he
 */
public class LogConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Constrains.group);
        consumer.setNamesrvAddr("192.168.103.3:9876");
        consumer.subscribe(Constrains.topic,"*");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                //
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody()));
                }
                MessageQueue queue = context.getMessageQueue();
                System.out.println(queue);

                System.out.println();
                System.out.println();
                System.out.println();
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }
}
