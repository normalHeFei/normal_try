package wk.clulog;

import java.util.Objects;

/**
 * @author: fei.he
 */
public class TraceId {
    public final static String key = "traceId";

    private String url;
    private String biz;

    public TraceId(String url, String biz) {
        this.url = url;
        this.biz = biz;
    }

    public TraceId(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return new StringBuffer(biz).append("_").append(url).toString();
    }

    public static TraceId valueOf(String traceId) {
        String[] items = traceId.split("_");
        return new TraceId(items[0], items[1]);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        TraceId traceId = (TraceId) object;
        return url.equals(traceId.url) &&
                biz.equals(traceId.biz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, biz);
    }
}
