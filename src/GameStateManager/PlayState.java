package GameStateManager;

import View.MainFrame;

import java.awt.event.KeyEvent;

public class PlayState extends GameState {
    public PlayState() {}

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        MainFrame.setPanel(MainFrame.getPlayPanel());
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
