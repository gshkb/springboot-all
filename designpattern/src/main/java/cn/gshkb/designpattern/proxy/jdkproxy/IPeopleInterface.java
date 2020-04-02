package cn.gshkb.designpattern.proxy.jdkproxy;

/**
 * 抽象公共接口
 * @author hkb
 * @create 2019-11-13 17:39 v1.0
 **/
public interface IPeopleInterface {

    /**
     * 说话
     * @param say
     */
    public void say(String say);

    /**
     * 去wc
     * @param wc
     */
    public void wc(String wc);
}
