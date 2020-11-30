package trysth.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author hf
 * @date 18-11-5
 */
public interface DynamicDao {

    @UpdateProvider(type = trysth.mybatis.UpdateProvider.class,method = "updateSqlWithSimpleParam")
    int updateWithSimpleParam(@Param("mobile") String mobile);

    @UpdateProvider(type = trysth.mybatis.UpdateProvider.class,method = "updateSqlWithBeanParam")
    int updateWithBean(@Param("bean") UpdateBean bean);
}
