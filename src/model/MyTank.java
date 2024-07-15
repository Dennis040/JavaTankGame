package model;

import java.awt.*;
import java.util.ArrayList;

public class MyTank extends Item {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    int orientation;
    ArrayList<Bullet> bullets;
    private int health;
    private int maxHealth;

    public MyTank(int id, int x, int y, int size, int orientation) {
        super(id, x, y, size);
        this.orientation = orientation;
        bullets = new ArrayList<Bullet>();
        health = 3;
    }

    public MyTank(int id, int x, int y, int size, int orientation, int maxHealth) {
        super(id, x, y, size);
        this.orientation = orientation;
        bullets = new ArrayList<Bullet>();
        this.maxHealth = maxHealth;
        health = 3;
    }

    public void increaseHealth() {
        health += 1;
        if (health > maxHealth)
            health = maxHealth;
        System.out.println("Health increased to: " + health);
    }

    public void increaseHealth(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        super.draw(g2d);  // Gọi phương thức draw của lớp cha để vẽ hình ảnh của xe tăng

        // Vẽ thanh máu
        int barWidth = size;  // Chiều rộng của thanh máu
        int barHeight = 5;    // Chiều cao của thanh máu

        // Chiều rộng thanh máu dựa vào tỷ lệ sức khỏe
        int healthBarWidth = (int) ((health / (double) maxHealth) * barWidth);
        if (healthBarWidth < 0) {
            healthBarWidth = 0;
        }  // Đảm bảo thanh máu không có chiều rộng âm

        // Vẽ nền của thanh máu
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y + size + 2, barWidth, barHeight);  // Vẽ nền thanh máu

        // Vẽ thanh máu
        g2d.setColor(Color.RED);
//        g2d.fillRect(x, y + size + 2, healthBarWidth, barHeight);
        g2d.fillRect(x, y + size + 2, barWidth, barHeight);

        // Vẽ viền của thanh máu
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y + size + 2, barWidth, barHeight);  // Vẽ viền thanh máu
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
            if (rect1.intersects(rect2)) {
                if (item instanceof HeartItem) {
                    ((HeartItem) item).interact(this);

                    items.remove(i);
                    i--;
                    continue;
                } else {
                    return true; // Chỉ trả về true nếu va chạm với vật cản
                }
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

    public boolean killEnemyTank(EnemyTank enemyTank) {
        Rectangle rect1 = new Rectangle(enemyTank.x, enemyTank.y, enemyTank.size, enemyTank.size);
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

    public boolean killTank(MyTank2 myTank2) {
        Rectangle rect1 = new Rectangle(myTank2.x, myTank2.y, myTank2.size, myTank2.size);
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
