package trysth.transcation.distributeddemo.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hf
 * @date 18-11-13
 */
@SpringBootApplication
@PropertySource("classpath:application-inventory.properties")
public class InventoryMain {
    public static void main(String[] args) {
        SpringApplication.run(InventoryMain.class, args);
    }
}
