package cn.gshkb.designpattern.adapter.objmode;

import cn.gshkb.designpattern.adapter.Adaptee;
import cn.gshkb.designpattern.adapter.ITarget;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjAdapter implements ITarget {

    private Adaptee adaptee;

    public ObjAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void doing() {
      log.info("我是对象适配器");
      adaptee.doing();
    }
}
