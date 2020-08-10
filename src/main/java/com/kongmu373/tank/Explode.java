package com.kongmu373.tank;

import lombok.Data;

import java.awt.Graphics;

@Data
public class Explode {
    private int x, y;
    private int width, height;
    private boolean live = true;
    private int step = 0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
    }

    public void paint(Graphics g) {
        if (!isLive()) {
            return;
        }
        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
        if (step >= ResourceMgr.explodes.length) {
            die();
        }
    }

    private void die() {
        this.live = false;
    }
}
