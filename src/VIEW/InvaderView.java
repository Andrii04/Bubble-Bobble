package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class InvaderView extends EnemyView {
    BufferedImage[] invaderIdle;
    BufferedImage[] invaderDie;
    BufferedImage[] invaderEnraged;
    BufferedImage[] invaderBubbledGreen;

    public InvaderView(Enemy enemy) {
        super(enemy);
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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

