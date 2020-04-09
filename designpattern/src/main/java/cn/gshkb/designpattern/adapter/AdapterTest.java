package cn.gshkb.designpattern.adapter;

import cn.gshkb.designpattern.adapter.classmode.ClassAdapter;
import cn.gshkb.designpattern.adapter.objmode.ObjAdapter;

public class AdapterTest {

    public static void main(String[] args) {
        testClassAdpter();
        testObjAdpter();
    }

    /**
     * 类对象适配器
     */
    private static void testClassAdpter() {
        ITarget target = new ClassAdapter();
        target.doing();
    }

    private static void testObjAdpter() {
        ITarget adapter = new ObjAdapter(new Adaptee());
        adapter.doing();
    }
}
