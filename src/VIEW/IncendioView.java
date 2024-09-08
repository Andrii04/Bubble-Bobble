package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class IncendioView extends EnemyView{
    BufferedImage[] incendioWalkRight;
    BufferedImage[] incendioWalkLeft;
    BufferedImage[] incendioDie;
    BufferedImage[] incendioEnragedLeft;
    BufferedImage[] incendioEnragedRight;
    BufferedImage[] incendioBubbledGreen;
    BufferedImage[] incendioBubbledBlue;
    BufferedImage[] incendioBubbledRed;
    public IncendioView(Enemy enemy) {
        super(enemy);
    }
    void loadImages(){
        try{
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
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Death/Enemy102.png"))),
            };
            incendioEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy89.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy90.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy91.png"))),
            };
            incendioEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy89Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy90Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Rage/Enemy91Right.png"))),
            };
            incendioBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/GreenBubble/Enemy93.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/GreenBubble/Enemy94.png")))
            };
            incendioBubbledBlue = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/BlueBubble/Enemy95.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/BlueBubble/Enemy96.png")))
            };
            incendioBubbledRed = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/RedBubble/Enemy97.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Incendio/Bubbled/RedBubble/Enemy98.png")))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BufferedImage[] getCurrentAnimation(){
        switch(currentAction) {
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
