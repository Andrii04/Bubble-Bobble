package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BlubbaView extends EnemyView {

    BufferedImage[] blubbaWalkRight;
    BufferedImage[] blubbaWalkLeft;
    BufferedImage[] blubbaDie;
    BufferedImage[] blubbaEnragedLeft;
    BufferedImage[] blubbaEnragedRight;
    BufferedImage[] blubbaBubbledGreen;
    BufferedImage[] blubbaBubbledBlue;
    BufferedImage[] blubbaBubbledRed;

    public BlubbaView(Enemy blubba) {
        super(blubba);

    }

    void loadImages(){
        try{
            blubbaWalkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy40.png")))
            };
            blubbaWalkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy40Right.png")))
            };
            blubbaDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy51.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy52.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy53.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Death/Enemy54.png")))
            };
            blubbaEnragedLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy41.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy42.png")))
            };
            blubbaEnragedRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy41Right.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Rage/Enemy42Right.png")))
            };
            blubbaBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/GreenBubble/Enemy45.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/GreenBubble/Enemy46.png")))
            };
            blubbaBubbledBlue = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/BlueBubble/Enemy47.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/BlueBubble/Enemy48.png")))
            };
            blubbaBubbledRed = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/RedBubble/Enemy49.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Blubba/Bubbled/RedBubble/Enemy50.png")))
            };
            }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            default:
                return enemy.getFacingRight() ? blubbaWalkRight : blubbaWalkLeft;
            case DIE:
                return blubbaDie;
            case RAGE:
            return enemy.getFacingRight() ? blubbaEnragedRight : blubbaEnragedLeft;
            case BUBBLED:
                /*
                if (enemy.getColor() == Enemy.Color.GREEN) return blubbaBubbledGreen;
                if (enemy.getColor() == Enemy.Color.BLUE) return blubbaBubbledBlue;
                if (enemy.getColor() == Enemy.Color.RED) return blubbaBubbledRed;/*
                 */
                return blubbaBubbledGreen;
        }
    }
}
