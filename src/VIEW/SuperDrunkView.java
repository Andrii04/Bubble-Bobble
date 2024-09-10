package VIEW;

import MODEL.Enemy.Enemy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Rappresenta la vista grafica del nemico SuperDrunk.
 * <p>
 * Questa classe estende {@code EnemyView} e gestisce le animazioni e le immagini
 * per il nemico SuperDrunk in base alle sue azioni e direzioni. Le immagini vengono
 * caricate e scalate per adattarsi alla visualizzazione.
 * </p>
 */
public class SuperDrunkView extends EnemyView {
    BufferedImage[] SuperdrunkMoveRight;
    BufferedImage[] SuperdrunkMoveLeft;
    BufferedImage[] SuperdrunkHurtLeft;
    BufferedImage[] SuperdrunkHurtRight;
    BufferedImage[] SuperdrunkBubbledLeft;
    BufferedImage[] SuperdrunkBubbledRight;
    BufferedImage[] SuperdrunkStart;
    BufferedImage[] SuperdrunkRageLeft;
    BufferedImage[] SuperdrunkRageRight;

    /**
     * Costruisce un'istanza della classe {@code SuperDrunkView}.
     * <p>
     * Inizializza la vista grafica per il nemico SuperDrunk utilizzando le immagini
     * caricate e scalate.
     * </p>
     *
     * @param enemy Il nemico SuperDrunk associato a questa vista.
     */
    public SuperDrunkView(Enemy enemy) {
        super(enemy);
    }

    /**
     * Carica le immagini necessarie per le animazioni del nemico SuperDrunk.
     * <p>
     * Le immagini vengono caricate da risorse e scalate per la visualizzazione.
     * </p>
     */
    public void loadImages() {
        try {
            SuperdrunkMoveLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Walk/Walk1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Walk/Walk2.png")))
            };
            SuperdrunkMoveRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Walk/Walk1Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Walk/Walk2Right.png")))
            };
            SuperdrunkBubbledLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Bubbled/Bubbled1.png"))),
            };
            SuperdrunkBubbledRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Bubbled/Bubbled1Right.png"))),
            };
            SuperdrunkHurtLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Hurt/Hurt1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Hurt/Hurt2.png"))),
            };
            SuperdrunkHurtRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Hurt/Hurt1Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Hurt/Hurt2Right.png"))),
            };
            SuperdrunkStart = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Start/Start.png"))),
            };
            SuperdrunkRageLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Hurt/Rage.png")))
            };
            SuperdrunkRageRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/SuperDrunk/Hurt/RageRight.png")))
            };


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'array di immagini correnti da visualizzare in base all'azione del nemico.
     * <p>
     * L'animazione restituita dipende dall'azione corrente del nemico e dalla sua direzione.
     * </p>
     *
     * @return L'array di immagini per l'animazione corrente.
     */
    @Override
    public BufferedImage[] getCurrentAnimation() {
        switch (currentAction) {
            default:
                return enemy.getFacingRight() ? SuperdrunkMoveRight : SuperdrunkMoveLeft;
            case HURT:
                return enemy.getFacingRight() ? SuperdrunkHurtRight : SuperdrunkHurtLeft;
            case BUBBLED:
                return enemy.getFacingRight() ? SuperdrunkBubbledRight : SuperdrunkBubbledLeft;
            case IDLE:
                return SuperdrunkStart;
            case RAGE:
                return enemy.getFacingRight() ? SuperdrunkRageRight : SuperdrunkRageLeft;
        }
    }
}

