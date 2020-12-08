package wk.clulog.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wk.clulog.TraceId;
import wk.clulog.TraceIdHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: fei.he
 */
public class LogProducer {

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    public static void main(String[] args) throws InterruptedException {
        ControllerMethodsAccess multiThreadAccessed = new ControllerMethodsAccess(Arrays.asList(
                new UrlCounter("/xxx/url", new AtomicInteger()),
                new UrlCounter("/yyy/url", new AtomicInteger()),
                new UrlCounter("/zzz/url", new AtomicInteger())));
        for (; ; ) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    multiThreadAccessed.access();
                }
            });
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }


}

class ControllerMethodsAccess {
    public static final Logger logger = LoggerFactory.getLogger(ControllerMethodsAccess.class);

    private List<UrlCounter> urlCounters;

    private Random random = new Random();

    public ControllerMethodsAccess(List<UrlCounter> urlCounters) {
        this.urlCounters = urlCounters;
    }

    public void access() {
        int randomIdx = random.nextInt(urlCounters.size());
        String url = urlCounters.get(randomIdx).getUrl();
        AtomicInteger counter = urlCounters.get(randomIdx).getCounter();
        TraceIdHolder.set(new TraceId(url));
        logger.info("thread: {} access {}  {} times", Thread.currentThread().getName(), url, counter.get());
        counter.getAndIncrement();
        TraceIdHolder.remove();
    }
}

class UrlCounter {
    private String url;
    private AtomicInteger counter;

    public UrlCounter(String url, AtomicInteger counter) {
        this.url = url;
        this.counter = counter;
    }

    public String getUrl() {
        return url;
    }

    public AtomicInteger getCounter() {
        return counter;
    }
}

