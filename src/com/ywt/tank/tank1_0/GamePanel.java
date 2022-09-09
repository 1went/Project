package com.ywt.tank.tank1_0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * 坦克大战的绘图区
 * 因为子弹必须时刻重绘，才能显示。所以可以将 GamePanel做成一个线程，让其不停重绘面板
 */
@SuppressWarnings({"all"})
public class GamePanel extends JPanel implements KeyListener, Runnable {
    /**
     *定义玩家坦克
     */
    PlayerTank pt;

    /**
     * 定义敌人坦克
     * 让敌人坦克动起来，需要涉及多线程，因此使用集合存储敌人坦克时，应该使用线程安全的集合，比如 Vector
     */
    Vector<EnemyTank> enemyTanks = new Vector<>();

    /**
     * 敌人坦克的位置信息
     */
    Vector<Node> nodes = new Vector<>();

    /**
     * 敌人坦克的数量，初始设为 6
     */
    private int enemySizes = 6;


    public GamePanel(String key) {
        File file = new File(Recorder.getDestFile());
        if (file.exists()) {
            nodes = Recorder.readFile();
        }else {
            key = "1";
        }
        Recorder.setEnemyTanks(enemyTanks);
        // 初始化玩家坦克的位置
        pt = new PlayerTank(900, 600);
        pt.setSpeed(5);

        // 初始化敌人坦克
        switch (key) {
            case "1":  // 新游戏
                for(int i=0; i<enemySizes; i++) {
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    enemyTank.setEnemyTanks(enemyTanks);

                    // 敌人坦克默认出来是向下
                    enemyTank.setDirection(1);

                    // 启动敌人坦克线程
                    new Thread(enemyTank).start();

                    // 给敌人坦克加入子弹
                    Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    bullet.setSpeed(4);
                    enemyTank.bullets.add(bullet);

                    // 启动子弹线程
                    new Thread(bullet).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":  // 继续游戏
                for(int i=0; i<nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    enemyTank.setEnemyTanks(enemyTanks);

                    // 敌人坦克方向
                    enemyTank.setDirection(node.getDirector());

                    // 启动敌人坦克线程
                    new Thread(enemyTank).start();

                    // 给敌人坦克加入子弹
                    Bullet bullet = new Bullet(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    bullet.setSpeed(4);
                    enemyTank.bullets.add(bullet);

                    // 启动子弹线程
                    new Thread(bullet).start();

                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你输入的选择有误");
        }

    }


    /**
     * 输出玩家击毁坦克的信息
     */
    private void showInfo(Graphics g){
        g.setColor(Color.black);
        // 设置字体
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累计击毁坦克数",1020,30);
        drawTank(1020,60,g,0,1);
        g.setColor(Color.black);
        g.drawString(Recorder.getHitEnemyTanks()+"",1080,100);
    }


    /**
     * 绘制面板内容
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 游戏界面
        g.fillRect(0, 0, 1000, 750);  // 填充矩形，默认黑色
        showInfo(g);
        // 绘制玩家坦克
        if (pt != null && pt.isAlive) {
            drawTank(pt.getX(), pt.getY(), g, pt.getDirection(), 0);
        }

        // 绘制玩家坦克的一颗子弹
//        if (pt.bullet != null && pt.bullet.isAlive) {  // 当子弹对象存在并且子弹存活时才画
//            g.fillOval(pt.bullet.x, pt.bullet.y, 2, 2);
//        }

        // 绘制多颗子弹
        for (int i=0; i<pt.bullets.size(); i++) {
            Bullet bullet = pt.bullets.get(i);
            if (bullet != null && bullet.isAlive) {  // 当子弹对象存在并且子弹存活时才画
                g.fillOval(bullet.x, bullet.y, 2, 2);
            }else {  // 若子弹无效，需要将子弹从集合中移开
                pt.bullets.remove(bullet);
            }
        }

        // 绘制敌人坦克
//        Iterator<EnemyTank> iterator = enemyTanks.iterator();
//        while (iterator.hasNext()) {
//            EnemyTank et = iterator.next();
//            if (et.isAlive) {  // 只有当敌方坦克还存活时，才画
//                drawTank(et.getX(), et.getY(), g, et.getDirection(), 1);
//                for (int j = 0; j < et.bullets.size(); j++) {
//                    Bullet bullet = et.bullets.get(j);
//                    if (bullet.isAlive) {
//                        g.fillOval(bullet.x, bullet.y, 2, 2);
//                    } else {
//                        et.bullets.remove(bullet);
//                    }
//                }
//            }else {
//                iterator.remove();
//            }
//        }
        for (int i=0; i<enemyTanks.size(); i++) {
            EnemyTank et = enemyTanks.get(i);
            if (et.isAlive) {  // 只有当敌方坦克还存活时，才画
                drawTank(et.getX(), et.getY(), g, et.getDirection(), 1);
                for (int j = 0; j < et.bullets.size(); j++) {
                    Bullet bullet = et.bullets.get(j);
                    if (bullet.isAlive) {
                        g.fillOval(bullet.x, bullet.y, 2, 2);
                    } else {
                        et.bullets.remove(bullet);
                    }
                }
            }
        }
    }


    /**
     * 坦克形状如下:
     *      -----  |  -----        坦克两边的轮子宽 10像素，高 60像素
     *      |   |--|--|   |        坦克中间部分宽 20，高 40
     *      |   |  |  |   |        坦克炮筒 长 50
     *      |   |  |  |   |
     *      |   |-----|   |
     *      -----     -----
     * @param x 坦克左上角点的横坐标
     * @param y 纵坐标
     * @param g 画笔
     * @param direction 坦克的方向 (0:向上，1:向下，2:向左，3:向右)
     * @param type 坦克类型(0：玩家的坦克，1：敌方坦克)
     */
    private void drawTank(int x, int y, Graphics g, int direction, int type) {
        // 绘制坦克颜色
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        // 根据不同的方向绘制坦克
        switch (direction) {
            case 0:
                // 左轮
                g.fill3DRect(x, y, 10, 60, false);
                // 右轮
                g.fill3DRect(x+30, y, 10, 60, false);
                // 坦克中间部分矩形
                g.fill3DRect(x+10, y+10, 20, 40,false);
                // 中间部分圆形
                g.fillOval(x+10, y+20, 20, 20);
                // 炮筒
                g.drawLine(x+20, y+30, x+20, y);
                break;
            case 1:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x+30, y, 10, 60, false);
                g.fill3DRect(x+10, y+10, 20, 40,false);
                g.fillOval(x+9, y+20, 20, 20);
                g.drawLine(x+20, y+30, x+20, y+60);
                break;
            case 2:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y+30, 60, 10, false);
                g.fill3DRect(x+10, y+10, 40, 20, false);
                g.fillOval(x+20, y+10, 20, 20);
                g.drawLine(x+30, y+20, x, y+20);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y+30, 60, 10, false);
                g.fill3DRect(x+10, y+10, 40, 20, false);
                g.fillOval(x+20, y+10, 20, 20);
                g.drawLine(x+30, y+20, x+60, y+20);
                break;
        }
    }


