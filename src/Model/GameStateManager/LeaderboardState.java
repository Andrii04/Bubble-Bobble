package Model.GameStateManager;

import View.MainFrame;

import java.awt.event.KeyEvent;

public class LeaderboardState extends GameState {
    public LeaderboardState() {}

    public void update() {}

    public void draw() {
        MainFrame.setPanel(MainFrame.getLeaderboardPanel());
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
