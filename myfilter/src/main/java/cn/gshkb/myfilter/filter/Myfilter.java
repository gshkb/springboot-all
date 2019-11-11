package cn.gshkb.myfilter.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 自定义过滤器
 *
 * @author hkb
 * @version v1.0
 * @date 2019-11-11-21:17
 */

@WebFilter(filterName = "myFilter", urlPatterns = "/")
@Component
public class Myfilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化过滤器!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器执行" + servletRequest.getRemoteAddr());
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁!");
    }
}
