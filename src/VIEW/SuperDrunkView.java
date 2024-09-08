package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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

    public SuperDrunkView(Enemy enemy) {
        super(enemy);
    }

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

