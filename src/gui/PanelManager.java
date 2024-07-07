package gui;

import online.OnlineGame;
import sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class PanelManager extends JPanel {
    public static final String PANEL_GAME = "Panel Game";
    public static final String PANEL_MENU = "Panel Menu";
    public static final String PANEL_HELP = "Panel Help";
    public static final String PANEL_GAME2 = "Panel Game2";
    CardLayout cardLayout;
    private PanelGame panelGame;
    private PanelMenu panelMenu;
    private PanelHelp panelHelp;
    private PanelGame2 panelGame2;
    private Clip clip;

    public PanelManager() {
        clip = Sound.getSound(getClass().getResource("/sound/enter_game.wav"));
        panelGame = new PanelGame();
        panelGame2 = new PanelGame2();
        panelMenu = new PanelMenu(this);
        panelHelp = new PanelHelp(this);
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        add(panelGame, PANEL_GAME);
        add(panelMenu, PANEL_MENU);
        add(panelHelp, PANEL_HELP);
        add(panelGame2, PANEL_GAME2);
        clip.start();
        clip.loop(50);
        cardLayout.show(this, PANEL_MENU);
        addKeyListener(panelGame);
        addKeyListener(panelGame2);
        setFocusable(true);
        clip.stop();
    }

    public void showCard(String name) {
        if (name == PANEL_GAME) {
            cardLayout.show(this, name);
            clip.stop();
            panelGame = new PanelGame();

        } else if (name == PANEL_MENU) {
            cardLayout.show(this, PANEL_MENU);
            clip.stop();
        } else if (name == PANEL_HELP) {
            cardLayout.show(this, name);
            clip.stop();
        }else if (name == PANEL_GAME2) {
            cardLayout.show(this, name);
            clip.stop();
            panelGame2 = new PanelGame2();
        }
    }
}
