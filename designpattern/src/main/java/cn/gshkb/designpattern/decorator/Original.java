package cn.gshkb.designpattern.decorator;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 抽象的原身
 */
@Slf4j
public class Original extends JFrame implements IMorrigan {

    private static final long serialVersionUID = 1L;
    private String t = "Morrigan0.jpg";

    public Original() {
        super("《恶魔战士》中的莫莉卡·安斯兰");
    }

    public void setImage(String t) {
        this.t = t;
    }

    @Override
    public void display() {
        log.info("我是原始人");
        this.setLayout(new FlowLayout());
        Class<? extends Original> aClass = getClass();
        ImageIcon imageIcon = new ImageIcon(t);
        JLabel l1 = new JLabel(imageIcon);
        this.add(l1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
