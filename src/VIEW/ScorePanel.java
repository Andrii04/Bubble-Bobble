package VIEW;

import MODEL.Player;

import javax.swing.*;
import java.awt.*;


/**
 * ScorePanel è un componente grafico che mostra il punteggio del giocatore
 * durante il gioco. È collegato a un'istanza di {@link Player} da cui ottiene
 * i dati relativi al punteggio.
 */
public class ScorePanel extends JPanel {

    private JLabel scoreLabel; // Label per visualizzare il punteggio del giocatore
    private Player player; // Istanza del giocatore di cui visualizzare il punteggio
    private Font font = MainFrame.getPixelFont(); // Font personalizzato per il testo

    /**
     * Costruttore della classe ScorePanel.
     * Inizializza il pannello con il punteggio corrente del giocatore.
     *
     * @param player L'istanza del giocatore da cui viene recuperato il punteggio.
     */
    public ScorePanel(Player player) {
        this.player = player; // Assegna l'istanza del giocatore al campo player
        this.setLayout(new FlowLayout()); // Imposta un layout di tipo FlowLayout
        this.setOpaque(false); // Rende il pannello trasparente

        // Inizializza l'etichetta per visualizzare il punteggio
        scoreLabel = new JLabel("" + player.getPunteggio()); // Punteggio iniziale
        scoreLabel.setFont(font.deriveFont(20f)); // Imposta il font e la dimensione del testo
        scoreLabel.setForeground(Color.green); // Imposta il colore del testo su verde
        scoreLabel.setVisible(true); // Rende visibile l'etichetta
        this.add(scoreLabel); // Aggiunge l'etichetta al pannello
    }

    /**
     * Aggiorna il punteggio visualizzato nel pannello.
     * Questo metodo dovrebbe essere chiamato ogni volta che il punteggio del giocatore cambia.
     */
    public void updateScore() {
        scoreLabel.setText("" + player.getPunteggio()); // Aggiorna il testo del label con il nuovo punteggio

        // Richiama i metodi revalidate() e repaint() per aggiornare la visualizzazione
        this.revalidate();
        this.repaint();
    }

}
