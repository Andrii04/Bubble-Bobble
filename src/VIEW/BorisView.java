package VIEW;

import MODEL.Enemy.Enemy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * La classe BorisView gestisce la visualizzazione delle animazioni per il nemico Boris.
 * Si occupa di caricare le immagini per le diverse azioni del nemico e di restituire
 * l'animazione corrente in base allo stato del nemico.
 */

public class BorisView extends EnemyView {

    private BufferedImage[] borisWalkRight;
    private BufferedImage[] borisWalkLeft;
    private BufferedImage[] borisDie;
    private BufferedImage[] borisEnragedLeft;
    private BufferedImage[] borisEnragedRight;
    private BufferedImage[] borisBubbledGreen;

    /**
     * Costruttore della classe BorisView.
     * Inizializza un oggetto BorisView con il nemico fornito.
     *
     * @param enemy il nemico Boris associato a questa vista
     */
    public BorisView(Enemy enemy) {
        super(enemy);
    }

    /**
     * Carica le immagini necessarie per le animazioni di Boris.
     * Le animazioni includono: camminata a destra e a sinistra, morte, stato di rabbia,
     * e stato imbollato con una bolla verde.
     */
    void loadImages() {
        try {
            // Carica le immagini per l'animazione di camminata a sinistra
            borisWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy18.png")))
            };
            // Carica le immagini per l'animazione di camminata a destra
            borisWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy18Right.png")))
            };
            // Carica le immagini per l'animazione della morte
            borisDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy29.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy30.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy31.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy32.png")))
            };
            // Carica le immagini per lo stato di rabbia a sinistra
            borisEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy19.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy20.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy21.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy22.png")))
            };
            // Carica le immagini per lo stato di rabbia a destra
            borisEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy19Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy20Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy21Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy22Right.png")))
            };
            // Carica le immagini per lo stato imbollato (bolla verde)
            borisBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/GreenBubble/Enemy23.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/GreenBubble/Enemy24.png")))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini che rappresenta l'animazione corrente
     * del nemico Boris in base all'azione corrente.
     *
     * @return l'array di {@code BufferedImage} corrispondente all'azione corrente del nemico
     */
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return enemy.getFacingRight() ? borisWalkRight : borisWalkLeft;
            case DIE:
                return borisDie;
            case RAGE:
                return enemy.getFacingRight() ? borisEnragedRight : borisEnragedLeft;
            case BUBBLED:
                return borisBubbledGreen;
        }
    }
}