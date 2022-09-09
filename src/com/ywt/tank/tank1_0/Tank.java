package com.ywt.tank.tank1_0;

/**
 * 所有的坦克都继承此类
 */
public class Tank {
    private int x;  // 坦克横坐标
    private int y;  // 坦克纵坐标
    private int direction; // 坦克方向 0上 1下 2左 3右
    private int speed = 2;  // 坦克速度
    public boolean isAlive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // 坦克上下左右移动
    public void moveUp() {
        this.y -= speed;
    }

    public void moveDown() {
        this.y += speed;
    }

    public void moveLeft() {
        this.x -= speed;
    }

    public void moveRight() {
        this.x += speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
