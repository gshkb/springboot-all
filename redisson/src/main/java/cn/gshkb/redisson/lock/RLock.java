package cn.gshkb.redisson.lock;

import cn.gshkb.redisson.config.RedisConifg;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分布式锁
 *
 * @author hkb
 * @create 2019-09-10 16:08 v1.0
 **/
@Service
public class RLock {

	@Autowired
	private RedisConifg redisConifg;

	public void getLock() {
		RedissonClient redissonClient = redisConifg.redissonClient();
		RLock          lock           = (RLock) redissonClient.getLock("myLock");
		RLock          fairLock       = (RLock) redissonClient.getFairLock("myFairLock");
	}
}
