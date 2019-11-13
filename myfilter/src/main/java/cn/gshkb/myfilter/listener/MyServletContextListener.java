package cn.gshkb.myfilter.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author hkb
 * @create 2019-11-13 14:02 v1.0
 **/
@Component
@WebListener
public class MyServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContext创建");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContext销毁");
	}
}
