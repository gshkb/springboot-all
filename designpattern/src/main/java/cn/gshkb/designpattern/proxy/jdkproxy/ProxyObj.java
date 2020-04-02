package cn.gshkb.designpattern.proxy.jdkproxy;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyObj implements IPeopleInterface{


    private IPeopleInterface people;

    public ProxyObj(IPeopleInterface people) {
        this.people = people;
    }

    @Override
    public void say(String say) {
        log.warn("我静态代理模式 调用了被目标类 say");
        people.say(say);
    }

    @Override
    public void wc(String wc) {
        log.warn("我静态代理模式 调用了被目标类 wc");
        people.wc(wc);
    }
}
