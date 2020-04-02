package cn.gshkb.designpattern.proxy.jdkproxy;

import java.lang.annotation.Target;
import java.lang.reflect.Proxy;

/**
 * 测试类
 *
 * @author hkb
 * @create 2019-11-13 17:54 v1.0
 **/
public class TestJdkProxy {
    public static void main(String[] args) throws Throwable {

        testProxyObj();

        testProxyFactory();

        testDynamicProxy();

    }


    private static void testProxyObj() {
        ProxyObj proxyObj = new ProxyObj(new ProxyTarget());
        proxyObj.say("测试静态代理");
    }

    private static void testProxyFactory() {
        IPeopleInterface builder = ProxyFactory.Builder(ProxyTarget.class);
        builder.say("测试工厂方法代理");
    }

    private static void testDynamicProxy() throws Throwable {
        DynamicProxyObj proxyObj = new DynamicProxyObj();
        proxyObj.say("测试动态代理");
    }
}
