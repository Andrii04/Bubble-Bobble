package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.Fireball;
import MODEL.Enemy.Boris;
import MODEL.Enemy.Enemy;

import javax.swing.*;
import java.awt.*;

/**
 * Rappresenta la vista di una palla di fuoco (Fireball) nel gioco, estendendo la classe `BubbleView`.
 */
public class FireballView extends BubbleView {

    private Enemy enemy;
    private boolean facingRight;

    /**
     * Costruttore per la vista della palla di fuoco.
     *
     * @param bubble La palla di fuoco da visualizzare.
     * @param enemy  Il nemico associato alla palla di fuoco.
     */
    public FireballView(Fireball bubble, Enemy enemy) {
        super(bubble);
        this.enemy = enemy;
    }

    /**
     * Inizia il processo di sparo della palla di fuoco, impostando l'immagine corretta in base alla direzione.
     */
    @Override
    public void startFiring() {
        distanceTraveled = 0;
        facingRight = enemy.getFacingRight();
        if (facingRight) {
            currentSkinPath = bubble.getSkinsPath() + "2.png";
        } else {
            currentSkinPath = bubble.getSkinsPath() + "1.png";
        }
        ImageIcon skin = new ImageIcon(getClass().getResource(currentSkinPath));
        Image img = skin.getImage().getScaledInstance(skin.getIconWidth() * 2, skin.getIconHeight() * 2, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(img);
    }

    /**
     * Aggiorna la posizione della palla di fuoco e gestisce l'animazione di esplosione.
     */
    @Override
    public void update() {
        if (firing) {
            if (facingRight) {
                bubble.updateLocation(bubble.getX() + shootingSpeed, bubble.getY());
            } else {
                bubble.updateLocation(bubble.getX() - shootingSpeed, bubble.getY());
            }

            if (distanceTraveled >= 60) {
                bubble.explode();
            }
        } else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 10 && explodingAnimationTimer < 20) {
                currentSkinPath = bubble.getSkinsPath() + "6.png";
                ImageIcon skin = new ImageIcon(getClass().getResource(currentSkinPath));
                Image img = skin.getImage().getScaledInstance(skin.getIconWidth() * 2, skin.getIconHeight() * 2, Image.SCALE_SMOOTH);
                currentSkin = new ImageIcon(img);
            } else if (explodingAnimationTimer >= 20) {
                bubble.stopExplosion();
                bubble.erase();
            }
        }
    }

    /**
     * Imposta l'immagine dell'esplosione per la palla di fuoco.
     */
    @Override
    public void setExplodeIMG() {
        currentSkinPath = bubble.getSkinsPath() + "5.png";
        ImageIcon skin = new ImageIcon(getClass().getResource(currentSkinPath));
        Image img = skin.getImage().getScaledInstance(skin.getIconWidth() * 2, skin.getIconHeight() * 2, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(img);
    }
}
