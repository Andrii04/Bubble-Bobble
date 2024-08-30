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
    BufferedImage[] benzoRage;
    BufferedImage[] benzoBubbled;


    public BenzoView(Enemy benzo){
        super(benzo);
        currentAction = MOVE_RIGHT;
        loadImages();
    }

    public BufferedImage scaleImage(BufferedImage img) {
        BufferedImage scaledImage = new BufferedImage(img.getWidth() * 3, img.getHeight() * 3, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, img.getWidth() * 3, img.getHeight() * 3, null);
        g2d.dispose();
        return scaledImage;
    }

    public void loadImages() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d){
        BufferedImage[] currentAnimation = getCurrentAnimation();
        if (currentAnimation != null){
            long currentTime = System.currentTimeMillis();
            if(currentTime-lastTime > frameDelay){
                lastTime = currentTime;
                currentFrame = (currentFrame + 1) % currentAnimation.length;
            }
        }
        currentFrame = Math.min(currentFrame, currentAnimation.length - 1);
        g2d.drawImage(currentAnimation[currentFrame], x, y, null);
    }

    private BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            default:
                return benzoWalkRight;
            case MOVE_LEFT:
                return benzoWalkLeft;
            case DIE:
                return benzoDie;
            case RAGE:
                return benzoRage;
            case BUBBLED:
                return benzoBubbled;
        }
    }
}

