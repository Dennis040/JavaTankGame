package online;

import gui.MyFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tank Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(MyFrame.W_FRAME, MyFrame.H_FRAME);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        OnlineGame2 onlineGame2 = new OnlineGame2();
        frame.add(onlineGame2);
        frame.setVisible(true);

        onlineGame2.requestFocusInWindow(); // Đảm bảo panel nhận sự kiện bàn phím
    }
}
