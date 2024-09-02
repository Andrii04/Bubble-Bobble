package VIEW;

import MODEL.Enemy.Enemy;
import MODEL.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;

import static MODEL.Entity.Action.MOVE_RIGHT;

public class BenzoView extends EnemyView {

    BufferedImage[] benzoWalkRight;
    BufferedImage[] benzoWalkLeft;
    BufferedImage[] benzoDie;
    BufferedImage[] benzoEnraged;
    BufferedImage[] benzoBubbledGreen;
    BufferedImage[] benzoBubbledBlue;
    BufferedImage[] benzoBubbledRed;


    public BenzoView(Enemy benzo){
        super(benzo);
        currentAction = MOVE_RIGHT;
        loadImages();
        benzo.addObserver(this);
    }

    private BufferedImage scaleImage(BufferedImage img) {
        BufferedImage scaledImage = new BufferedImage(img.getWidth() * 2, img.getHeight() * 2, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, img.getWidth() * 2, img.getHeight() * 2, null);
        g2d.dispose();
        return scaledImage;
    }

    private void loadImages() {
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
            benzoEnraged = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy4.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy5.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Rage/Enemy6.png")))
            };
            benzoBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/GreenBubble/Enemy7.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/GreenBubble/Enemy8.png"))),
            };
            benzoBubbledBlue = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/BlueBubble/Enemy9.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/BlueBubble/Enemy10.png")))
            };
            benzoBubbledRed = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/RedBubble/Enemy11.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/Benzo/Bubbled/RedBubble/Enemy12.png")))
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            default:
                return benzoWalkRight;
            case MOVE_LEFT:
                return benzoWalkLeft;
            case DIE:
                return benzoDie;
            case RAGE:
                return benzoEnraged;
            case BUBBLED:
                return benzoBubbledGreen; // benzoBubbledBlue, benzoBubbledRed
        }
    }
}


