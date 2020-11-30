package trysth.mq.rocket;

import trysth.MixAll;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(MixAll.PRODUCT_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.103.3:9876");
        //Launch the instance.
        producer.start();
        //Create a message instance, specifying topic, tag and message body.
        Message msg = new Message(MixAll.TOPIC /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        //Call send message to deliver message to one of brokers.
        //send synchronously
        //consumer should deal with duplication issues
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);
        //Shut down once the producer instance is not longer in use.
        //producer.shutdown();
    }
}