package trysth.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import java.util.List;

/**
 * Created by hefei on 2017/6/28.
 * 临时节点：生命周期和客户端回话绑定，客户端没有连接了，临时节点就会被删除，可以用该特征做心跳检查
 */
public class ZkTest {
    //149.129.215.224:2181
    @Test
    public void zkOps() {
        ZkClient zkClient = new ZkClient("149.129.215.224");
        Object o = zkClient.readData("/dubbo");
        //List<String> children1 = zkClient.getChildren("/jobCenter");
        List<String> children2 = zkClient.getChildren("/zookeeper");
        List<String> children3 = zkClient.getChildren("/newretaildev");
        List<String> children4 = zkClient.getChildren("/dubbo");
        List<String> children5 = zkClient.getChildren("/disconf");
        List<String> children6 = zkClient.getChildren("/newretailqafc");
        System.out.println(children6);

    }
}
