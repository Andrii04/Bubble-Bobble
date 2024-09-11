package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Rappresenta la vista di un nemico di tipo Incendio nel gioco, estendendo la classe `EnemyView`.
 */
public class IncendioView extends EnemyView {

    private BufferedImage[] incendioWalkRight;
    private BufferedImage[] incendioWalkLeft;
    private BufferedImage[] incendioDie;
    private BufferedImage[] incendioEnragedLeft;
    private BufferedImage[] incendioEnragedRight;
    private BufferedImage[] incendioBubbledGreen;

    /**
     * Costruttore per la vista dell'Incendio.
     *
     * @param enemy Il nemico da visualizzare.
     */
    public IncendioView(Enemy enemy) {
        super(enemy);
        loadImages(); // Carica le immagini all'inizializzazione
    }

    /**
     * Carica le immagini necessarie per le diverse animazioni dell'Incendio.
     */
    void loadImages() {
        try {
            incendioWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk2.png")))
            };
            incendioWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk2Right.png")))
            };
            incendioDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Death/Enemy92.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Death/Enemy99.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Death/Enemy100.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Death/Enemy101.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Death/Enemy102.png")))
            };
            incendioEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy89.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy90.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy91.png")))
            };
            incendioEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy89Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy90Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy91Right.png")))
            };
            incendioBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/GreenBubble/Enemy93.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/GreenBubble/Enemy94.png")))
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini correnti in base all'azione del nemico e alla direzione.
     *
     * @return Un array di immagini che rappresentano l'animazione corrente dell'Incendio.
     */
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return enemy.getFacingRight() ? incendioWalkRight : incendioWalkLeft;
            case DIE:
                return incendioDie;
            case RAGE:
                return enemy.getFacingRight() ? incendioEnragedRight : incendioEnragedLeft;
            case BUBBLED:
                return incendioBubbledGreen;
        }
    }
}
