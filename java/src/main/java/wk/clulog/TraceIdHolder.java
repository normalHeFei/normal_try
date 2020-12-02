package wk.clulog;

/**
 * @author: fei.he
 */
public class TraceIdHolder {

    private final static ThreadLocal TraceIdLocal = new ThreadLocal();

    public static TraceId get() {
        return (TraceId) TraceIdLocal.get();
    }

    public static void remove() {
        TraceIdLocal.remove();
    }

    public static void set(TraceId value) {
        TraceIdLocal.set(value);
    }
}
