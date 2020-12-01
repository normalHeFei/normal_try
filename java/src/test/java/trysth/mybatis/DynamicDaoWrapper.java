package trysth.mybatis;

import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * @author hf
 * @date 18-11-5
 */
public class DynamicDaoWrapper implements GenericService {

    private DynamicDao dynamicDao;

    public int updateWithSimpleParam(String mobile) {
        return dynamicDao.updateWithSimpleParam(mobile);
    }

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        if (method.equals("updateWithSimpleParam")) {
            return updateWithSimpleParam((String) args[0]);
        }
        throw new IllegalArgumentException("unknow invoked method");
    }
}
