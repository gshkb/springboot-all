package cn.gshkb.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Target implements ITarget {
    @Override
    public void doing(String s) {
      log.info("目标适配器："+s);
    }
}
