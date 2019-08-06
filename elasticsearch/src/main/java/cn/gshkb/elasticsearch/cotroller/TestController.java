package cn.gshkb.elasticsearch.cotroller;

import lombok.*;
import lombok.extern.slf4j.*;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

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
