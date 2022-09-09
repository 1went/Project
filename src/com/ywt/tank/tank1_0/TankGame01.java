package com.ywt.tank.tank1_0;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * 坦克大战 1.0版本
 */
public class TankGame01 extends JFrame {
    GamePanel gp;

    public static void main(String[] args) {
        new TankGame01();
    }

    public TankGame01() {
        System.out.println("选择1：开始新游戏  2：继续上局游戏");

        gp = new GamePanel(new Scanner(System.in).next());
        // 启动 GamePanel线程
        new Thread(gp).start();
        this.add(gp);
        this.setSize(1300, 750);
        this.addKeyListener(gp);
        // 设置可关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口可见
        this.setVisible(true);

        // 监听窗口关闭，当关闭窗口时，需要相应的写入一些文件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.saveFile();
                // 如果监听到了，就正常退出
                System.exit(0);
            }
        });
    }
}
