package cn.gshkb.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
public class IoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoApplication.class, args);

        new HashMap<>();
        new ConcurrentHashMap<>();
    }

}
