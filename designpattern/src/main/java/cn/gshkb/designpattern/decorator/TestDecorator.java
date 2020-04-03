package cn.gshkb.designpattern.decorator;

public class TestDecorator {


    public static void main(String[] args) {
        IMorrigan original = new Original();
        original.display();

        /*IMorrigan succubus = new Succubus(original);
        succubus.display();

        IMorrigan girl = new Girl(original);
        girl.display();*/
    }
}
