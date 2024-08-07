
package model;

import java.awt.*;
import java.util.ArrayList;

public class MyTank3 extends Item {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    int orientation;
    ArrayList<Bullet> bullets;

    public MyTank3(int id, int x, int y, int size, int orientation) {
        super(id, x, y, size);
        this.orientation = orientation;
        bullets = new ArrayList<Bullet>();
    }

    public void move(int orientation, ArrayList<Item> items) {
        id = Images.ID_TANKS[orientation];
        img = Images.getImage(id);
        this.orientation = orientation;
        switch (orientation) {
            case LEFT:
                x = x - 1;
                boolean isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    x = x + 1;
                }
                break;
            case RIGHT:
                x = x + 1;
                isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    x = x - 1;
                }
                break;
            case UP:
                y = y - 1;
                isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    y = y + 1;
                }
                break;
            case DOWN:
                y = y + 1;
                isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    y = y - 1;
                }
                break;
        }
    }

    boolean interactWithItems(ArrayList<Item> items) {
        Rectangle rect1 = new Rectangle(x, y, size, size);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.id == Images.TREE_ID) {
                continue;
            }

            Rectangle rect2 = new Rectangle(item.x, item.y, item.size, item.size);
            if (rect1.intersects(rect2) == true) {
                return true;
            }

        }
        return false;
    }

    public void fireBullet() {
        int sizeB = 20;
        int orB = orientation;
        int xB;
        int yB;
        switch (orientation) {
            case LEFT:
                //Đặt tọa độ x của viên đạn ở phía bên trái xe tăng.
                xB = x - sizeB;
                //Đặt tọa độ y của viên đạn ở giữa chiều cao của xe tăng.
                yB = y + (size - sizeB) / 2;
                break;
            case RIGHT:
                xB = x + size;
                yB = y + (size - sizeB) / 2;
                break;
            case UP:
                xB = x + (size - sizeB) / 2;
                yB = y - sizeB;
                break;

            default:
                xB = x + (size - sizeB) / 2;
                yB = y + size;
                break;
        }

        Bullet bullet = new Bullet(Images.BULLET_ID, xB, yB, sizeB, orB);
        bullets.add(bullet);
    }

    public void drawAllBullet(Graphics2D g2d) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.draw(g2d);
        }

    }

    public void moveAllBullet() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.move();
        }

    }

    public void interactBullet(ArrayList<Item> items) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            Rectangle rect1 = new Rectangle(bullet.x, bullet.y, bullet.size, bullet.size);
            for (int j = 0; j < items.size(); j++) {
                Item item = items.get(j);
                if (item.id == Images.TREE_ID) {
                    continue;
                }
                if (item.id == Images.WATER_ID) {
                    continue;
                }

                Rectangle rect2 = new Rectangle(item.x, item.y, item.size, item.size);
                if (rect1.intersects(rect2) == true) {
                    if (item.id == Images.ROCK_ID) {
                        bullets.remove(i);
                        return;
                    }

                    if (item.id == Images.BRICK_ID) {
                        bullets.remove(i);
                        items.remove(j);
                        return;
                    }
                }
            }
        }

    }

    public boolean killTank(MyTank2 myTank) {
        Rectangle rect1 = new Rectangle(myTank.x, myTank.y, myTank.size, myTank.size);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            Rectangle rect2 = new Rectangle(bullet.x, bullet.y, bullet.size, bullet.size);
            if (rect1.intersects(rect2)) {
                return true;
            }
        }
        return false;

    }

    public boolean killHome(Item home) {
        Rectangle rect1 = new Rectangle(home.x, home.y, home.size, home.size);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            Rectangle rect2 = new Rectangle(bullet.x, bullet.y, bullet.size, bullet.size);
            if (rect1.intersects(rect2)) {
                return true;
            }
        }
        return false;
    }


}
