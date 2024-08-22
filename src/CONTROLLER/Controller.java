package CONTROLLER;

import GAMESTATEMANAGER.GameStateManager;

import java.awt.event.*;

public class Controller implements KeyListener, MouseListener, ActionListener {

    GameStateManager gsm;
    private static Controller instance;

    private Controller() {gsm = GameStateManager.getInstance();}

    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent k) {
        gsm.keyTyped(k);
    }

    @Override
    public void keyPressed(KeyEvent k) {
        gsm.keyPressed(k);
    }

    @Override
    public void keyReleased(KeyEvent k) {
        gsm.keyReleased(k);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gsm.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gsm.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gsm.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        gsm.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        gsm.mouseExited(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gsm.actionPerformed(e);
    }
}
