package wk.cluconf.solve;

import org.springframework.context.ApplicationEvent;

/**
 * @author: fei.he
 */
public class PropertyUpdateParam extends ApplicationEvent {

    /**
     * 0: insert
     * 1: update
     * 2: delete
     */
    private int type;


    private String key;

    private Object value;

    private PropertyUpdateParam(int type, String key, Object value) {
        super("PropertyUpdateParam");
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public static PropertyUpdateParam newAddParam(String key, Object value) {
        return new PropertyUpdateParam(0, key, value);
    }

    public static PropertyUpdateParam newUpdateParam(String key, Object value) {
        return new PropertyUpdateParam(1, key, value);
    }

    public static PropertyUpdateParam newDeleParam(String key) {
        return new PropertyUpdateParam(2, key, null);
    }

    public int getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
