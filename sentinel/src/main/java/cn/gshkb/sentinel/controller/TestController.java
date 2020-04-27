package cn.gshkb.sentinel.controller;

import cn.gshkb.sentinel.service.TestService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hkb
 * @version v1.0
 * @date 2020-03-28-13:35
 */
@RestController
public class TestController {

    @Autowired
    private TestService service;

    //@SentinelResource
    @GetMapping(value = "/hello/{name}")
    public String apiHello(@PathVariable String name) {
        return service.sayHello(name);
    }
}


