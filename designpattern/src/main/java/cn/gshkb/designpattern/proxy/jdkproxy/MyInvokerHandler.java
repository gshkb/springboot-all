package cn.gshkb.designpattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 中间类
 *
 * @author hkb
 * @create 2019-11-13 17:44 v1.0
 **/

public class MyInvokerHandler implements InvocationHandler {

	private Object people;

	public MyInvokerHandler(PeopleInterface peopleInterface) {
		this.people = peopleInterface;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//调用目标方法
		Object invoke = null;
		try {
			invoke = method.invoke(people, args);
			System.out.println("invoke:" + invoke.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invoke;
	}
}
