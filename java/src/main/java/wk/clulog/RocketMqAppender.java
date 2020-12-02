package wk.clulog;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.status.ErrorStatus;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;

/**
 * @author: fei.he
 */
public class RocketMqAppender extends AppenderBase<ILoggingEvent> {

    /**
     * Message tag define
     */
    private String tag;

    /**
     * Whitch topic to send log messages
     */
    private String topic;

    /**
     * RocketMQ nameserver address
     */
    private String nameServerAddress;

    /**
     * Log producer group
     */
    private String producerGroup;

    /**
     * Log producer send instance
     */
    private MQProducer producer;

    private Layout layout;

    private final static MessageQueueSelector hashSelector = new SelectMessageQueueByHash();

    /**
     * Info,error,warn,callback method implementation
     */
    @Override
    protected void append(ILoggingEvent event) {
        if (!isStarted()) {
            return;
        }
        String logStr = this.layout.doLayout(event);
        try {
            Message msg = new Message(topic, tag, logStr.getBytes());
            //Send message and do not wait for the ack from the message broker.
            //同一个traceId 对应同一个队列
            producer.sendOneway(msg, hashSelector, TraceIdHolder.get());
        } catch (Exception e) {
            addError("Could not send message in RocketmqLogbackAppender [" + name + "]. Message is : " + logStr, e);
        }
    }

    /**
     * Options are activated and become effective only after calling this method.
     */
    @Override
    public void start() {
        int errors = 0;

        if (this.layout == null) {
            addStatus(new ErrorStatus("No layout set for the RocketmqLogbackAppender named \"" + name + "\".", this));
            errors++;
        }

        if (errors > 0 || !checkEntryConditions()) {
            return;
        }
        try {
            producer = ProducerInstance.getProducerInstance().getInstance(nameServerAddress, producerGroup);
        } catch (Exception e) {
            addError("Starting RocketmqLogbackAppender [" + this.getName()
                    + "] nameServerAddress:" + nameServerAddress + " group:" + producerGroup + " " + e.getMessage());
        }
        if (producer != null) {
            super.start();
        }
    }

    /**
     * When system exit,this method will be called to close resources
     */
    @Override
    public synchronized void stop() {
        // The synchronized modifier avoids concurrent append and close operations
        if (!this.started) {
            return;
        }

        this.started = false;

        try {
            ProducerInstance.getProducerInstance().removeAndClose(this.nameServerAddress, this.producerGroup);
        } catch (Exception e) {
            addError("Closeing RocketmqLogbackAppender [" + this.getName()
                    + "] nameServerAddress:" + nameServerAddress + " group:" + producerGroup + " " + e.getMessage());
        }

        // Help garbage collection
        producer = null;
    }

    protected boolean checkEntryConditions() {
        String fail = null;

        if (this.topic == null) {
            fail = "No topic";
        }

        if (fail != null) {
            addError(fail + " for RocketmqLogbackAppender named [" + name + "].");
            return false;
        } else {
            return true;
        }
    }

    public Layout getLayout() {
        return this.layout;
    }

    /**
     * Set the pattern layout to format the log.
     */
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setNameServerAddress(String nameServerAddress) {
        this.nameServerAddress = nameServerAddress;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }
}
