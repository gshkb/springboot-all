package cn.gshkb.designpattern.adapter.classmode;


import cn.gshkb.designpattern.adapter.Adaptee;
import cn.gshkb.designpattern.adapter.ITarget;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassAdapter extends Adaptee implements ITarget {

    @Override
    public void doing() {
      log.info("我是类适配器");
      super.doing();
    }
}
