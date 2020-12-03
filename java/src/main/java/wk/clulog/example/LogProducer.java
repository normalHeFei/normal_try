package wk.clulog.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wk.clulog.TraceId;
import wk.clulog.TraceIdHolder;

import java.util.concurrent.TimeUnit;

/**
 * @author: fei.he
 */
public class LogProducer {

    public static final Logger logger = LoggerFactory.getLogger(LogProducer.class);

    private static void log() {
        logger.info(TraceIdHolder.get().toString() + "\t xxx");
//        logger.info("xxxd ");
    }

    public static void main(String[] args) throws InterruptedException {
        myLog();
    }

    private static void myLog() {
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread("thread" + finalI) {
                @Override
                public void run() {
                    for (; ; ) {
                        if (finalI / 2 == 0) {
                            TraceIdHolder.set(new TraceId("even url"));
                        } else {
                            TraceIdHolder.set(new TraceId("odd url"));
                        }
                        log();
//                        TraceIdHolder.remove();
                        try {
                            TimeUnit.SECONDS.sleep(1L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }


}


