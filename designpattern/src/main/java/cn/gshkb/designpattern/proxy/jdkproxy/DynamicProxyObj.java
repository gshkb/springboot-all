package cn.gshkb.designpattern.proxy.jdkproxy;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

@Slf4j
public class DynamicProxyObj {

    private IPeopleInterface target;

    public void say(String say) throws Throwable {
        target = ProxyTarget.class.newInstance();
        MyInvokerHandler invokerHandler = new MyInvokerHandler(target);
        IPeopleInterface o = (IPeopleInterface)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invokerHandler);
        o.say(say);
        o.wc("吃完饭后wc");
    }


}
