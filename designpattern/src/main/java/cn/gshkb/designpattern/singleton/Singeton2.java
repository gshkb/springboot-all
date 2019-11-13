package cn.gshkb.designpattern.singleton;

/**
 * 双重检查模式
 * <p>
 *     基于懒汉模式
 * <p/>
 * @author hkb
 * @create 2019-11-13 15:33 v1.0
 **/
public class Singeton2 {

	private volatile static Singeton2 instance;

	private Singeton2(){}

	public static Singeton2 getInstance(){
		if (null==instance){
			synchronized (Singeton2.class){
				if (null==instance){
					return new Singeton2();
				}
			}
		}
		return instance;
	}
}
