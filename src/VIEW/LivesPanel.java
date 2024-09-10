package VIEW;


import MODEL.Player;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Pannello per visualizzare le icone delle vite del giocatore.
 * <p>
 * Questo pannello mostra un numero dinamico di icone che rappresentano le vite rimanenti del giocatore.
 * Le icone vengono aggiunte o rimosse in base al numero di vite attuali del giocatore.
 * </p>
 */
public class LivesPanel extends JPanel {

    private ArrayList<JLabel> lifeIcons;  // Usa ArrayList per gestire dinamicamente le icone
    private Player player;

    /**
     * Costruisce un'istanza della classe {@code LivesPanel} e inizializza l'interfaccia utente.
     * <p>
     * Configura il layout del pannello e inizializza le icone delle vite in base al numero di vite del giocatore.
     * </p>
     *
     * @param player Il giocatore di cui visualizzare le vite.
     */
    public LivesPanel(Player player) {
        this.player = player;
        this.lifeIcons = new ArrayList<>();  // Inizializza l'ArrayList
        this.setLayout(new FlowLayout());
        updateLives();  // Inizializza le vite
        this.setOpaque(false);
    }

    /**
     * Aggiorna il pannello delle vite in base al numero di vite attuali del giocatore.
     * <p>
     * Aggiunge o rimuove le icone delle vite per adattarsi al numero corrente di vite del giocatore.
     * Se il numero di vite aumenta, aggiunge nuove icone. Se il numero di vite diminuisce, rimuove le icone in eccesso.
     * </p>
     */
    public void updateLives() {
        int currentLives = player.getLives();
        if (currentLives < 0) {
            currentLives = 0;  // Assicurati che il numero di vite non sia negativo
        }

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