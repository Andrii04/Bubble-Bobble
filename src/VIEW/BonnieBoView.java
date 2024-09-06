package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BonnieBoView extends EnemyView{
    BufferedImage[] bonnieBoWalkLeft;
    BufferedImage[] bonnieBoWalkRight;
    BufferedImage[] bonnieBoDie;
    BufferedImage[] bonnieBoEnragedLeft;
    BufferedImage[] bonnieBoEnragedRight;
    BufferedImage[] bonnieBoBubbledGreen;
    BufferedImage[] bonnieBoBubbledRed;
    BufferedImage[] bonnieBoBubbledBlue;

    public BonnieBoView(Enemy enemy) {
        super(enemy);
    }
    void loadImages() {
        try {
            bonnieBoWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Walk/Enemy71.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Walk/Enemy72.png")))
            };
            bonnieBoWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Walk/Enemy71Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Walk/Enemy72Right.png")))
            };
            bonnieBoDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Death/Enemy76.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Death/Enemy83.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Death/Enemy84.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Death/Enemy85.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Death/Enemy86.png")))
            };
            bonnieBoEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Rage/rage1.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Rage/rage2.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Rage/rage3.png"))),
            };
            bonnieBoEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Rage/rage1Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Rage/rage2Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Rage/rage3Right.png"))),
            };
            bonnieBoBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Bubbled/GreenBubble/Enemy77.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Bubbled/GreenBubble/Enemy78.png")))
            };
            bonnieBoBubbledRed = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Bubbled/RedBubble/Enemy81.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Bubbled/RedBubble/Enemy82.png")))
            };
            bonnieBoBubbledBlue = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Bubbled/BlueBubble/Enemy79.png"))),
                    scaleImage(ImageIO.read(getClass().getResource("/Resources/Bubble Bobble Resources/Enemies/BonnieBo/Bubbled/BlueBubble/Enemy80.png")))
            };
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage[] getCurrentAnimation() {
        switch(currentAction){
            default:
                return enemy.getFacingRight() ? bonnieBoWalkRight : bonnieBoWalkLeft;
            case DIE:
                return bonnieBoDie;
            case RAGE:
                return enemy.getFacingRight() ? bonnieBoEnragedRight : bonnieBoEnragedLeft;
            case BUBBLED:
                return bonnieBoBubbledGreen;
        }
    }
}

