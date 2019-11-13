package cn.gshkb.myfilter.configration;

import cn.gshkb.myfilter.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器配置项
 *
 * @author hkb
 * @create 2019-11-12 16:28 v1.0
 **/
@Configuration
public class InterceptorConfigration extends WebMvcConfigurationSupport {

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		//注册自己的拦截器,并设置拦截路径，拦截多个可以全一个list集合
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/");
	}
}
