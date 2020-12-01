package trysth.transcation.distributeddemo.inventory;

/**
 * @author hf
 * @date 18-11-11
 */
public interface InventoryService {
    int decrease(String productId, Integer amount);

}
