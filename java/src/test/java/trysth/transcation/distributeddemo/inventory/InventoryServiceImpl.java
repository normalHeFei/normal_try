package trysth.transcation.distributeddemo.inventory;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hf
 * @date 18-11-13
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    @Transactional
    public int decrease(String productId, Integer amount) {
        int count = inventoryMapper.decreaseAmount(productId, amount);
        return count;
    }

}
