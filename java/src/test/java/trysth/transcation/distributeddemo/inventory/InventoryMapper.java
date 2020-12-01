package trysth.transcation.distributeddemo.inventory;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author hf
 * @date 18-11-12
 */
@Mapper
public interface InventoryMapper {

    @Update("update inventory set total_inventory = total_inventory - #{amount} where product_id = #{productId}")
    int decreaseAmount(@Param("productId") String productId, @Param("amount") Integer amount);
}
