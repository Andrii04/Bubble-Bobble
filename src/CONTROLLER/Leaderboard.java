package CONTROLLER;

import MODEL.LeaderboardM;
import MODEL.UserProfile;
import VIEW.LeaderboardPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
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

    // Metodo per aggiornare la vista con i dati del modello
    public void updateView() {
        List<UserProfile> profiles = model.getSortedUsers();
        if(profiles == null) return;
        // Converti la lista di UserProfile in Object[][]
        Object[][] data = new Object[profiles.size()][8];
        for (int i = 0; i < profiles.size(); i++) {
            UserProfile profile = profiles.get(i);
            ImageIcon image = null;
            try{
                image = new ImageIcon(ImageIO.read(new File(profile.getAvatarImage())));}
            catch(IOException e){
                e.printStackTrace();
            }
            data[i] = new Object[]{
                    image,
                    profile.getUsername(),
                    profile.getRound(),
                    profile.getPunteggio(),
                    profile.getPartiteVinte(),
                    profile.getPartitePerse(),
                    profile.getPartiteTot()
            };
        }

        view.updateLeaderboard(data);
    }



public LeaderboardM getModel() {
        return model;
    }

}

