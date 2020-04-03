package cn.gshkb.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Girl extends BaseChanger {

    public Girl(IMorrigan morrigan) {
        super(morrigan);
    }

    @Override
    public void display() {
        log.info("我是小仙女");
        setChange();
        super.display();
    }

    private void setChange() {
        ((Original) super.morrigan).setImage("Morrigan2.jpg");
    }
}
