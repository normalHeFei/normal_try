package wk.statistic.v1;

import java.lang.annotation.*;

/**
 * @author: fei.he
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatisticItem {

    /**
     * spel 表达式
     * @return
     */
    String expression();


    /**
     * StatisticItem标记字段所属当前对象引用字段名.
     * 用于表达式中引用的 StatisticFactor 计算参数.
     * @return
     */
    String refFieldName() default "";
}
