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

	public MyInvokerHandler(PeopleInterface people){
		this.people = people;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object invoke = method.invoke(people, args);
		System.out.println("invoke:"+invoke.toString());
		return invoke;
	}
}
