package cn.gshkb.designpattern.singleton;

/**
 * 饿汉模式-类加载的时候就完成初始化
 *
 * @author hkb
 * @create 2019-11-13 15:25 v1.0
 **/
public class Singleton {

	private static Singleton instance = new Singleton();

	private  Singleton(){

	}

	public static Singleton getInstance(){
		return instance;
	}
}
