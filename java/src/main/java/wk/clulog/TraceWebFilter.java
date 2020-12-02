package wk.clulog;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author: fei.he
 */
public class TraceWebFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contextPath = request.getServletContext().getContextPath();

        TraceId traceId = new TraceId(contextPath, "biz");
        try {
            TraceIdHolder.set(traceId);
            //其他filter 和 servlet
            chain.doFilter(request, response);

        } finally {
            TraceIdHolder.remove();
        }

    }

}
