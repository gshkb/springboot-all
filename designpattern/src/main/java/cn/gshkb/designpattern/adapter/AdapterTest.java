package cn.gshkb.designpattern.adapter;

import cn.gshkb.designpattern.adapter.ITarget;
import cn.gshkb.designpattern.adapter.classmode.ClassAdapter;

public class AdapterTest {

    public static void main(String[] args) {
        testClassAdpter();
    }

    /**
     * 类对象适配器
     */
    private static void testClassAdpter() {
        ITarget target = new ClassAdapter();
        target.doing();
    }
}
