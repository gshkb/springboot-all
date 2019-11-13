package cn.gshkb.designpattern.proxy.jdkproxy;

/**
 * 目标类
 *
 * @author hkb
 * @create 2019-11-13 17:41 v1.0
 **/
public class ProxyTarget implements PeopleInterface{

	@Override
	public void say() {
	 System.out.println("say hello");
	}
}
