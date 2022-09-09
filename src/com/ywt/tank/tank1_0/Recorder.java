package com.ywt.tank.tank1_0;


import java.io.*;
import java.util.Vector;

/**
 * 记录玩家击毁敌人坦克的数量并且将其写入磁盘保存
 */
public class Recorder {
    // 击毁坦克数
    private static int hitEnemyTanks = 0;
    // 敌人坦克
    private static Vector<EnemyTank> enemyTanks = null;
    // 定义一个 Node的 Vector，用于恢复坦克的位置
    private static Vector<Node> nodes = new Vector<>();

    // 定义 IO对象用于文件写入
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static final String destFile = "F:\\interest\\后端\\java\\Project\\src\\com\\ywt\\tank\\tank1_0\\recorder.txt";

    /**
     * 该方法在继续游戏时，用于恢复游戏中的数据
     */
    public static Vector<Node> readFile() {
        try {
            br = new BufferedReader(new FileReader(destFile));
            hitEnemyTanks = Integer.parseInt(br.readLine());
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] s = line.split(" ");
                Node node = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)  {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }

    /**
     * 将玩家击毁的坦克数量和退出游戏时敌人坦克的位置保存起来
     */
    public static void saveFile() {
        try {
            Recorder.bw = new BufferedWriter(new FileWriter(destFile));
            // 保存玩家击毁的坦克数量
            bw.write(Recorder.hitEnemyTanks+"");
            bw.newLine();
            // 记录退出时，敌人坦克的坐标和方向
            for (EnemyTank enemyTank : enemyTanks) {
                if (enemyTank.isAlive) {
                    String location = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection();
                    bw.write(location);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Recorder.bw != null) {
                    Recorder.bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static int getHitEnemyTanks() {
        return hitEnemyTanks;
    }

    public static void setHitEnemyTanks(int hitEnemyTanks) {
        Recorder.hitEnemyTanks = hitEnemyTanks;
    }

    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getDestFile() {
        return destFile;
    }

    public static void addHitEnemyTanks() {
        Recorder.hitEnemyTanks++;
    }

}
