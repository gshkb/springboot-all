package cn.gshkb.designpattern.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Callback;

/**
 * cglib自定义数据回调
 *
 * @author hkb
 * @version v1.0
 * @date 2019-11-14-15:24
 */
@Slf4j
public class CglibCallBack implements Callback {

    private String name;

    private Integer code;

    public void callback() {
        log.info("cglib 回调");
    }
}
