package VIEW;

import MODEL.Enemy.Enemy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * La classe BoaBoaView gestisce la visualizzazione delle animazioni per il nemico BoaBoa.
 * Si occupa di caricare le immagini per le diverse azioni del nemico e di restituire
 * l'animazione corrente in base allo stato del nemico.
 */
public class BoaBoaView extends EnemyView {

    private BufferedImage[] boaBoaWalk;
    private BufferedImage[] boaBoaDie;
    private BufferedImage[] boaBoaEnraged;
    private BufferedImage[] boaBoaBubbledGreen;

    /**
     * Costruttore della classe BoaBoaView.
     * Inizializza un oggetto BoaBoaView con il nemico fornito.
     *
     * @param enemy il nemico BoaBoa associato a questa vista
     */
    public BoaBoaView(Enemy enemy) {
        super(enemy);
    }

    /**
     * Carica le immagini necessarie per le animazioni di BoaBoa.
     * Le animazioni includono: camminare, morire, essere arrabbiato e
     * stato imbollato con una bolla verde.
     */
    void loadImages() {
        try {
            // Carica le immagini per l'animazione di camminata
            boaBoaWalk = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy56.png")))
            };
            // Carica le immagini per l'animazione della morte
            boaBoaDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy67.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy68.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy69.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy70.png")))
            };
            // Carica le immagini per lo stato di rabbia
            boaBoaEnraged = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Rage/Enemy57.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Rage/Enemy58.png")))
            };
            // Carica le immagini per lo stato imbollato (bolla verde)
            boaBoaBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/GreenBubble/Enemy61.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/GreenBubble/Enemy62.png")))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini che rappresenta l'animazione corrente
     * del nemico BoaBoa in base all'azione corrente.
     *
     * @return l'array di {@code BufferedImage} corrispondente all'azione corrente del nemico
     */
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return boaBoaWalk;
            case DIE:
                return boaBoaDie;
            case RAGE:
                return boaBoaEnraged;
            case BUBBLED:
                return boaBoaBubbledGreen;
        }
    }
}
