package trysth.transcation.distributeddemo.order;

/**
 * @author hf
 * @date 18-11-11
 */
public interface OrderService {
    int addOrder(String productId, Integer amount);
}
