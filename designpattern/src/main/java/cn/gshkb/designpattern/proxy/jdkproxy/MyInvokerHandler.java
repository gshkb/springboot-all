package cn.gshkb.designpattern.proxy.jdkproxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 中间类
 *
 * @author hkb
 * @create 2019-11-13 17:44 v1.0
 **/
@Slf4j
public class MyInvokerHandler implements InvocationHandler {

    private Object proxy;

    public MyInvokerHandler(Object peopleInterface) {
        this.proxy = peopleInterface;
	}

	@Override
    public Object invoke(Object ob, Method method, Object[] args) throws Throwable {
		//调用目标方法

        log.info("运行增强的动态代理proxy{},method{},args{}", proxy, method, args);

        return method.invoke(proxy, args);
    }




}
