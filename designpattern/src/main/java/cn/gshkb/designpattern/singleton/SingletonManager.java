package cn.gshkb.designpattern.singleton;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用容器实现单例模式
 *
 * @author hkb
 * @create 2019-11-13 15:39 v1.0
 **/
public class SingletonManager {

	private static ConcurrentHashMap map = new ConcurrentHashMap();


	public static void setInstance(String key, Object o) {
		if (!map.containsKey(key)) {
			map.put(key, o);
		}
	}

	public static Object getInstance(String key) {
		return map.get(key);
	}
}
