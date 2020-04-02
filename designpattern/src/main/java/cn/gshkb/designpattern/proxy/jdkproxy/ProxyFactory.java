package cn.gshkb.designpattern.proxy.jdkproxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * 代理工厂
 *
 * @author hkb
 * @version v1.0
 * @date 2019-11-14-11:11
 */
@Slf4j
public class ProxyFactory {

    public static IPeopleInterface Builder(Class classFile) {

        IPeopleInterface proxy = null;
        try {
            proxy = (IPeopleInterface) classFile.newInstance();
        } catch (Exception e) {
            log.error("代理对象创建失败,{}", e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("我是代理工厂");
        MyInvokerHandler myInvokerHandler = new MyInvokerHandler(proxy);
        return (IPeopleInterface) Proxy.newProxyInstance(classFile.getClassLoader(), classFile.getInterfaces(), myInvokerHandler);
    }
}
