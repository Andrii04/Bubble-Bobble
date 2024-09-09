package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class LivesPanel extends JPanel {

    private ArrayList<JLabel> lifeIcons;  // Usa ArrayList per gestire dinamicamente le icone
    private Player player;

    public LivesPanel(Player player) {
        this.player = player;
        this.lifeIcons = new ArrayList<>();  // Inizializza l'ArrayList
        this.setLayout(new FlowLayout());
        updateLives();  // Inizializza le vite
        this.setOpaque(false);
    }

    // Metodo per aggiornare il pannello delle vite
    public void updateLives() {
        int currentLives = player.getLives();

        // Aggiungi o rimuovi icone per adattarsi al numero di vite attuali
        if (currentLives > lifeIcons.size()) {
            // Aggiungi icone se le vite sono aumentate
            for (int i = lifeIcons.size(); i < currentLives; i++) {
                JLabel lifeIcon = new JLabel(new ImageIcon("src/Resources/Bubble Bobble Resources/Bubbles/GreenBubble3.png"));
                lifeIcons.add(lifeIcon);
                this.add(lifeIcon);
            }
        } else if (currentLives < lifeIcons.size()) {
            // Rimuovi icone se le vite sono diminuite
            for (int i = lifeIcons.size() - 1; i >= currentLives; i--) {
                this.remove(lifeIcons.get(i));  // Rimuovi dal JPanel
                lifeIcons.remove(i);            // Rimuovi dall'ArrayList
            }
        }

        // Repaint per garantire che le modifiche siano visibili
        this.revalidate();
        this.repaint();
    }
}