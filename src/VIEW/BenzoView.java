package VIEW;

import MODEL.Enemy.Enemy;
import MODEL.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

public class BenzoView extends EnemyView {

    BufferedImage[] benzoWalkRight;
    BufferedImage[] benzoWalkLeft;
    BufferedImage[] benzoDie;
    BufferedImage[] benzoEnragedLeft;
    BufferedImage[] benzoEnragedRight;
    BufferedImage[] benzoBubbledGreen;

    public BenzoView(Enemy benzo){
        super(benzo);
    }

    void loadImages() {
        try {
            benzoWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy2.png")))
            };
            benzoWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy2Right.png")))
            };
            benzoDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy13.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy14.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy15.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Death/Enemy16.png"))),
            };
            // enraged left and right
            benzoEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy4.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy5.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy6.png")))
            };
            benzoEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy3Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy4Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy5Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy6Right.png")))
            };
            benzoBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/GreenBubble/Enemy7.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/GreenBubble/Enemy8.png"))),
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
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


