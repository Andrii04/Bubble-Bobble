package CONTROLLER;

import MODEL.LeaderboardM;
import MODEL.UserProfile;
import VIEW.LeaderboardPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Leaderboard {
    private LeaderboardM model;
    private LeaderboardPanel view;

    public Leaderboard(LeaderboardM model, LeaderboardPanel view) {
        this.model = model;
        this.view = view;
        updateView(); // Inizializza la vista con i dati correnti
    }

    // Metodo per aggiungere un nuovo profilo
    public void addProfile(String username, int round, int punteggio, int avatarIndex) {
        try {
            UserProfile profile = new UserProfile(username, punteggio, round, avatarIndex);
            model.addProfile(profile);
            updateView(); // Aggiorna la vista dopo aver aggiunto un profilo
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
            updateView(); // Aggiorna la vista dopo aver caricato i dati
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error loading leaderboard from file.");
        }
    }

    // Metodo per aggiornare la vista con i dati del modello
    private void updateView() {
        List<UserProfile> profiles = model.getSortedLeaderboard();

        // Converti la lista di UserProfile in Object[][]
        Object[][] data = new Object[profiles.size()][8];
        for (int i = 0; i < profiles.size(); i++) {
            UserProfile profile = profiles.get(i);
            data[i] = new Object[]{
                    i+1,
                    profile.getAvatarImage(),
                    profile.getUsername(),
                    profile.getPunteggio(),
                    profile.getRound(),
                    profile.getPartiteVinte(),
                    profile.getPartitePerse(),
                    profile.getPartiteTot()
            };
        }

        view.updateLeaderboard(data);
    }
}

