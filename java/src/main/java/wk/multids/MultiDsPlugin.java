package wk.multids;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class})})
public class MultiDsPlugin implements Interceptor {

    protected static final Logger logger = LoggerFactory.getLogger(MultiDsPlugin.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DataSource annotation = invocation.getMethod().getAnnotation(DataSource.class);
        if (annotation != null) {
            String key = annotation.value();
            MultiDsHolder.set(key);
        } else {
            //没配的话就用默认的
            MultiDsHolder.set(MultiDsHolder.defaultDsKey);
        }
        Object rst = null;
        try {
            rst = invocation.proceed();
        } catch (Exception e) {
            logger.error("mybatis 执行出错:", e);
        } finally {
            MultiDsHolder.remove();
        }
        return rst;
    }


    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }
}