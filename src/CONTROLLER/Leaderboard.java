package CONTROLLER;

import MODEL.LeaderboardM;
import MODEL.UserProfile;
import VIEW.LeaderboardPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * La classe Leaderboard è responsabile di collegare il modello dei dati della classifica (LeaderboardM)
 * con la vista (LeaderboardPanel), gestendo l'aggiornamento della vista con i dati correnti.
 */
public class Leaderboard {
    private LeaderboardM model; // Riferimento al modello della classifica
    private LeaderboardPanel view; // Riferimento alla vista della classifica

    /**
     * Costruttore che inizializza il modello e la vista della classifica.
     *
     * @param model il modello dei dati della classifica (LeaderboardM)
     * @param view il pannello che visualizza la classifica (LeaderboardPanel)
     */
    public Leaderboard(LeaderboardM model, LeaderboardPanel view) {
        this.model = model;
        this.view = view;
        updateView(); // Inizializza la vista con i dati correnti del modello
    }

    /**
     * Metodo che aggiorna la vista della classifica con i dati più recenti dal modello.
     * Converte la lista di profili utente in una struttura dati adatta alla visualizzazione.
     */
    public void updateView() {
        List<UserProfile> profiles = model.getSortedUsers(); // Ottiene gli utenti ordinati dal modello
        if (profiles == null) return; // Controlla che la lista di profili non sia nulla

        // Converte la lista di UserProfile in una matrice Object[][] per la visualizzazione
        Object[][] data = new Object[profiles.size()][8];
        for (int i = 0; i < profiles.size(); i++) {
            UserProfile profile = profiles.get(i);
            ImageIcon image = null;
            try {
                // Carica l'immagine dell'avatar del profilo
                image = new ImageIcon(ImageIO.read(new File(profile.getAvatarImage())));
            } catch (IOException e) {
                e.printStackTrace(); // Gestisce eventuali errori nel caricamento dell'immagine
            }

            // Popola la riga con i dati del profilo utente
            data[i] = new Object[]{
                    image, // Avatar dell'utente
                    profile.getUsername(), // Nome utente
                    profile.getRound(), // Numero di round raggiunti
                    profile.getPunteggio(), // Punteggio totale
                    profile.getPartiteVinte(), // Numero di partite vinte
                    profile.getPartitePerse(), // Numero di partite perse
                    profile.getPartiteTot() // Numero totale di partite giocate
            };
        }

        // Aggiorna la vista della classifica con i nuovi dati
        view.updateLeaderboard(data);
    }

    /**
     * Metodo getter per ottenere il modello della classifica.
     *
     * @return il modello dei dati della classifica (LeaderboardM)
     */
    public LeaderboardM getModel() {
        return model;
    }
}

