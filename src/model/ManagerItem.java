package model;

import sound.Sound;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class ManagerItem {
    long currentTimeFireMyTank = 0;
    long currentMoveEnemy;
    long currentfireEnemyTank;
    Item home;

    private ArrayList<HeartItem> heartItems;
    private ArrayList<Item> items;
    private ArrayList<EnemyTank> enemyTanks;
    private MyTank myTank;

    public ManagerItem() {
        items = new ArrayList<>();
        int x = 200;
        int y = 580;
        int orientation = MyTank.UP;
        int id = Images.ID_TANKS[orientation];
        myTank = new MyTank(id, x, y, 35, orientation);
        enemyTanks = new ArrayList<>();
        heartItems = new ArrayList<>();

        createEnemy();
        createHeart();
    }

    public ArrayList<HeartItem> getHeartItems() {
        return heartItems;
    }

    public ArrayList<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public void createHeart() {
        HeartItem heart1 = new HeartItem(Images.HEART_ID, 100, 100, 20);
        items.add(heart1);
        HeartItem heart2 = new HeartItem(Images.HEART_ID, 720, 70, 20);
        items.add(heart2);
        HeartItem heart3 = new HeartItem(Images.HEART_ID, 840, 540, 20);
        items.add(heart3);
    }

    public void createEnemy() {
        int x1 = 20;
        int y1 = 20;
        int ori1 = MyTank.DOWN;
        int size1 = 36;

        EnemyTank enemyTank1 = new EnemyTank(Images.ID_ENEMY_TANKS[ori1],
                x1, y1, size1, ori1);

        enemyTanks.add(enemyTank1);

        int x2 = 8 * 20;
        int y2 = 20;
        int ori2 = MyTank.DOWN;
        int size2 = 36;

        EnemyTank enemyTank2 = new EnemyTank(Images.ID_ENEMY_TANKS[ori2],
                x2, y2, size2, ori2);
        enemyTanks.add(enemyTank2);

        int x3 = 20 * 20;
        int y3 = 20;
        int ori3 = MyTank.DOWN;
        int size3 = 36;

        EnemyTank enemyTank3 = new EnemyTank(Images.ID_ENEMY_TANKS[ori3],
                x3, y3, size3, ori3);

        enemyTanks.add(enemyTank3);

        int x4 = 13 * 20;
        int y4 = 80;
        int ori4 = MyTank.DOWN;
        int size4 = 36;

        EnemyTank enemyTank4 = new EnemyTank(Images.ID_ENEMY_TANKS[ori4],
                x4, y4, size4, ori4);

        enemyTanks.add(enemyTank4);


        int x5 = 4 * 20;
        int y5 = 14 * 20;
        int ori5 = MyTank.RIGHT;
        int size5 = 36;

        EnemyTank enemyTank5 = new EnemyTank(Images.ID_ENEMY_TANKS[ori5],
                x5, y5, size5, ori5);

        enemyTanks.add(enemyTank5);

        int x6 = 21 * 20;
        int y6 = 16 * 20;
        int ori6 = MyTank.RIGHT;
        int size6 = 36;

        EnemyTank enemyTank6 = new EnemyTank(Images.ID_ENEMY_TANKS[ori6],
                x6, y6, size6, ori6);
        enemyTanks.add(enemyTank6);

        int x7 = 2 * 20;
        int y7 = 8 * 20;
        int ori7 = MyTank.RIGHT;
        int size7 = 36;

        EnemyTank enemyTank7 = new EnemyTank(Images.ID_ENEMY_TANKS[ori7],
                x7, y7, size7, ori7);
        enemyTanks.add(enemyTank7);

        int x8 = 5 * 20;
        int y8 = 17 * 20;
        int ori8 = MyTank.RIGHT;
        int size8 = 36;

        EnemyTank enemyTank8 = new EnemyTank(Images.ID_ENEMY_TANKS[ori8],
                x8, y8, size8, ori8);
        enemyTanks.add(enemyTank8);

    }

    public void drawAllHeartItems(Graphics2D g2d) {
        for (HeartItem heartItem : heartItems) {
            heartItem.draw(g2d);
        }
    }

    public void drawEnemyAllTank(Graphics2D g2d) {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            enemyTank.draw(g2d);
        }
    }

    public void readMap(String map) {
        File file = new File("src/map/" + map);
        try {
            RandomAccessFile rd = new RandomAccessFile(file, "rw");
            String content = rd.readLine();
            int index = 0;
            while (content != null) {
                for (int i = 0; i < content.length(); i++) {
                    int id = content.charAt(i) - '0';
                    if (id == 0) {
                        continue;
                    }
                    int x = i * 20;
                    int y = index * 20;

                    Item item;
                    if (id == 9) {
                        item = new Item(id, x, y, 40);
                        home = item;
                    } else if (id == 17) { // Assuming a specific ID for hearts
                        item = new HeartItem(id, x, y, 20);
                    } else {
                        item = new Item(id, x, y, 20);
                    }
                    items.add(item);

                }
                index++;
                content = rd.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawAllItem(Graphics2D g2d) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.draw(g2d);
        }
    }

    public void drawMyTank(Graphics2D g2d) {
        myTank.draw(g2d);
    }

    public void moveMyTank(int direction) {
        myTank.move(direction, items);
        myTank.interactWithHearts(heartItems); // Tương tác với các HeartItem
        // Kiểm tra và giảm sức khỏe của xe tăng nếu trúng đạn từ EnemyTank
        if (myTank.interactWithEnemyTanks(enemyTanks)) {
            myTank.decreaseHealth(); // Gọi phương thức để giảm sức khỏe của xe tăng
        }
    }


    public void fireBullet() {
        long time = System.currentTimeMillis();
        if (time - currentTimeFireMyTank > 500) {
            myTank.fireBullet();
            currentTimeFireMyTank = time;
        }
    }

    public void drawBulletOfMyTank(Graphics2D g2d) {
        myTank.drawAllBullet(g2d);
    }

    public void moveBulletOfMyTank() {
        myTank.moveAllBullet();
    }

    public void interactBulletOfMyTank() {
        myTank.interactBullet(items);
    }

    public void interactBulletOfAllEnemyTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            enemyTank.interactBullet(items);
        }
    }

    public void interactBulletOfAllEnemyTankWithMyTank() {
        for (EnemyTank enemyTank : enemyTanks) {
            enemyTank.moveAllBullet();
            enemyTank.interactBulletWithMyTank(myTank);
        }
    }

    public void moveAllEnemyTank() {
        long current = System.currentTimeMillis();
        if (current - currentMoveEnemy < 25) {
            return;
        }
        currentMoveEnemy = current;
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            enemyTank.move(items);
        }
    }

    public void fireEnemyTank() {
        long time = System.currentTimeMillis();
        if (time - currentfireEnemyTank < 3500) {
            return;
        }
        currentfireEnemyTank = time;
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            enemyTank.fireBullet();
        }
    }

    public void drawAllBulletEnemyTank(Graphics2D g2d) {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            enemyTank.drawAllBullet(g2d);
        }
    }

    public void moveAllBulletEnemyTank() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            enemyTank.moveAllBullet();
        }
    }

    public void killEnemyTank() {

        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            boolean isKill = myTank.killEnemyTank(enemyTank);
            if (isKill) {
                // enemyTank.imgExp();
                enemyTanks.remove(i);
                i--;

                Clip clip = Sound.getSound(getClass().getResource("/sound/explosion.wav"));
                clip.start();
            }
        }
    }

    public boolean checkGameOver() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            boolean isKill = enemyTank.killMyTank(myTank);
            if (isKill || myTank.getHealth() == 0) {
                return true;
            }
        }

        if (killHome()) {
            Clip clip = Sound.getSound(getClass().getResource("/sound/explosion_tank.wav"));
            clip.start();
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        if (enemyTanks.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean killHome() {
        boolean killFromMyTank = myTank.killHome(home);
        if (killFromMyTank) {
            return true;
        }
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.killHome(home)) {
                Clip clip = Sound.getSound(getClass().getResource("/sound/explosion_tank.wav"));
                clip.start();
                return true;
            }
        }
        return false;
    }
}
