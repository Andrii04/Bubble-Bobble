package VIEW;

import MODEL.Enemy.Enemy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * La classe BenzoView gestisce la visualizzazione dell'animazione per il nemico Benzo.
 * Carica le immagini corrispondenti alle diverse azioni e fornisce l'animazione corrente
 * in base allo stato del nemico.
 */
public class BenzoView extends EnemyView {

    private BufferedImage[] benzoWalkRight;
    private BufferedImage[] benzoWalkLeft;
    private BufferedImage[] benzoDie;
    private BufferedImage[] benzoEnragedLeft;
    private BufferedImage[] benzoEnragedRight;
    private BufferedImage[] benzoBubbledGreen;

    /**
     * Costruttore della classe BenzoView.
     * Inizializza l'oggetto BenzoView con il nemico specificato.
     *
     * @param benzo l'istanza del nemico Benzo
     */
    public BenzoView(Enemy benzo) {
        super(benzo);
    }

    /**
     * Carica le immagini per le varie animazioni del nemico Benzo.
     * Include animazioni per camminare a sinistra e a destra, morire,
     * stato di rabbia e stato in una bolla verde.
     */
    void loadImages() {
        try {
            // Carica le immagini per camminare a sinistra
            benzoWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy2.png")))
            };
            // Carica le immagini per camminare a destra
            benzoWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy2Right.png")))
            };
            // Carica le immagini per l'animazione della morte
            benzoDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy13.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy14.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy15.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy16.png"))),
            };
            // Carica le immagini per lo stato di rabbia (sinistra)
            benzoEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy4.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy5.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy6.png")))
            };
            // Carica le immagini per lo stato di rabbia (destra)
            benzoEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy3Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy4Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy5Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy6Right.png")))
            };
            // Carica le immagini per lo stato imbollato (bolla verde)
            benzoBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/GreenBubble/Enemy7.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/GreenBubble/Enemy8.png"))),
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini corrispondente all'azione corrente del nemico Benzo.
     *
     * @return l'array di {@code BufferedImage} corrispondente all'azione corrente
     */
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return enemy.getFacingRight() ? benzoWalkRight : benzoWalkLeft;
            case DIE:
                return benzoDie;
            case RAGE:
                return enemy.getFacingRight() ? benzoEnragedRight : benzoEnragedLeft;
            case BUBBLED:
                return benzoBubbledGreen;
        }
    }
}


