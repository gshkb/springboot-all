package cn.gshkb.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * 适配者
 */
@Slf4j
public class Adaptee {

    public void doing(){
      log.info("我是适配者");
    }
}
