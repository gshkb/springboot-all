package cn.gshkb.elasticsearch.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hkb
 * @create 2019-08-02 20:00 v1.0
 **/
@RestController("/es")
@Data
@Slf4j
public class TestController {

	@Autowired
	private RestClient restClient;
}
