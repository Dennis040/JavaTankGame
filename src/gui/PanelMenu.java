package gui;

import javax.sound.sampled.Clip;
import javax.swing.*;

import sound.Sound;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelMenu extends JPanel implements KeyListener{
    private PanelManager panelManager;

    public PanelMenu( PanelManager panelManager) {
    initPanelMenu();
    initComp();
    initListener();
    this.panelManager = panelManager;


    }
    private JButton jbStart;
    private JButton jbHelp;
    private JButton jbExit;

    public static final String START = "start";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    private void initListener() {
        jbStart.addActionListener(this::actionPerformed);
        jbStart.setActionCommand(START);
        jbHelp.addActionListener(this::actionPerformed);
        jbHelp.setActionCommand(HELP);
        jbExit.addActionListener(this::actionPerformed);
        jbExit.setActionCommand(EXIT);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String run = actionEvent.getActionCommand();
        switch (run) {
            case START: {
                Clip clip = Sound.getSound(getClass().getResource("/sound/shoot.wav"));
                clip.start();
                panelManager.showCard(PanelManager.PANEL_GAME);
                break;
            }

            case HELP: {
                Clip clip = Sound.getSound(getClass().getResource("/sound/shoot.wav"));
                clip.start();
                panelManager.showCard(PanelManager.PANEL_HELP);
                break;
            }
            case EXIT: {
                Clip clip = Sound.getSound(getClass().getResource("/sound/shoot.wav"));
                clip.start();
                System.exit(0);
            }
        }
    }


    private void initPanelMenu() {
        setLayout(null);
    }
    private void initComp() {
        jbStart = new JButton(imgStart[0]);
        jbStart.setRolloverIcon(imgStart[1]);
        jbStart.setSize(imgStart[0].getIconWidth(),imgStart[0].getIconHeight());
        jbStart.setLocation(220,250);
        add(jbStart);


        jbHelp = new JButton(imgHelp[0]);
        jbHelp.setRolloverIcon(imgHelp[1]);
        jbHelp.setSize(imgHelp[0].getIconWidth(),imgHelp[0].getIconHeight());
        jbHelp.setLocation(220,320);
        add(jbHelp);

        jbExit = new JButton(imgExit[0]);
        jbExit.setRolloverIcon(imgExit[1]);
        jbExit.setSize(imgExit[0].getIconWidth(),imgExit[0].getIconHeight());
        jbExit.setLocation(220,390);
        add(jbExit);
    }

    public final Icon[] imgStart = {
            new ImageIcon(getClass().getResource("/image/menu/star5.png")),
            new ImageIcon(getClass().getResource("/image/menu/star.png")),
    };
    public final Icon[] imgHelp = {
            new ImageIcon(getClass().getResource("/image/menu/help.png")),
            new ImageIcon(getClass().getResource("/image/menu/help2.png")),
    };
    public final Icon[] imgExit = {
            new ImageIcon(getClass().getResource("/image/menu/exit.png")),
            new ImageIcon(getClass().getResource("/image/menu/Untitled-1.png")),
    };
    protected void paintComponent(Graphics graphics) {
        super.paintChildren(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        Image img = new ImageIcon(getClass().getResource("/image/icon.jpg")).getImage();
        graphics2D.drawImage(img, 0, 0, MyFrame.W_FRAME, MyFrame.H_FRAME, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
