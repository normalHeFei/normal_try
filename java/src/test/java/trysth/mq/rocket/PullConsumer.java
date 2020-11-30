package trysth.mq.rocket;

import trysth.MixAll;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.MessageQueueListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Set;

/**
 * @author hf
 * @date 18-9-26
 */
public class PullConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer();
        consumer.setNamesrvAddr("localhost:9876");
        consumer.setConsumerGroup(MixAll.CONSUMER_GROUP);
        consumer.registerMessageQueueListener(MixAll.TOPIC, new MessageQueueListener() {
            @Override
            public void messageQueueChanged(String topic, Set<MessageQueue> mqAll, Set<MessageQueue> mqDivided) {

            }
        });
        consumer.start();

    }
}
