package com.kongmu373.tank;

import java.util.Random;

public enum Dir {
    L, U, R, D;

    public static Random random = new Random();

    public static Dir randomDir() {
        return Dir.values()[random.nextInt(Dir.values().length)];
    }
}
