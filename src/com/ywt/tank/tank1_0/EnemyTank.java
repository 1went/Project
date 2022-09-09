package com.ywt.tank.tank1_0;

import java.util.Vector;

/**
 * 敌人坦克
 */
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable{
    /**
     * 在敌人坦克内用 Vector保存敌人坦克的子弹
     */
    Vector<Bullet> bullets = new Vector<>();

    /**
     * 用于获取其他敌人坦克
     */
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    /**
     * 判断这个敌坦是否与其他敌坦重叠
     */
    private boolean overlap() {
        int myX = this.getX();
        int myY = this.getY();
        switch (this.getDirection()) {
            case 0:  // 上
                for (int i=0; i<enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        int etX = enemyTank.getX();
                        int etY = enemyTank.getY();
                        int etDir = enemyTank.getDirection();
                        // 当坦克是上下
                        if (etDir == 0 || etDir == 1) {
                            // 拿当前坦克的左上角坐标比
                            if (myX >= etX && myX <= etX + 40 && myY >= etY && myY < etY + 60) {
                                return true;
                            }
                            // 比较右上角
                            if (myX+40 >= etX && myX+40 <= etX + 40 && myY >= etY && myY < etY + 60) {
                                return true;
                            }
                        }
                        // 当坦克是左右
                        if (etDir == 2 || etDir == 3) {
                            if (myX >= etX && myX <= etX + 60 && myY >= etY && myY <= etY + 40) {
                                return true;
                            }
                            if (myX+40 >= etX && myX+40 <= etX+60 + 40 && myY >= etY && myY < etY + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1:  // 下
                for (int i=0; i<enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        int etX = enemyTank.getX();
                        int etY = enemyTank.getY();
                        int etDir = enemyTank.getDirection();
                        // 当坦克是上下
                        if (etDir == 0 || etDir == 1) {
                            // 拿当前坦克的左下角坐标比
                            if (myX >= etX && myX <= etX + 40 && myY+60 >= etY && myY+60 < etY + 60) {
                                return true;
                            }
                            // 比较右下角
                            if (myX+40 >= etX && myX+40 <= etX + 40 && myY+60 >= etY && myY+60 < etY + 60) {
                                return true;
                            }
                        }
                        // 当坦克是左右
                        if (etDir == 2 || etDir == 3) {
                            if (myX >= etX && myX <= etX + 60 && myY+60 >= etY && myY+60 <= etY + 40) {
                                return true;
                            }
                            if (myX+40 >= etX && myX+40 <= etX+60 + 40 && myY+60 >= etY && myY+60 < etY + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2:  // 左
                for (int i=0; i<enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        int etX = enemyTank.getX();
                        int etY = enemyTank.getY();
                        int etDir = enemyTank.getDirection();
                        // 当坦克是上下
                        if (etDir == 0 || etDir == 1) {
                            // 拿当前坦克的左上角坐标比
                            if (myX >= etX && myX <= etX + 40 && myY >= etY && myY < etY + 60) {
                                return true;
                            }
                            // 比较左下角
                            if (myX >= etX && myX <= etX + 40 && myY+40 >= etY && myY+40 < etY + 60) {
                                return true;
                            }
                        }
                        // 当坦克是左右
                        if (etDir == 2 || etDir == 3) {
                            if (myX >= etX && myX <= etX + 60 && myY >= etY && myY <= etY + 40) {
                                return true;
                            }
                            if (myX >= etX && myX <= etX+60 + 40 && myY+40 >= etY && myY+40 < etY + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3:  // 右
                for (int i=0; i<enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    // 不和自己比较
                    if (enemyTank != this) {
                        int etX = enemyTank.getX();
                        int etY = enemyTank.getY();
                        int etDir = enemyTank.getDirection();
                        // 当坦克是上下
                        if (etDir == 0 || etDir == 1) {
                            // 拿当前坦克的右上角坐标比
                            if (myX+60 >= etX && myX+60 <= etX + 40 && myY >= etY && myY < etY + 60) {
                                return true;
                            }
                            // 比较右下角
                            if (myX+60 >= etX && myX+60 <= etX + 40 && myY+40 >= etY && myY+40 < etY + 60) {
                                return true;
                            }
                        }
                        // 当坦克是左右
                        if (etDir == 2 || etDir == 3) {
                            if (myX+60 >= etX && myX+60 <= etX + 60 && myY >= etY && myY <= etY + 40) {
                                return true;
                            }
                            if (myX+60 >= etX && myX+60 <= etX+60 + 40 && myY+40 >= etY && myY+40 < etY + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {

            if (isAlive && bullets.size() < 3) {  // 规定敌人最多一次性发射3颗子弹
                Bullet b = null;
                switch (getDirection()) {
                    case 0:
                        b = new Bullet(getX()+20, getY(), 0);
                        break;
                    case 1:
                        b = new Bullet(getX()+20, getY()+60, 1);
                        break;
                    case 2:
                        b = new Bullet(getX(), getY()+20, 2);
                        break;
                    case 3:
                        b = new Bullet(getX()+60, getY()+20, 3);
                        break;
                }
                b.setSpeed(5);
                bullets.add(b);
                new Thread(b).start();
            }

            switch (getDirection()) {
                // 为了模拟真实情况，应该让坦克每次在当前方向先移动一定步数，再随机切换方向
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if(getY() > 0 && !overlap()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !overlap()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !overlap()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !overlap()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            // 随机改变方向
            setDirection((int)(Math.random()*4));

            // 当坦克不存活时，就要结束这个线程
            if(!isAlive) {
                break;
            }
        }
    }
}
