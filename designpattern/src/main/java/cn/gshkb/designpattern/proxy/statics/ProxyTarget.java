package cn.gshkb.designpattern.proxy.statics;

/**
 * 代理目标类
 *
 * @author hkb
 * @create 2019-11-13 15:54 v1.0
 **/
public class ProxyTarget implements ProxyInterface{

	@Override
	public String write() {
		System.out.println("代理目标类");
		return "代理目标类";
	}
}
