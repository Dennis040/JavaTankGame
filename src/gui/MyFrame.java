package gui;

import javax.swing.*;

public class MyFrame extends JFrame {
    public static final int W_FRAME = 575;
    public static final int H_FRAME = 600;

    PanelManager panelManager = new PanelManager();
    //PanelGame panelGame = new PanelGame();


    public MyFrame() {
        setTitle("GAME TANK");
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // add(panelGame);
        add(panelManager);


        setVisible(true);
    }
}
