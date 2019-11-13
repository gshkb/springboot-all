package cn.gshkb.designpattern.proxy.statics;

/**
 * 代理类
 *
 * @author hkb
 * @create 2019-11-13 15:53 v1.0
 **/
public class ProxyObject implements ProxyInterface{

	private ProxyInterface proxyTarget;

	ProxyObject(ProxyInterface proxyTarget){
		this.proxyTarget = proxyTarget;
	}

    @Override
	public String write() {
		return proxyTarget.write();
	}

	public static void main(String[] args) {
		ProxyObject proxyObject = new ProxyObject(new ProxyTarget());
		proxyObject.write();
	}
}
