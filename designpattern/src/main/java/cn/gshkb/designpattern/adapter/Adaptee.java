package cn.gshkb.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Adaptee implements IAdaptee{

    @Override
    public void doing(String s) {
      log.info("适配器："+s);
    }

}
