package cn.gshkb.designpattern.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author hkb
 * @version v1.0
 * @date 2019-11-14-14:46
 */
@Slf4j
public class CgLibProxy implements MethodInterceptor {

    private Object target;

    public CgLibProxy(Object o) {
        this.target = o;

    }

    public Object getInstance() {
        Enhancer enhancer = new Enhancer(); //创建加强器，用来创建动态代理类
        enhancer.setSuperclass(this.target.getClass());  //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        // 创建动态代理类对象并返回
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("cglib before...");
        methodProxy.invokeSuper(target, objects);
        log.info("cglib after...");
        return null;
    }
}
