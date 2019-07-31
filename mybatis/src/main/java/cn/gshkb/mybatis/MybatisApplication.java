package cn.gshkb.mybatis;

import org.mybatis.spring.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;


@SpringBootApplication
@MapperScan("cn.gshkb.*.mapper")
public class MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisApplication.class, args);
	}

}
