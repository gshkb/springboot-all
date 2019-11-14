package cn.gshkb.designpattern.proxy.cglib;

import cn.gshkb.designpattern.proxy.jdkproxy.ProxyTarget;

/**
 * @author hkb
 * @version v1.0
 * @date 2019-11-14-15:21
 */

public class TestCgLibProxy {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        CgLibProxy cgLibProxy = new CgLibProxy(new ProxyTarget());
        ProxyTarget instance = (ProxyTarget) cgLibProxy.getInstance();
        instance.say("cglib");
        instance.wc("cglib");
    }
}
