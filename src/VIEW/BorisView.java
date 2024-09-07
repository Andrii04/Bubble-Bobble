package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BorisView extends EnemyView{
    BufferedImage[] borisWalkRight;
    BufferedImage[] borisWalkLeft;
    BufferedImage[] borisDie;
    BufferedImage[] borisEnragedLeft;
    BufferedImage[] borisEnragedRight;
    BufferedImage[] borisBubbledGreen;
    BufferedImage[] borisBubbledBlue;
    BufferedImage[] borisBubbledRed;
    BufferedImage[] fireball;
    public BorisView(Enemy enemy) {
        super(enemy);
    }
    void loadImages(){
        try {
            borisWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy18.png")))
            };
            borisWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy18Right.png")))
            };
            borisDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy29.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy30.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy31.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Death/Enemy32.png")))
            };
            borisEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy19.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy20.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy21.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy22.png")))
            };
            borisEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy19Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy20Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy21Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Rage/Enemy22Right.png")))
            };
            borisBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/GreenBubble/Enemy23.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/GreenBubble/Enemy24.png")))
            };
            borisBubbledBlue = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/BlueBubble/Enemy25.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/BlueBubble/Enemy26.png")))
            };
            borisBubbledRed = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/RedBubble/Enemy27.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/Bubbled/RedBubble/Enemy28.png")))
            };
            fireball = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball4.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball5.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Boris/FireballAttack/Fireball6.png")))
            };
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            default:
                return enemy.getFacingRight() ? borisWalkRight : borisWalkLeft;
            case DIE:
                return borisDie;
            case RAGE:
                return enemy.getFacingRight() ? borisEnragedRight : borisEnragedLeft;
            case BUBBLED:
                return borisBubbledGreen; // borisBubbledBlue, borisBubbledRed
        }
    }
}
