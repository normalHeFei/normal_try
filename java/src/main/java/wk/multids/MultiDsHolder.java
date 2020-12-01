package wk.multids;

import org.springframework.core.NamedThreadLocal;

/**
 * @author: fei.he
 */
public class MultiDsHolder {
    public final static String defaultDsKey = "default";

    private final static ThreadLocal<String> holder = new NamedThreadLocal("db source thread local");

    public static String get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }

    public static void set(String ds) {
        holder.set(ds);
    }
}
