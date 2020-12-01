package wk.cluconf.solve;

import org.springframework.context.ApplicationListener;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: fei.he
 */
public class AutoUpdatePropertySource extends PropertySource<Map<String, Object>> implements ApplicationListener<PropertyUpdateParam> {

    public AutoUpdatePropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }


    @Override
    public void onApplicationEvent(PropertyUpdateParam event) {
        int type = event.getType();
        if (type == 0) {
            source.put(event.getKey(), event.getValue());
            return;
        }
        if (type == 1) {
            source.put(event.getKey(), event.getValue());
            return;
        }
        if (type == 2) {
            source.remove(event.getKey());
            return;
        }

        logger.warn("event type unknow \t" + type);
    }
}
