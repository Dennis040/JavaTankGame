package gui;

import javax.sound.sampled.Clip;
import javax.swing.*;
import sound.Sound;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelHelp extends JPanel implements ActionListener{
    private JButton jbBack;

    private PanelManager panelManager;

    public static final String BACK = "back";

    public PanelHelp(PanelManager panelManager) {
        setLayout(null);
        initButton();
        initListenner();
        this.panelManager = panelManager;
    }

    public void initButton() {
        jbBack = new JButton(images[0]);
        jbBack.setRolloverIcon(images[1]);
        jbBack.setSize(images[0].getIconWidth(), images[0].getIconHeight());
        jbBack.setLocation(20, 20);
        add(jbBack);
    }
    public final Icon[] images = {
            new ImageIcon(getClass().getResource("/image/back1.png")),
            new ImageIcon(getClass().getResource("/image/back2.png")),
    };
    public void initListenner() {
        jbBack.addActionListener(this);
        jbBack.setActionCommand(BACK);
    }
    protected void paintComponent(Graphics graphics) {
        super.paintChildren(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        Image img = new ImageIcon(getClass().getResource("/image/MenuHelp.jpg")).getImage();
        graphics2D.drawImage(img, 0, 0, MyFrame.W_FRAME, MyFrame.H_FRAME, null);
    }

    @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String run = actionEvent.getActionCommand();
            ;
            switch (run) {
                case BACK: {
                    Clip clip = Sound.getSound(getClass().getResource("/sound/shoot.wav"));
                    clip.start();
                    panelManager.showCard(PanelManager.PANEL_MENU);
                    break;
                }
                default:
                    break;
            }
        }
}
