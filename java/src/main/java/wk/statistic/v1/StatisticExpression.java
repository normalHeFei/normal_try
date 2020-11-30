package wk.statistic.v1;

/**
 * @author: fei.he
 */
public interface StatisticExpression {

    String getValue(StatisticContext context);

    <T> T getValue(Class<T> clazz, StatisticContext context);


}
