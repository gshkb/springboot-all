package cn.gshkb.myfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
/*@ComponentScan("cn.gshkb.myfilter.interceptor")*/
@ServletComponentScan(basePackages = "cn.gshkb.myfilter.*")
public class MyfilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyfilterApplication.class, args);
    }

}
