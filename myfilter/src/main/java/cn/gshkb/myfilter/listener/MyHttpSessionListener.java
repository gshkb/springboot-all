package cn.gshkb.myfilter.listener;

import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author hkb
 * @create 2019-11-13 13:45 v1.0
 **/
@Component
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("httpSession创建....");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("httpSession销毁....");
	}
}
