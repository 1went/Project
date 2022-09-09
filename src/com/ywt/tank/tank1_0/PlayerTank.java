package com.ywt.tank.tank1_0;


import java.util.Vector;

/**
 * 玩家的坦克
 */
@SuppressWarnings({"all"})
public class PlayerTank extends Tank {
    Bullet bullet;  // 定义子弹对象

    /**
     * 定义子弹集合
     */
    Vector<Bullet> bullets = new Vector<>();

    public PlayerTank(int x, int y) {
        super(x, y);
    }


    public void fireToEnemyTank() {

        // 控制玩家坦克一次性能发射的最多子弹
        if (bullets.size() == 5) {
            return;
        }

        // 根据坦克的不同方向设置子弹的方向
        switch (getDirection()){
            case 0:
                bullet = new Bullet(getX()+20,getY(),0);
                break;
            case 1:
                bullet = new Bullet(getX()+20,getY()+60,1);
                break;
            case 2:
                bullet = new Bullet(getX(),getY()+20,2);
                break;
            case 3:
                bullet = new Bullet(getX()+60,getY()+20,3);
                break;
        }
        // 设置玩家子弹速度
        bullet.setSpeed(5);
        bullets.add(bullet);
        // 启动线程
        new Thread(bullet).start();
    }

}
