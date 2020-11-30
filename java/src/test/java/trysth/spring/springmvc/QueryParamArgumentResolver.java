package trysth.spring.springmvc;

import trysth.objs.Obj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * Created by hefei on 2017/8/26.
 * 查询参数解析器
 */
public class QueryParamArgumentResolver implements HandlerMethodArgumentResolver {
    private static Logger logger = LoggerFactory.getLogger(QueryParamArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryParam.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Class<?> paramType = parameter.getParameterType();

        if (MultiValueMap.class.isAssignableFrom(paramType)) {
            throw new UnsupportedOperationException("通用查询参数map不支持同一个参数key对应多个参数值");
        }
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        WebDataBinder binder = binderFactory.createBinder(webRequest, new Obj(), "obj");
        BindingResult bindingResult = binder.getBindingResult();
        Object sth = bindingResult.getTarget();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

        }
        return "some value";
    }
}
