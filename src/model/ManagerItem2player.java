package model;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class ManagerItem2player {
    long currentTimeFireMyTank = 0;
    Item home;
    private ArrayList<Item> items;
    //private MyTank myTank;
    private MyTank2 myTank2;
    private MyTank3 myTank3;

    public ManagerItem2player() {

        items = new ArrayList<>();
        int x = 200;
        int y = 580;
        int x1 = 20;
        int y1 = 20;
        int orientation = MyTank.UP;
        int id = Images.ID_TANKS[orientation];
        myTank3 = new MyTank3(id, x, y, 35, orientation);
        myTank2 = new MyTank2(id, x1, y1, 35, orientation);
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

    public void drawAll(Graphics2D g2d) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.draw(g2d);
        }
    }

    public void drawMyTank(Graphics2D g2d) {
        myTank3.draw(g2d);

    }

    public void drawMyTank2(Graphics2D g2d) {
        myTank2.draw(g2d);

    }

    public void moveMyTank(int orientation) {
        myTank3.move(orientation, items);
    }

    public void moveMyTank2(int orientation) {
        myTank2.move(orientation, items);
    }

    public void fireBullet() {
        long time = System.currentTimeMillis();
        if (time - currentTimeFireMyTank > 500) {
            myTank3.fireBullet();
            currentTimeFireMyTank = time;
        }
    }

    public void fireBullet2() {
        long time = System.currentTimeMillis();
        if (time - currentTimeFireMyTank > 500) {
            myTank2.fireBullet();
            currentTimeFireMyTank = time;
        }
    }

    public void drawBulletOfMyTank(Graphics2D g2d) {
        myTank3.drawAllBullet(g2d);
        myTank2.drawAllBullet(g2d);
    }

    public void moveBulletOfMyTank() {
        myTank3.moveAllBullet();
        myTank2.moveAllBullet();
    }

    public void interactBulletOfMyTank() {
        myTank3.interactBullet(items);
        myTank2.interactBullet(items);
    }

    public boolean killTank() {

        boolean isKill = myTank3.killTank(myTank2);
        if (isKill) {
            return true;
        }
        return false;
    }

    public boolean killTank2() {
        boolean isKill = myTank2.killTank(myTank3);
        if (isKill) {
            return true;
        }
        return false;
    }
}
