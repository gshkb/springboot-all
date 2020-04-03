package cn.gshkb.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 装饰的角色-女妖
 */
@Slf4j
public class Succubus extends BaseChanger{

    public Succubus(IMorrigan morrigan) {
        super(morrigan);
    }

    @Override
    public void display() {
        log.info("我是女妖");
        setChange();
        super.display();

    }

    private void setChange(){
        ((Original) super.morrigan).setImage("Morrigan1.jpg");
    }
}
