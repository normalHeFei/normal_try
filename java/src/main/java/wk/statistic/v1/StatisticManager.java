package wk.statistic.v1;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author: fei.he
 */
public class StatisticManager {



    @PostConstruct
    public void registerFactors() {
    }

    /**
     * 获取单个统计项
     *
     * @param expression
     * @param args
     * @param <T>
     * @return
     */
    <T> T getOne(String expression, Map<String, Object> args) {

        return null;
    }


    /**
     * 填充单条记录统计项
     *
     * @param record
     * @param args
     */
    <T> void assemble(T record, Map<String, Object> args) {

    }

    /**
     * 填充列表统计项
     *
     * @param records
     * @param args
     * @param <T>
     */
    <T> void assemble(List<T> records, List<Map<String, Object>> args) {

    }


}
