package model;

import java.awt.*;

public class HeartItem extends Item {
    public HeartItem(int id, int x, int y, int size) {
        super(id, x, y, size);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, x, y, size, size, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void interact(MyTank myTank) {
        myTank.increaseHealth();  // Tăng sức khỏe của MyTank khi nhặt trái tim
    }
}
