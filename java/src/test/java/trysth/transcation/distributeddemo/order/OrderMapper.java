package trysth.transcation.distributeddemo.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author hf
 * @date 18-11-12
 */
@Mapper
public interface OrderMapper {

    @Insert("insert into `order`(product_id,amount) values(#{productId},#{amount})")
    int addOrder(@Param("productId") String productId, @Param("amount") Integer amount);


}
