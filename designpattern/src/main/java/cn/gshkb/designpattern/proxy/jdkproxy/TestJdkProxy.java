package cn.gshkb.designpattern.proxy.jdkproxy;

import java.lang.reflect.Proxy;

/**
 * 测试类
 *
 * @author hkb
 * @create 2019-11-13 17:54 v1.0
 **/
public class TestJdkProxy {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException {

        PeopleInterface proxyTarget = ProxyTarget.class.newInstance();

		MyInvokerHandler myInvokerHandler = new MyInvokerHandler(proxyTarget);

        PeopleInterface o = (PeopleInterface) Proxy.newProxyInstance(ProxyTarget.class.getClassLoader(), ProxyTarget.class.getInterfaces(), myInvokerHandler);
        o.say("吃饭");
        o.wc("wc");
        //工厂模式调用
		/*PeopleInterface builder = ProxyFactory.Builder(ProxyTarget.class);
		builder.wc("wx");
		builder.say("say");*/


	}

}
