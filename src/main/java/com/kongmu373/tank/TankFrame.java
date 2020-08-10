package com.kongmu373.tank;

import lombok.Data;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

@Data
public class TankFrame extends Frame {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    private Player myTank;
    private List<Tank> enemys;
    public static final TankFrame INSTANCE = new TankFrame();
    private Image offScreenImage = null;

    private TankFrame() {
        this.setTitle("com.kongmu373.tank.Tank War");
        this.setLocation(400, 100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());
        myTank = new Player(100, 100, Dir.R, Group.GOOD);
        enemys = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            enemys.add(new Tank(50 + 50 * i, 500, Dir.D, Group.BAD));
        }
    }

    @Override
    public void paint(Graphics g) {

        myTank.paint(g);
        enemys.forEach((enemy) -> enemy.paint(g));

    }


    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    // 为什么使用内部类？ -- 其他类不需要访问这个键盘监听类
    // 高内聚 低耦合
    // 非静态内部类可以访问外部类的成员变量以及方法
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }
}
