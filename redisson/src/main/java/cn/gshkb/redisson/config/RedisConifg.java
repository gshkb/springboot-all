package cn.gshkb.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件
 *
 * @author hkb
 * @create 2019-09-07 17:23 v1.0
 **/
@Configuration
public class RedisConifg {

	@Bean
	public Config config() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress("redis://101.37.125.181:7001")
				.setPassword("redis123");
		return config;
	}

	@Bean
	public RedissonClient redissonClient() {
		return Redisson.create(config());
	}

	@Bean
	public RedissonReactiveClient redissonReactiveClient() {
		return Redisson.createReactive(config());
	}

	@Bean
	public RedissonRxClient redissonRxClient() {
		return Redisson.createRx(config());
	}
}