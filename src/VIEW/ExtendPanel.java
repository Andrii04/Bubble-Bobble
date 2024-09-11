package VIEW;

import MODEL.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Pannello che visualizza le bolle estese raccolte dal giocatore.
 */
public class ExtendPanel extends JPanel {
    private Player player;

    /**
     * Costruttore per il pannello delle bolle estese.
     *
     * @param player Il giocatore il cui stato delle bolle estese viene visualizzato.
     */
    public ExtendPanel(Player player) {
        this.player = player;
        setPreferredSize(new Dimension(70, 550));
        this.setLayout(new FlowLayout());
        this.setOpaque(false);
    }

    /**
     * Disegna il pannello, mostrando le bolle estese raccolte come icone verticali.
     *
     * @param g L'oggetto Graphics utilizzato per il disegno.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<String> collectedBubbles = player.getExtendBubbles();
        String extend = "EXTEND";
        int y = 10;  // Posizione iniziale verticale

        // Disegna le bolle estese raccolte
        for (char letter : extend.toCharArray()) {
            if (collectedBubbles.contains(String.valueOf(letter))) {
                g.drawImage(getBubbleIcon(letter), 0, y, 40, 40, null);  // Disposizione verticale
            }
            y += 50;  // Spazio tra le bolle in verticale
        }
    }

    /**
     * Ottiene l'icona della bolla in base alla lettera.
     *
     * @param letter La lettera per cui ottenere l'icona della bolla.
     * @return L'immagine dell'icona della bolla.
     */
    private Image getBubbleIcon(char letter) {
        // Metodo per ottenere l'icona in base alla lettera
        return Toolkit.getDefaultToolkit().getImage("src/Resources/Bubble Bobble Resources/Bubbles/extend" + letter + ".png");
    }
}