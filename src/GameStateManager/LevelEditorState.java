package GameStateManager;

import View.MainFrame;

import java.awt.event.KeyEvent;

public class LevelEditorState extends GameState {
    public LevelEditorState() {
    }

    public void update() {
    }

    public void draw() {
        MainFrame.setPanel(MainFrame.getLevelEditorPanel());
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

