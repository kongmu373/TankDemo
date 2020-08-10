package com.kongmu373.tank;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        TankFrame.INSTANCE.setVisible(true);

        for (; ; ) {
            // repaint -> update -> paint
            TimeUnit.MILLISECONDS.sleep(25);
            TankFrame.INSTANCE.repaint();
        }
    }
}
