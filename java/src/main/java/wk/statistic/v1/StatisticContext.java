package wk.statistic.v1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author: fei.he
 * key: 统计项 bean alias
 * value: 计算所需的参数列表
 */
public class StatisticContext extends HashMap<String, List<Object>> {


    public void addArg(String factorName, Object arg){
        List<Object> args = this.get(factorName);
        if (args == null) {
            args = Arrays.asList(arg);
            put(factorName, args);
        }
        args.add(arg);
    }

}