    /**
     * 判断我方子弹击中敌方坦克
     */
    private void hitEnemyTank() {
        for (int i = 0; i < pt.bullets.size(); i++) {
            Bullet bullet = pt.bullets.get(i);
            // 判断是否击中敌方坦克
            if(bullet != null && bullet.isAlive) {  // 当子弹存活时
                for(EnemyTank et : enemyTanks) {
                    if (et.isAlive) {
                        hitTank(bullet, et);
                    }
                }
            }
        }
    }


    /**
     *  敌方坦克击中我方
     */
    private void hitPlayer() {
        for (int i=0; i<enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j=0; j<enemyTank.bullets.size(); j++) {
                Bullet bullet = enemyTank.bullets.get(j);
                if (pt.isAlive && bullet.isAlive) {
                    hitTank(bullet, pt);
                }
            }
        }
    }


    /**
     * 判断某一颗子弹是否击中坦克。当子弹进入坦克区域时，子弹和敌方坦克一起消失
     */
    private void hitTank(Bullet bullet, Tank tank) {
        switch (tank.getDirection()) {
            case 0:  // 上
            case 1:  // 下
                if (bullet.x > tank.getX() && bullet.x < tank.getX() + 40 &&
                    bullet.y > tank.getY() && bullet.y < tank.getY() + 60) {
                    bullet.isAlive = false;
                    tank.isAlive = false;
                    if (tank instanceof EnemyTank) {
                        Recorder.addHitEnemyTanks();
                    }
                }
                break;
            case 2:  // 左
            case 3:  // 右
                if (bullet.x > tank.getX() && bullet.x < tank.getX() + 60 &&
                    bullet.y > tank.getY() && bullet.y < tank.getY() + 40) {
                    bullet.isAlive = false;
                    tank.isAlive = false;
                    if (tank instanceof EnemyTank) {
                        Recorder.addHitEnemyTanks();
                    }
                }
                break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            // 修改坦克方向
            pt.setDirection(0);
            // 修改坦克坐标
            if (pt.getY() > 0) {
                pt.moveUp();
            }
        }else if(keyCode == KeyEvent.VK_S) {
            pt.setDirection(1);
            if (pt.getY() + 60 < 700) {
                pt.moveDown();
            }
        }else if(keyCode == KeyEvent.VK_A) {
            pt.setDirection(2);
            if (pt.getX() > 0) {
                pt.moveLeft();
            }
        }else if(keyCode == KeyEvent.VK_D) {
            pt.setDirection(3);
            if (pt.getX() + 60 < 980) {
                pt.moveRight();
            }
        }

        /*
         * 按下 J后发射子弹
         */
        if(keyCode == KeyEvent.VK_J) {
            // 当第一次发射子弹后，要等子弹生命周期结束后，才能发射新的子弹(发射一颗子弹的情况)
//            if (pt.bullet == null || !pt.bullet.isAlive) {
//                pt.fireToEnemyTank();
//            }
            pt.fireToEnemyTank();
        }
        // 面板重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 击中敌坦
            hitEnemyTank();
            // 击中我方坦克
            hitPlayer();

//            if (!pt.isAlive || Recorder.getHitEnemyTanks() == enemyTanks.size()) {
//                break;
//            }
            this.repaint();
//            System.out.println(enemyTanks.size());

        }
    }

}
