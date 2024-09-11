package VIEW;

import MODEL.PowerUps.PowerUp;

import javax.swing.*;
import java.awt.*;

/**
 * La classe PowerUpView gestisce la visualizzazione e l'animazione degli oggetti di tipo PowerUp.
 */
public class PowerUpView {
    private PowerUp powerUp;  // Oggetto PowerUp associato alla vista
    private int explodingAnimationTimer;  // Timer per gestire l'animazione di esplosione

    /**
     * Costruttore della classe PowerUpView.
     *
     * @param powerUp L'oggetto PowerUp da visualizzare.
     */
    public PowerUpView(PowerUp powerUp) {
        this.powerUp = powerUp;
        this.explodingAnimationTimer = 0;  // Inizializza il timer di esplosione
    }

    /**
     * Disegna l'oggetto PowerUp sulla superficie grafica.
     *
     * @param g2d L'oggetto Graphics2D utilizzato per disegnare l'oggetto PowerUp.
     */
    public void draw(Graphics2D g2d) {
        if (!powerUp.isErased() && (!powerUp.isActive() || powerUp.isExploding())) {
            // Disegna l'immagine dell'oggetto PowerUp e il suo rettangolo di collisione
            g2d.drawImage(powerUp.getSkin().getImage(), powerUp.getX(), powerUp.getY(), null);
            g2d.draw(powerUp.getHitbox());
        }
    }

    /**
     * Aggiorna lo stato dell'oggetto PowerUp, inclusa la gestione dell'animazione di esplosione.
     */
    public void update() {
        if (!powerUp.isErased()) {
            if (!powerUp.isActive()) {
                // Aggiorna la posizione dell'oggetto PowerUp se non è attivo
                powerUp.updateLocation();
            } else if (powerUp.isExploding()) {
                // Gestisce l'animazione di esplosione
                explodingAnimationTimer++;
                if (explodingAnimationTimer >= 15) {
                    powerUp.stopExplosion();  // Ferma l'animazione di esplosione dopo un certo tempo
                }
            }
        }
    }

    /**
     * Imposta l'immagine di esplosione per l'oggetto PowerUp.
     */
    public void setExplodeIMG() {
        String skinPath = "/Resources/Bubble Bobble Resources/Bubbles/BlueBubble5.png";
        // Imposta l'immagine di esplosione per l'oggetto PowerUp
        powerUp.setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }
}