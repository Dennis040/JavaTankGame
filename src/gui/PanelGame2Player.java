package gui;

import model.ManagerItem2player;
import model.MyTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class PanelGame2Player extends JPanel implements KeyListener, Runnable {

    private Thread thread;
    private boolean isLeft;
    private boolean isRight;
    private boolean isUp;
    private boolean isDown;
    private boolean isFire;
    private boolean isRunning = true;
    private BitSet bitSet = new BitSet(256);
    private ManagerItem2player managerItem2Player;
    private PanelManager panelManager;

    public PanelGame2Player() {
        setSize(MyFrame.W_FRAME, MyFrame.H_FRAME);
        setBackground(Color.BLACK);
        setFocusable(true);
        setLocation(0, 0);
        managerItem2Player = new ManagerItem2player();
        managerItem2Player.readMap("map2player.txt");
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
        managerItem2Player.drawMyTank(g2d);
        managerItem2Player.drawMyTank2(g2d);
        managerItem2Player.drawBulletOfMyTank(g2d);
        managerItem2Player.drawAll(g2d);

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
                managerItem2Player.moveMyTank(MyTank.LEFT);
            } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
                managerItem2Player.moveMyTank(MyTank.RIGHT);
            } else if (bitSet.get(KeyEvent.VK_UP)) {
                managerItem2Player.moveMyTank(MyTank.UP);
            } else if (bitSet.get(KeyEvent.VK_DOWN)) {
                managerItem2Player.moveMyTank(MyTank.DOWN);

            }
            if (bitSet.get(KeyEvent.VK_ENTER)) {

                managerItem2Player.fireBullet();
            }

            if (bitSet.get(KeyEvent.VK_A)) {
                managerItem2Player.moveMyTank2(MyTank.LEFT);
            } else if (bitSet.get(KeyEvent.VK_D)) {
                managerItem2Player.moveMyTank2(MyTank.RIGHT);
            } else if (bitSet.get(KeyEvent.VK_W)) {
                managerItem2Player.moveMyTank2(MyTank.UP);
            } else if (bitSet.get(KeyEvent.VK_S)) {
                managerItem2Player.moveMyTank2(MyTank.DOWN);

            }
            if (bitSet.get(KeyEvent.VK_SPACE)) {

                managerItem2Player.fireBullet2();
            }
            //moveMyTank();
            moveBulletOfTank();
            //fireOfMyTank();
            managerItem2Player.interactBulletOfMyTank();
            if (managerItem2Player.killTank()) {
                bitSet.clear();
                int check = JOptionPane.showConfirmDialog(PanelGame2Player.this, "Do you want continue", "Player 2 Win", JOptionPane.YES_NO_OPTION);
                if (check == JOptionPane.YES_OPTION) {
                    managerItem2Player = new ManagerItem2player();
                    managerItem2Player.readMap("map2player.txt");
                } else System.exit(0);
            } else if (managerItem2Player.killTank2()) {
                bitSet.clear();
                int check = JOptionPane.showConfirmDialog(PanelGame2Player.this, "Do you want continue", "Player 1 Win", JOptionPane.YES_NO_OPTION);
                if (check == JOptionPane.YES_OPTION) {
                    managerItem2Player = new ManagerItem2player();
                    managerItem2Player.readMap("map2player.txt");
                } else System.exit(0);
            }
//            if (managerItem.checkWin()) {
//                bitSet.clear();
//                managerItem = new ManagerItem();
//                managerItem.readMap("map2.txt");
//                if (managerItem.checkWin()) {
//                    int end = JOptionPane.showConfirmDialog(PanelGame.this, "You Win\nYou want to reply", "End Game", JOptionPane.YES_NO_OPTION);
//                    if (end == JOptionPane.YES_NO_OPTION) {
//                        bitSet.clear();
//                        managerItem = new ManagerItem();
//                        managerItem.readMap("map1.txt");
//                    }
//                    break;
//                }
//            }
            repaint();
        }

    }

    private void moveBulletOfTank() {
        managerItem2Player.moveBulletOfMyTank();

    }


}

