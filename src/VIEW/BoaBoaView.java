package VIEW;

import MODEL.Enemy.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BoaBoaView extends EnemyView{

    BufferedImage[] boaBoaWalk;
    BufferedImage[] boaBoaDie;
    BufferedImage[] boaBoaEnraged;
    BufferedImage[] boaBoaBubbledGreen;
    BufferedImage[] boaBoaBubbledBlue;
    BufferedImage[] boaBoaBubbledRed;

    public BoaBoaView(Enemy enemy) {
        super(enemy);
    }
    void loadImages(){
        try{
            boaBoaWalk= new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy56.png")))
            };
            boaBoaDie = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy67.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy68.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy69.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Death/Enemy70.png")))
            };
            boaBoaEnraged = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Rage/Enemy57.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Rage/Enemy58.png")))
            };
            boaBoaBubbledGreen = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/GreenBubble/Enemy61.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/GreenBubble/Enemy62.png")))
            };
            boaBoaBubbledBlue = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/BlueBubble/Enemy63.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/BlueBubble/Enemy64.png")))
            };
            boaBoaBubbledRed = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/RedBubble/Enemy65.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Bubbled/RedBubble/Enemy66.png")))
            };
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            default:
                return boaBoaWalk;
            case DIE:
                return boaBoaDie;
            case RAGE:
                return boaBoaEnraged;
            case BUBBLED:

                return boaBoaBubbledGreen; // boaBoaBubbledBlue, boaBoaBubbledRed
        }
    }
}
