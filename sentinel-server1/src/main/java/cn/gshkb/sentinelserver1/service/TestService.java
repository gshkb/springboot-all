package cn.gshkb.sentinelserver1.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @SentinelResource
    public String sayHello(String name) {
        return "hello"+name;
    }
}
