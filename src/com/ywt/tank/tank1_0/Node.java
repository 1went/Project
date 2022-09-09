package com.ywt.tank.tank1_0;

/**
 * 用于封装坦克的位置信息和方向
 */
public class Node {
    private final int x;
    private final int y;
    private final int director;

    public Node(int x, int y, int director) {
        this.x = x;
        this.y = y;
        this.director = director;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirector() {
        return director;
    }

}
