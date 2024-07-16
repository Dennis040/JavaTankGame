package gui;

import model.ManagerItem;
import model.MyTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class PanelGame extends JPanel implements KeyListener, Runnable {

    private Thread thread;
    private boolean isLeft;
    private boolean isRight;
    private boolean isUp;
    private boolean isDown;
    private boolean isFire;
    private boolean isRunning = true;
    private BitSet bitSet = new BitSet(256);
    private ManagerItem managerItem;

    public PanelGame() {
        setSize(MyFrame.W_FRAME, MyFrame.H_FRAME);
        setBackground(Color.BLACK);
        setFocusable(true);
        setLocation(0, 0);
        managerItem = new ManagerItem();
        managerItem.readMap("map1.txt");
        addKeyListener(this);

        setRequestFocusEnabled(true);
        setFocusable(true);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        managerItem.drawMyTank(g2d);
        managerItem.drawEnemyAllTank(g2d);
        managerItem.drawBulletOfMyTank(g2d);
        managerItem.drawAllBulletEnemyTank(g2d);
        managerItem.drawAllItem(g2d);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        bitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear(e.getKeyCode());
    }

    @Override
    public void run() {

        while (isRunning) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bitSet.get(KeyEvent.VK_LEFT)) {
                managerItem.moveMyTank(MyTank.LEFT);
            } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
                managerItem.moveMyTank(MyTank.RIGHT);
            } else if (bitSet.get(KeyEvent.VK_UP)) {
                managerItem.moveMyTank(MyTank.UP);
            } else if (bitSet.get(KeyEvent.VK_DOWN)) {
                managerItem.moveMyTank(MyTank.DOWN);
            }
            if (bitSet.get(KeyEvent.VK_SPACE)) {
                managerItem.fireBullet();
            }
            //moveMyTank();
            moveBulletOfTank();
            //fireOfMyTank();
            managerItem.interactBulletOfMyTank();

            managerItem.moveAllEnemyTank();
            managerItem.moveAllBulletEnemyTank();
            managerItem.fireEnemyTank();
            managerItem.interactBulletOfAllEnemyTank();
            managerItem.killEnemyTank();
            managerItem.interactBulletOfAllEnemyTankWithMyTank();

            if (managerItem.checkGameOver()) {
                bitSet.clear();
                int check = JOptionPane.showConfirmDialog(PanelGame.this, "Do you want continue", "Game Over", JOptionPane.YES_NO_OPTION);
                if (check == JOptionPane.YES_OPTION) {
                    managerItem = new ManagerItem();
                    managerItem.readMap("map1.txt");
                } else System.exit(0);
            }
            if (managerItem.checkWin()) {
                bitSet.clear();
                managerItem = new ManagerItem();
                managerItem.readMap("map2.txt");
                if (managerItem.checkWin()) {
                    int end = JOptionPane.showConfirmDialog(PanelGame.this, "You Win\nYou want to reply", "End Game", JOptionPane.YES_NO_OPTION);
                    if (end == JOptionPane.YES_NO_OPTION) {
                        bitSet.clear();
                        managerItem = new ManagerItem();
                        managerItem.readMap("map1.txt");
                    }
                    break;
                }
            }
            repaint();
        }

    }

    private void moveBulletOfTank() {
        managerItem.moveBulletOfMyTank();
    }

    void fireOfMyTank() {
        if (isFire) {
            System.out.println("fire");
            managerItem.fireBullet();
        }
    }

    void moveMyTank() {
        if (isDown) {
            managerItem.moveMyTank(MyTank.DOWN);

        } else {
            if (isUp) {
                managerItem.moveMyTank(MyTank.UP);

            } else {
                if (isRight) {
                    managerItem.moveMyTank(MyTank.RIGHT);

                } else {
                    if (isLeft) {
                        managerItem.moveMyTank(MyTank.LEFT);

                    }

                }
            }
        }

    }


}

