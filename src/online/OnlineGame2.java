package online;

import gui.MyFrame;
import model.ManagerItem2;
import model.MyTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class OnlineGame2 extends JPanel implements KeyListener, Runnable {
    private Thread thread;
    private boolean isRunning = true;
    private BitSet bitSet = new BitSet(256);
    private ManagerItem2 managerItem2;
    private OnlineGame onlineGame; // Thêm đối tượng OnlineGame

    public OnlineGame2() {
        setSize(MyFrame.W_FRAME, MyFrame.H_FRAME);
        setBackground(Color.BLACK);
        setFocusable(true);
        setLocation(0, 0);
        managerItem2 = new ManagerItem2();
        managerItem2.readMap("map2player.txt");
        addKeyListener(this);


        setRequestFocusEnabled(true);
        setFocusable(true);

        onlineGame = new OnlineGame(); // Khởi tạo OnlineGame

        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        managerItem2.drawMyTank(g2d);
        managerItem2.drawMyTank2(g2d);
        managerItem2.drawBulletOfMyTank(g2d);
        managerItem2.drawAll(g2d);

    }
    // Khai báo biến cờ để kiểm tra xem đã xử lý sự kiện di chuyển chưa
    private boolean tankMoved = false;
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        bitSet.set(keyCode);
        sendKeyPressToServer(keyCode); // Gửi thông tin nhấn phím tới server
    }

    private void sendKeyPressToServer(int keyCode) {
        onlineGame.sendMessage("KEY_PRESS:" + keyCode);
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

            SwingUtilities.invokeLater(this::processServerUpdates);
            SwingUtilities.invokeLater(this::processKeyPress);
            SwingUtilities.invokeLater(this::repaint);
        }

    }

    private void processServerUpdates() {
        for (String update : onlineGame.getGameUpdates()) {
            String[] parts = update.split(":");
            if (parts[0].equals("KEY_PRESS")) {
                int keyCode = Integer.parseInt(parts[1]);
                if (keyCode == KeyEvent.VK_LEFT) {
                    managerItem2.moveMyTank(MyTank.LEFT);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    managerItem2.moveMyTank(MyTank.RIGHT);
                } else if (keyCode == KeyEvent.VK_UP) {
                    managerItem2.moveMyTank(MyTank.UP);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    managerItem2.moveMyTank(MyTank.DOWN);
                } else if (keyCode == KeyEvent.VK_ENTER) {
                    managerItem2.fireBullet();
                }
                if (keyCode == KeyEvent.VK_A) {
                    managerItem2.moveMyTank(MyTank.LEFT);
                } else if (keyCode == KeyEvent.VK_D) {
                    managerItem2.moveMyTank(MyTank.RIGHT);
                } else if (keyCode == KeyEvent.VK_W) {
                    managerItem2.moveMyTank(MyTank.UP);
                } else if (keyCode == KeyEvent.VK_S) {
                    managerItem2.moveMyTank(MyTank.DOWN);
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    managerItem2.fireBullet();
                }
            }
        }
    }

    private void processKeyPress() {
        if (bitSet.get(KeyEvent.VK_LEFT)) {
            managerItem2.moveMyTank(MyTank.LEFT);
        } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
            managerItem2.moveMyTank(MyTank.RIGHT);
        } else if (bitSet.get(KeyEvent.VK_UP)) {
            managerItem2.moveMyTank(MyTank.UP);
        } else if (bitSet.get(KeyEvent.VK_DOWN)) {
            managerItem2.moveMyTank(MyTank.DOWN);

        }
        if (bitSet.get(KeyEvent.VK_ENTER)) {

            managerItem2.fireBullet();
        }

        if (bitSet.get(KeyEvent.VK_A)) {
            managerItem2.moveMyTank2(MyTank.LEFT);
        } else if (bitSet.get(KeyEvent.VK_D)) {
            managerItem2.moveMyTank2(MyTank.RIGHT);
        } else if (bitSet.get(KeyEvent.VK_W)) {
            managerItem2.moveMyTank2(MyTank.UP);
        } else if (bitSet.get(KeyEvent.VK_S)) {
            managerItem2.moveMyTank2(MyTank.DOWN);

        }
        if (bitSet.get(KeyEvent.VK_SPACE)) {

            managerItem2.fireBullet2();
        }
        //moveMyTank();
        moveBulletOfTank();
        //fireOfMyTank();
        managerItem2.interactBulletOfMyTank();
        if (managerItem2.killTank()) {
//            bitSet.clear();
//            int check = JOptionPane.showConfirmDialog(OnlineGame2.this, "Do you want continue", "Player 2 Win", JOptionPane.YES_NO_OPTION);
//            if (check == JOptionPane.YES_OPTION) {
//                managerItem2 = new ManagerItem2();
//                managerItem2.readMap("map2player.txt");
//            } else System.exit(0);
            System.exit(0);
        } else if (managerItem2.killTank2()) {
//            bitSet.clear();
//            int check = JOptionPane.showConfirmDialog(OnlineGame2.this, "Do you want continue", "Player 1 Win", JOptionPane.YES_NO_OPTION);
//            if (check == JOptionPane.YES_OPTION) {
//                managerItem2 = new ManagerItem2();
//                managerItem2.readMap("map2player.txt");
//            } else System.exit(0);
            System.exit(0);
        }
    }

    private void moveBulletOfTank() {
        managerItem2.moveBulletOfMyTank();

    }
}
