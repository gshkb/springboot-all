package cn.gshkb.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${cn.gshkb.nacos.config:message}", autoRefreshed = true)
    private String message;

    @GetMapping("/hello")
    public String get() {
        return "hello" + message;
    }
}
