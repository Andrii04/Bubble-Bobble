package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Rappresenta la vista di un nemico di tipo Invader nel gioco, estendendo la classe `EnemyView`.
 */
public class InvaderView extends EnemyView {

    private BufferedImage[] invaderIdle;
    private BufferedImage[] invaderDie;
    private BufferedImage[] invaderEnraged;
    private BufferedImage[] invaderBubbledGreen;

    /**
     * Costruttore per la vista dell'Invader.
     *
     * @param enemy Il nemico da visualizzare.
     */
    public InvaderView(Enemy enemy) {
        super(enemy);
        loadImages(); // Carica le immagini all'inizializzazione
    }

    /**
     * Carica le immagini necessarie per le diverse animazioni dell'Invader.
     */
    void loadImages() {
        try {
            invaderIdle = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle2.png")))
            };
            invaderDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Death/die1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Death/die2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Death/die3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Death/die4.png")))
            };
            invaderEnraged = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Rage/rage1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Rage/rage2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Rage/rage3.png")))
            };
            invaderBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Bubbled/bubble1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Invader/Bubbled/bubble2.png")))
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini correnti in base all'azione del nemico.
     *
     * @return Un array di immagini che rappresentano l'animazione corrente dell'Invader.
     */
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return invaderIdle;
            case DIE:
                return invaderDie;
            case RAGE:
                return invaderEnraged;
            case BUBBLED:
                return invaderBubbledGreen;
        }
    }
}

