package VIEW;

import MODEL.Bubbles.Bubble;

import javax.swing.*;
import java.awt.*;

/**
 * La classe BottleView gestisce la visualizzazione e l'animazione delle bolle speciali a forma di bottiglia.
 * Si occupa di animare la rotazione della bottiglia durante la fase di "firing" e gestisce anche
 * l'aggiornamento dello stato di esplosione.
 */
public class BottleView extends BubbleView {

    private int bottleRotationTimer;

    /**
     * Costruttore della classe BottleView.
     * Inizializza una nuova istanza di BottleView con la bolla specificata e imposta il timer di rotazione a 0.
     *
     * @param bubble l'oggetto Bubble associato a questa vista
     */
    public BottleView(Bubble bubble) {
        super(bubble);
        bottleRotationTimer = 0;
    }

    /**
     * Metodo che avvia l'animazione di "firing" della bottiglia.
     * Imposta l'immagine della bottiglia iniziale.
     */
    @Override
    public void startFiring() {
        currentSkinPath = bubble.getSkinsPath() + "5.png";
        ImageIcon originalSkin = new ImageIcon(currentSkinPath);
        setSkin(originalSkin);
    }

    /**
     * Imposta la skin corrente (immagine) della bottiglia ridimensionandola a 32x32 pixel.
     *
     * @param skin l'oggetto {@code ImageIcon} che rappresenta la skin da impostare
     */
    public void setSkin(ImageIcon skin) {
        Image resizedSkin = skin.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }

    /**
     * Aggiorna lo stato della bolla e gestisce l'animazione della rotazione della bottiglia.
     * Ogni ciclo incrementa il timer di rotazione e cambia l'immagine della bottiglia in base
     * al valore del timer. Gestisce anche l'animazione di esplosione e l'aggiornamento della posizione
     * della bolla quando sta sparando.
     */
    @Override
    public void update() {
        // Animazione della rotazione della bottiglia durante il "firing"
        if (firing) {
            bottleRotationTimer++;
            if (bottleRotationTimer >= 5 && bottleRotationTimer < 11) {
                currentSkinPath = bubble.getSkinsPath() + "6.png";
                ImageIcon originalSkin = new ImageIcon(getClass().getResource(currentSkinPath));
                setSkin(originalSkin);
            } else if (bottleRotationTimer >= 11 && bottleRotationTimer < 16) {
                currentSkinPath = bubble.getSkinsPath() + "7.png";
                ImageIcon originalSkin = new ImageIcon(getClass().getResource(currentSkinPath));
                setSkin(originalSkin);
            } else if (bottleRotationTimer >= 16 && bottleRotationTimer < 21) {
                currentSkinPath = bubble.getSkinsPath() + "8.png";
                ImageIcon originalSkin = new ImageIcon(getClass().getResource(currentSkinPath));
                setSkin(originalSkin);
            } else if (bottleRotationTimer >= 21) {
                currentSkinPath = bubble.getSkinsPath() + "5.png";
                ImageIcon originalSkin = new ImageIcon(getClass().getResource(currentSkinPath));
                setSkin(originalSkin);
                bottleRotationTimer = 0;
            }
        }

        // Aggiornamento della posizione della bolla se sta sparando
        if (firing) {
            bubble.updateLocation();
        }
        // Gestione dell'animazione di esplosione
        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 15) {
                bubble.erase();
            }
        }
    }

    /**
     * Imposta l'immagine corrente della bolla quando esplode.
     * Poiché non c'è un'immagine specifica per l'esplosione delle bottiglie,
     * viene utilizzata un'immagine predefinita di una bolla blu.
     */
    @Override
    public void setExplodeIMG() {
        // Non esiste un'immagine specifica di esplosione per le bottiglie, quindi ne usiamo una di default
        currentSkinPath = "/Resources/Bubble Bobble Resources/Bubbles/BlueBubble5.png";
        Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }
}