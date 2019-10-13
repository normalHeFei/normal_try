package com.normal.trysth.transcation.distributeddemo.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.normal.trysth.transcation.distributeddemo.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hf
 * @date 18-11-11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Reference(url = "dubbo://localhost:12345")
    private InventoryService inventoryService;

    @Override
    @Transactional
    public int addOrder(String productId, Integer amount) {
        int count = orderMapper.addOrder(productId, amount);
        if (count > 0) {

            return inventoryService.decrease(productId, amount);
        }
        throw new RuntimeException("unknown error");
    }


}
