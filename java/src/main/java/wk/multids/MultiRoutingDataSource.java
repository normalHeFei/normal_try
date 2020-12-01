package wk.multids;

import org.assertj.core.util.Maps;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

/**
 * @author: fei.he
 */
public class MultiRoutingDataSource extends AbstractRoutingDataSource {


    public MultiRoutingDataSource(Object defaultDataSource, Map<Object, Object> dataSources) {
        setDefaultTargetDataSource(defaultDataSource);
        Map<Object, Object> dses = Maps.newHashMap(MultiDsHolder.defaultDsKey, defaultDataSource);
        dses.putAll(dataSources);
        setTargetDataSources(dses);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return MultiDsHolder.get();
    }

}
