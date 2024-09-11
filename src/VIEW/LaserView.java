package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.Laser;

import javax.swing.*;
import java.awt.*;

/**
 * Rappresenta la vista di un laser nel gioco, estendendo la classe `BubbleView`.
 */
public class LaserView extends BubbleView {

    /**
     * Costruttore per la vista del laser.
     *
     * @param bubble Il laser da visualizzare.
     */
    public LaserView(Bubble bubble) {
        super(bubble);
    }

    /**
     * Inizia a sparare il laser, aggiornando l'immagine del laser e la sua dimensione.
     */
    @Override
    public void startFiring() {
        currentSkinPath = bubble.getSkinsPath() + "2.png";
        Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }

    /**
     * Aggiorna la posizione del laser e gestisce l'animazione di esplosione se necessario.
     */
    @Override
    public void update() {
        if (firing) {
            bubble.updateLocation(bubble.getX(), bubble.getY() + shootingSpeed);
        } else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 15) bubble.erase();
        }
    }

    /**
     * Imposta l'immagine di esplosione del laser.
     */
    @Override
    public void setExplodeIMG() {
        currentSkinPath = bubble.getSkinsPath() + "3.png";
        Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }
}