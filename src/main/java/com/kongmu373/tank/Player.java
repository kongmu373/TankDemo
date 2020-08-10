package com.kongmu373.tank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * 面向对象的设计思想:
 * 抽象出名词，类，属性
 * 抽象出动词, 方法
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Player extends Tank {


    private boolean bL, bR, bU, bD;
    private static final int SPEED = 5;
    private boolean moving = false;


    public Player(int x, int y, Dir dir, Group group) {
        super(x, y, dir, group);

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();
    }


    private void setMainDir() {
        if (!bL && !bU && !bR && !bD) {
            setMoving(false);
        } else {
            setMoving(true);
            if (bL) {
                setDir(Dir.L);
            }
            if (bU) {
                setDir(Dir.U);
            }
            if (bR) {
                setDir(Dir.R);
            }
            if (bD) {
                setDir(Dir.D);
            }
        }
    }

    public void move() {
        if (!moving) {
            return;
        }
        setOldX(getX());
        setOldY(getY());
        switch (getDir()) {
            case L:
                setX(getX() - SPEED);
                break;
            case R:
                setX(getX() + SPEED);
                break;
            case U:
                setY(getY() - SPEED);
                break;
            case D:
                setY(getY() + SPEED);
                break;
        }

    }

    public void paint(Graphics g) {
        if (isOverBound()) {
            back();
        }


        if (!isLive() && getExplode() != null && !getExplode().isLive()) {
            return;
        }
        if (!isLive()) {
            if (getExplode() == null) {
                setExplode(new Explode(getX(), getY()));
            }
            getExplode().paint(g);
            return;
        }
        drawBullets(g);

        switch (getDir()) {
            case D:
                g.drawImage(ResourceMgr.goodTankD, getX(), getY(), null);
                break;

            case U:
                g.drawImage(ResourceMgr.goodTankU, getX(), getY(), null);
                break;

            case L:
                g.drawImage(ResourceMgr.goodTankL, getX(), getY(), null);
                break;

            case R:
                g.drawImage(ResourceMgr.goodTankR, getX(), getY(), null);
                break;
        }
        move();
    }

    private void drawBullets(Graphics g) {
        if (!getBullets().isEmpty()) {
            // 显示 子弹
            getBullets().forEach((bullet) -> {
                bullet.paint(g);
                for (Tank enemy : TankFrame.INSTANCE.getEnemys()) {
                    bullet.collidesWithTank(enemy);
                }
            });
            // 清除 超出边界的子弹
            getBullets().removeIf((bullet) -> !bullet.isLive());
        }
    }


}
