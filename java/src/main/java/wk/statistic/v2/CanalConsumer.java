package wk.statistic.v2;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;

/**
 * @author: fei.he
 */
public class CanalConsumer {

    public static void main(String[] args) {
        CanalConnector connector = CanalConnectors.newClusterConnector("192.168.103.3:2181", "example", "", "");
        connector.subscribe();

    }
}
