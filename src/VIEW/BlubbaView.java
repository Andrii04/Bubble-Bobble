package VIEW;

import MODEL.Enemy.Enemy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * La classe BlubbaView gestisce la visualizzazione dell'animazione per il nemico Blubba.
 * Carica le immagini corrispondenti alle diverse azioni e fornisce l'animazione corrente
 * in base allo stato del nemico.
 */
public class BlubbaView extends EnemyView {

    private BufferedImage[] blubbaWalkRight;
    private BufferedImage[] blubbaWalkLeft;
    private BufferedImage[] blubbaDie;
    private BufferedImage[] blubbaEnragedLeft;
    private BufferedImage[] blubbaEnragedRight;
    private BufferedImage[] blubbaBubbledGreen;

    /**
     * Costruttore della classe BlubbaView.
     * Inizializza l'oggetto BlubbaView con il nemico specificato.
     *
     * @param blubba l'istanza del nemico Blubba
     */
    public BlubbaView(Enemy blubba) {
        super(blubba);
    }

    /**
     * Carica le immagini per le varie animazioni del nemico Blubba.
     * Include animazioni per camminare a sinistra e a destra, morire,
     * stato di rabbia e stato in una bolla verde.
     */
    void loadImages() {
        try {
            // Carica le immagini per camminare a sinistra
            blubbaWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy40.png")))
            };
            // Carica le immagini per camminare a destra
            blubbaWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy40Right.png")))
            };
            // Carica le immagini per l'animazione della morte
            blubbaDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy51.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy52.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy53.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy54.png")))
            };
            // Carica le immagini per lo stato di rabbia (sinistra)
            blubbaEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy41.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy42.png")))
            };
            // Carica le immagini per lo stato di rabbia (destra)
            blubbaEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy41Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy42Right.png")))
            };
            // Carica le immagini per lo stato imbollato (bolla verde)
            blubbaBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/GreenBubble/Enemy45.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/GreenBubble/Enemy46.png")))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini corrispondente all'azione corrente del nemico Blubba.
     *
     * @return l'array di {@code BufferedImage} corrispondente all'azione corrente
     */
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return enemy.getFacingRight() ? blubbaWalkRight : blubbaWalkLeft;
            case DIE:
                return blubbaDie;
            case RAGE:
                return enemy.getFacingRight() ? blubbaEnragedRight : blubbaEnragedLeft;
            case BUBBLED:
                return blubbaBubbledGreen;
        }
    }
}