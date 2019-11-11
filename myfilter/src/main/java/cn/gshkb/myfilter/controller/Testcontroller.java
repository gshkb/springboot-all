package cn.gshkb.myfilter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hkb
 * @version v1.0
 * @date 2019-11-11-21:48
 */

@RestController
public class Testcontroller {

    @GetMapping("/test")
    public void test() {
        System.out.println("test controller!");
    }
}
