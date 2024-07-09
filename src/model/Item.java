package model;

import java.awt.*;

public class Item {
    Image img;
    int id;
    int x, y;
    int size;

    public Item(int id, int x, int y, int size) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.size = size;
        this.img = Images.getImage(this.id);
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(img, x, y, size, size, null);
    }

    public void drawHeart(Graphics2D g2d) {
        g2d.drawImage(img, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
    }
}


