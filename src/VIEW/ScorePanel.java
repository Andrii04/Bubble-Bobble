package VIEW;

import MODEL.Player;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private JLabel scoreLabel; // Label per visualizzare i punti
    private Player player;
    private Font font = MainFrame.getPixelFont();

    public ScorePanel(Player player) {
        this.player = player; // Assegna il parametro player al campo player
        this.setLayout(new FlowLayout());
        this.setOpaque(false);

        // Inizializza il label con il punteggio iniziale
        scoreLabel = new JLabel("" + player.getPunteggio());
        scoreLabel.setFont(font.deriveFont(20f)); // Imposta font e dimensione
        scoreLabel.setForeground(Color.green);
        scoreLabel.setVisible(true);
        this.add(scoreLabel);
    }

    // Metodo per aggiornare il punteggio visualizzato
    public void updateScore() {
        scoreLabel.setText(""+player.getPunteggio());

        // Repaint per assicurare che le modifiche siano visibili
        this.revalidate();
        this.repaint();
    }

}
