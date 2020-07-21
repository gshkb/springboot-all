package cn.gshkb.redisson.demo;

import cn.gshkb.redisson.config.RedisConifg;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class DemoController {


    @Autowired
    private RedisConifg redisConifg;


    @PostMapping("/sock")
    public Boolean incrSock() {
        RedissonClient redissonClient = redisConifg.redissonClient();
       // RLock lock = redissonClient.getLock("goods");
       // lock.lock(3,TimeUnit.SECONDS);
        Integer goods = (Integer) redissonClient.getMap("maps").get("goods");
        System.out.println(Thread.currentThread().getId() + "：" + goods);

        if (goods > 0) {
            goods--;
            redissonClient.getMap("maps").fastPut("goods", goods);
         //   lock.forceUnlock();
            return true;
        } else {
            return false;
        }
    }

    @PutMapping("/add")
    public Boolean add() {
        RedissonClient redissonClient = redisConifg.redissonClient();
        return redissonClient.getMap("maps").fastPut("goods", 1000);
    }
}
