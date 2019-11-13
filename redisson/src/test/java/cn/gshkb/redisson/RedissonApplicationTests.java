package cn.gshkb.redisson;

/*import cn.gshkb.redisson.config.RedisConifg;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonApplicationTests {

	@Autowired
	private RedisConifg redisConifg;

	private RedissonClient redisClient;

	@Before
	public void client(){
		this.redisClient = redisConifg.redissonClient();
	}

	@Test
	public void contextLoads() {
	}

	*//**
	 * 获取 重入锁
	 *//*
	@Test
	public void getLock(){
		RLock firstLock = redisClient.getLock("firstLock");
		firstLock.lock();
		firstLock.unlock();
		System.out.println(firstLock.tryLock());

	}

	*//**
	 * 获取  bucket
	 *//*
	@Test
	public void getBucket(){
		RBucket<Integer> bucket = redisClient.getBucket("anyObject");
		bucket.set(new Integer(1));
		System.out.println(bucket.get());
		bucket.trySet(new Integer(2));
		System.out.println(bucket.get());
		bucket.compareAndSet(new Integer(3), new Integer(4));
		System.out.println(bucket.get());
		bucket.getAndSet(new Integer(6));
		System.out.println(bucket.get());
	}

	*//**
	 * 获取  bucket
	 *//*
	@Test
	public void getBuckets(){
		RBuckets               buckets       = redisClient.getBuckets();
		//List<RBucket<Integer>> foundBuckets  = (List<RBucket<Integer>>) buckets.get("myBucket*");
		Map<String, Integer>   loadedBuckets = buckets.get("myBucket1", "myBucket2", "myBucket3");

		Map<String, Object> map = new HashMap<>();
		map.put("myBucket1", new Integer(1));
		map.put("myBucket2", new Integer(2));

		// sets all or nothing if some bucket is already exists
		buckets.trySet(map);
		// store all at once
		buckets.set(map);
		System.out.println(buckets.get().toString());
	}

	@Test
	public void binaryStream() throws IOException {
		RBinaryStream stream  = redisClient.getBinaryStream("anyStream");
		byte[]      oldContent = new byte[]{6};
		byte[]        content = new byte[]{1,23,45,'m','c'};
		stream.set(content);
		stream.getAndSet(content);
		stream.trySet(content);
		stream.compareAndSet(oldContent, content);

		InputStream is         = stream.getInputStream();
		byte[]      readBuffer = new byte[512];
		System.out.println(is.read(readBuffer));

		OutputStream os             = stream.getOutputStream();
		byte[]       contentToWrite = new byte[]{1,2};
		os.write(contentToWrite);
	}

	@Test
	public void geospatial(){
		RGeo<String> geo = redisClient.getGeo("test");
		geo.add(new GeoEntry(13.361389, 38.115556, "Palermo"),
				new GeoEntry(15.087269, 37.502669, "Catania"));
		geo.addAsync(37.618423, 55.751244, "Moscow");

		Double distance = geo.dist("Palermo", "Catania", GeoUnit.METERS);
		System.out.println(distance);
		geo.hashAsync("Palermo", "Catania");
		Map<String, GeoPosition> positions = geo.pos("test2", "Palermo", "test3", "Catania", "test1");
		List<String> cities = geo.radius(15, 37, 200, GeoUnit.KILOMETERS);
		Map<String, GeoPosition> citiesWithPositions = geo.radiusWithPosition(15, 37, 200, GeoUnit.KILOMETERS);

		for (Map.Entry<String, GeoPosition> entry :citiesWithPositions.entrySet()){
                System.out.println("key:"+entry.getKey()+"..value:"+entry.getValue());
		}
	}
}*/


