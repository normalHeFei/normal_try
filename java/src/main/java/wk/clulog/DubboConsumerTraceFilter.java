package wk.clulog;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * @author: fei.he
 */
@Activate(group = Constants.CONSUMER)
public class DubboConsumerTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        invocation.getAttachments().put(TraceId.key, TraceIdHolder.get().toString());
        return invoker.invoke(invocation);
    }
}
