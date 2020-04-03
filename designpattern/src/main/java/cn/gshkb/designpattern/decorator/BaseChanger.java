package cn.gshkb.designpattern.decorator;

/**
 * 抽象的装饰角色-变身
 */
public abstract class BaseChanger implements IMorrigan {

    public IMorrigan morrigan;

    public BaseChanger(IMorrigan morrigan) {
        this.morrigan = morrigan;
    }

    @Override
    public void display() {
        morrigan.display();
    }


}
