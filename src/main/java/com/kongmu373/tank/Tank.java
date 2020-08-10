package com.kongmu373.tank;

import static com.kongmu373.tank.TankFrame.GAME_HEIGHT;
import static com.kongmu373.tank.TankFrame.GAME_WIDTH;

import lombok.Data;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

/**
 * 面向对象的设计思想:
 * 抽象出名词，类，属性
 * 抽象出动词, 方法
 */
@Data
public class Tank {
    private int x;
    private int y;
    // 代表方向的枚举变量
    // 为什么使用Enum比int类型好?
    private Dir dir;
    private static final int SPEED = 5;
    private Group group;
    private boolean live = true;
    private List<Bullet> bullets = new LinkedList<>();
    private int oldX, oldY;
    private Explode explode;

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }


    public void fire() {
        int bX = x + ResourceMgr.badTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int bY = y + ResourceMgr.badTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;
        this.bullets.add(new Bullet(bX, bY, dir, group));
    }

    private void drawBullets(Graphics g) {
        if (!bullets.isEmpty()) {
            // 显示 子弹
            bullets.forEach((bullet) -> {
                bullet.collidesWithTank(TankFrame.INSTANCE.getMyTank());
                bullet.paint(g);
            });
            // 清除 超出边界的子弹
            bullets.removeIf((bullet) -> !bullet.isLive());
        }
    }

    public void move() {
        setOldX(getX());
        setOldY(getY());
        if (Dir.random.nextInt(100) > 90) {
            setRandomDir();
            fire();
        }
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }

    }

    public void paint(Graphics g) {
        if (isOverBound()) {
            back();
        }
        if (!isLive() && explode != null && !explode.isLive()) {
            return;
        }
        if (!isLive()) {
            if (explode == null) {
                explode = new Explode(x, y);
            }
            explode.paint(g);
            return;
        }
        drawBullets(g);
        switch (dir) {
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;

            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;

            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;

            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
        }
        move();

    }

    public void back() {
        this.x = this.oldX;
        this.y = this.oldY;
    }


    private void setRandomDir() {
        this.dir = Dir.randomDir();
    }

    public void die() {
        this.setLive(false);
    }

    public boolean isOverBound() {
        return this.getX() > GAME_WIDTH - ResourceMgr.badTankU.getWidth()
                   || this.getX() < 0
                   || this.getY() > (GAME_HEIGHT - ResourceMgr.badTankU.getHeight())
                   || this.getY() < 30;
    }
}
