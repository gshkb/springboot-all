package cn.gshkb.sentinelserver1embedded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

@SpringBootApplication
public class SentinelServer1EmbeddedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelServer1EmbeddedApplication.class, args);

       // new AbstractQueuedSynchronizer()
    }


}
