package com.ywt.tank.tank1_0;


/**
 * 子弹类
 * 要让子弹发射，应该是一个线程
 */
@SuppressWarnings({"all"})
public class Bullet implements Runnable{
    int x;  // 子弹的横坐标
    int y;  // 子弹纵坐标
    int director = 0;  // 子弹方向
    int speed = 2;  // 子弹速度
    boolean isAlive = true;  // 子弹是否存活

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Bullet(int x, int y, int director) {
        this.x = x;
        this.y = y;
        this.director = director;
    }

    @Override
    public void run() {
        while (true) {
            // 先让子弹休眠 50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 子弹移动
            switch (director) {
                case 0: // 上
                    y -= speed;
                    break;
                case 1: // 下
                    y += speed;
                    break;
                case 2: // 左
                    x -= speed;
                    break;
                case 3: // 右
                    x += speed;
                    break;
            }
            // 当子弹走出边界时或者子弹不存活时，应该被销毁
            if(!(x >= 0 && x <= 1000 && y >= 0 && y<= 750 && isAlive)) {
                isAlive = false;
                break;
            }
        }
    }
}
