package VIEW;

import MODEL.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ExtendPanel extends JPanel {
    private Player player;

    public ExtendPanel(Player player) {
        this.player = player;
        setPreferredSize(new Dimension(70, 550));
        this.setLayout(new FlowLayout());
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<String> collectedBubbles = player.getExtendBubbles();
        String extend = "EXTEND";
        int y = 10;  // Posizione iniziale verticale
        for (char letter : extend.toCharArray()) {
            if (collectedBubbles.contains(String.valueOf(letter))) {
                g.drawImage(getBubbleIcon(letter), 0, y, 40, 40, null);  // Disposizione verticale
            }
            y += 50;  // Spazio tra le bolle in verticale
        }
    }

    private Image getBubbleIcon(char letter) {
        // Metodo per ottenere l'icona in base alla lettera
        return Toolkit.getDefaultToolkit().getImage("src/Resources/Bubble Bobble Resources/Bubbles/extend" + letter + ".png");
    }
}
