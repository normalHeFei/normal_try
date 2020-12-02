package wk.clulog;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * @author: fei.he
 */
@Activate(group = Constants.PROVIDER)
public class DubboProvideTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceIdStr = invocation.getAttachments().get(TraceId.key);

        if (traceIdStr == null) {
            return new RpcResult("服务端未找到TradeId");
        }

        TraceId traceId = TraceId.valueOf(traceIdStr);
        /**
         * 服务端重新设置 traceId,用于日志打印
         */
        TraceIdHolder.set(traceId);
        Result rst = null;
        try {
            rst = invoker.invoke(invocation);
        } finally {
            TraceIdHolder.remove();
        }
        return rst;
    }
}
