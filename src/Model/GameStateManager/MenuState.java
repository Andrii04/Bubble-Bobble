package Model.GameStateManager;

import View.MainFrame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
    public MenuState() {}

    public void update() {}

    @Override
    public void draw() {
            MainFrame.setPanel(MainFrame.getMenuPanel());
    }

    @Override
    public void keyTyped(KeyEvent k) {

    }

    @Override
    public void keyPressed(KeyEvent k) {

    }

    @Override
    public void keyReleased(KeyEvent k) {

    }
}
