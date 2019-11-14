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

    public static PeopleInterface Builder(Class classFile) {

        PeopleInterface proxy = null;
        try {
            proxy = (PeopleInterface) classFile.newInstance();

        } catch (Exception e) {
            log.error("代理对象创建失败,{}", e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        MyInvokerHandler myInvokerHandler = new MyInvokerHandler(proxy);
        return (PeopleInterface) Proxy.newProxyInstance(classFile.getClassLoader(), classFile.getInterfaces(), myInvokerHandler);
    }
}
