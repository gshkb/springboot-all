package cn.gshkb.myfilter.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @author hkb
 * @version v1.0
 * @date 2019-11-11-21:57
 */
@Component
public class MyInterceptor implements WebRequestInterceptor {


    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        System.out.println("拦截预处理");
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
        System.out.println("拦截post处理");
    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
        System.out.println("拦截后处理");
    }
}
