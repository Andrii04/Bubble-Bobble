package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class LivesPanel extends JPanel implements Observer {

    private static final ImageIcon LIFE_ICON = new ImageIcon("src/Resources/Bubble Bobble Resources/Bubbles/GreenBubble3.png");
    //non funziona se viene messo il path giusto
    private int lives;

    public LivesPanel(Player player) {
        this.lives = player.getLives();
        player.addObserver(this);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < lives; i++) {
            g.drawImage(LIFE_ICON.getImage(), i * LIFE_ICON.getIconWidth(), 0, this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Player) {
            Player player = (Player) o;
            this.lives = player.getLives();
            repaint();
        }
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LIFE_ICON.getIconWidth() * 5, LIFE_ICON.getIconHeight());
    }
}
