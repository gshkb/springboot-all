package cn.gshkb.designpattern.proxy.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * 测试类
 *
 * @author hkb
 * @create 2019-11-13 17:54 v1.0
 **/
public class TestJdkProxy {
	public static void main(String[] args) throws NoSuchMethodException {
		ProxyTarget proxyTarget = new ProxyTarget();
		MyInvokerHandler myInvokerHandler = new MyInvokerHandler(proxyTarget);
		Object o = Proxy.newProxyInstance(proxyTarget.getClass().getClassLoader(), proxyTarget.getClass().getInterfaces(), myInvokerHandler);


	}

}
