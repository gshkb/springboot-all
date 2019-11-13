package cn.gshkb.designpattern.singleton;

/**
 * 懒汉模式
 *
 * @author hkb
 * @create 2019-11-13 15:27 v1.0
 **/
public class Singleton1 {

	private static Singleton1 instance;

	private Singleton1(){}

	/**
	 * 线程不安全
	 * @return
	 */
	public static Singleton1 getInstance(){
		if (null==instance){
			return new Singleton1();
		}
		return instance;
	}

	/**
	 * 线程安全
	 * @return
	 */
	public static synchronized  Singleton1 getInstance1(){
		if (null==instance){
			return new Singleton1();
		}
		return instance;
	}
}
