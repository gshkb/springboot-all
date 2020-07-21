package cn.gshkb.sentinel.server2.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello/{name}")
    @SentinelResource(value = "test")
    public String get(@PathVariable String name){
        return "hello,"+name;
    }
}
