package CONTROLLER;

import MODEL.LeaderboardM;
import MODEL.UserProfile;
import VIEW.LeaderboardPanel;

import javax.swing.*;
import java.io.IOException;

public class Leaderboard {
    private LeaderboardM model;
    private LeaderboardPanel view;

    public Leaderboard(LeaderboardM model, LeaderboardPanel view) {
        this.model = model;
        this.view = view;
        updateView();
    }

    // Metodo per aggiungere un nuovo profilo
    public void addProfile(String username, int round, int punteggio, int avatarIndex) {
        try {
            UserProfile profile = new UserProfile(username, punteggio, round, avatarIndex);
            model.addProfile(profile);
            updateView();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    // Metodo per salvare la leaderboard su file
    public void saveLeaderboard(String filename) {
        try {
            model.saveToFile(filename);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error saving leaderboard to file.");
        }
    }

    // Metodo per caricare la leaderboard da file
    public void loadLeaderboard(String filename) {
        try {
            model = LeaderboardM.loadFromFile(filename);
            updateView();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error loading leaderboard from file.");
        }
    }

    private void updateView() {
        //view.updateLeaderboard(model.getSortedLeaderboard());
    }
}

