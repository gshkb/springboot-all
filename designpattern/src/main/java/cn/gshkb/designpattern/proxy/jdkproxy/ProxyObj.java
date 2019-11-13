package cn.gshkb.designpattern.proxy.jdkproxy;

/**
 * 代理类
 *
 * @author hkb
 * @create 2019-11-13 17:42 v1.0
 **/
public class ProxyObj implements PeopleInterface{
	@Override
	public void say() {
		System.out.println("say Chinese!");
	}

}
