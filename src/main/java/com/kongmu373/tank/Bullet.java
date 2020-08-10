package com.kongmu373.tank;

import static com.kongmu373.tank.TankFrame.GAME_HEIGHT;
import static com.kongmu373.tank.TankFrame.GAME_WIDTH;

import lombok.Data;

import java.awt.Graphics;
import java.awt.Rectangle;

@Data
public class Bullet {
    private int x, y;
    private Dir dir;
    private Group group;
    public static final int SPEED = 10;
    private boolean live = true;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }


    public void paint(Graphics g) {
        if (isOverBound()) {
            die();
        }
        switch (dir) {
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;

            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;

            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;

            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
        }
        move();
    }

    public void move() {
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


    public void collidesWithTank(Tank tank) {
        if (!tank.isLive()) {
            return;
        }
        if (this.group == tank.getGroup()) {
            return;
        }
        Rectangle rect = new Rectangle(x, y, ResourceMgr.bulletU.getWidth(), ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(), tank.getY(),
            ResourceMgr.goodTankU.getWidth(),
            ResourceMgr.goodTankU.getHeight());
        if (rect.intersects(rectTank)) {
            die();
            tank.die();
        }
    }


    public void die() {
        this.setLive(false);
    }

    private boolean isOverBound() {
        return this.getX() > GAME_WIDTH
                   || this.getX() < 0
                   || this.getY() > GAME_HEIGHT
                   || this.getY() < 30;
    }
}
