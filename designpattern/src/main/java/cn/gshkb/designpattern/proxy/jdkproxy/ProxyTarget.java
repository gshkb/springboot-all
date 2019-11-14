package cn.gshkb.designpattern.proxy.jdkproxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 目标类
 *
 * @author hkb
 * @create 2019-11-13 17:41 v1.0
 **/
@Slf4j
public class ProxyTarget implements PeopleInterface{

	@Override
    public void say(String say) {
        log.info("被代理对象说:{}", say);
    }

    @Override
    public void wc(String wc) {
        log.info("被代理wc:{}", wc);
	}
}
