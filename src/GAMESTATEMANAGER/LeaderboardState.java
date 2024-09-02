package GAMESTATEMANAGER;

import CONTROLLER.Leaderboard;
import MODEL.LeaderboardM;
import VIEW.LeaderboardPanel;
import VIEW.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LeaderboardState extends GameState {

    private Leaderboard leaderboardController; // Il controller della leaderboard
    private LeaderboardM model;  // Il modello della leaderboard
    private LeaderboardPanel leaderboardPanel; // Il pannello della leaderboard


    public LeaderboardState() {
        model = new LeaderboardM();
        leaderboardPanel = MainFrame.getLeaderboardPanel();
        leaderboardController = new Leaderboard(model, leaderboardPanel);
        leaderboardController.loadLeaderboard("leaderboard.txt");

    }

    public void update() {}

    public void draw() {

        //leaderboardController.updateView();
        MainFrame.setPanel(leaderboardPanel);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
