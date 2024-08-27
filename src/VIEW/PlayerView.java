package VIEW;

import MODEL.Entity;
import MODEL.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Observable;
import java.util.Observer;
import static MODEL.Entity.Action.*;

public class PlayerView implements Observer {
    int x;
    int y;
    Player player;
    private BufferedImage[] walkLeft;
    private BufferedImage[] walkRight;
    private BufferedImage[] idleRight;
    private BufferedImage[] idleLeft;
    private BufferedImage[] jumpUpRight;
    private BufferedImage[] jumpUpLeft;
    private BufferedImage[] jumpDownRight;
    private BufferedImage[] jumpDownLeft;
    private BufferedImage[] die;
    private BufferedImage[] attackRight;
    private BufferedImage[] attackLeft;
    private BufferedImage[] hurt;
    private Entity.Action currentAction = IDLE;
    private int currentFrame;
    private long lastTime;
    private final int frameDelay = 150;
    private BubbleView bubbleView;

    public PlayerView(Player player) {
        this.player = player;
        this.x = player.getX();
        this.y = player.getY();
        this.bubbleView = player.getBubbleType().getBubbleView();
        player.addObserver(this);
        loadImages();
    }

    public BufferedImage scaleImage(BufferedImage img) {
        BufferedImage scaledImage = new BufferedImage(img.getWidth()*3, img.getHeight()*3, img.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(img, 0, 0, img.getWidth()*3, img.getHeight()*3, null);
        g2d.dispose();
        return scaledImage;
    }
    public void loadImages(){
        try {
            walkRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run4.png")))};
            idleRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Bub&Bob1.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Bub&Bob2.png")))};
            idleLeft = new BufferedImage[]{
                   scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//Bub&Bob1Left.png"))),
                   scaleImage( ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/Bub&Bob2Left.png")))};
            walkLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/RunLeft2.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft3.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/Bub&Bob4.png")))};
            jumpUpRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run6.png")))};
            jumpUpLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft6.png")))};
            jumpDownRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run8.png")))};
            jumpDownLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft8.png")))};
            attackRight = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Attack/CharacterAttack.png"))),
            };
            attackLeft = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Attack/CharacterAttack1.png")))
            };
            hurt = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob17.png")))
            };
            die = new BufferedImage[]{
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob16.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob17.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob18.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob19.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob20.png"))),
                    scaleImage(ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob21.png")))};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Observable o, Object arg) {
        if(o instanceof Player){
            Player player = (Player) o;
            if(arg instanceof Entity.Action){
                Entity.Action action = (Entity.Action) arg;
                this.currentAction = action;
                x = player.getX();
                y = player.getY();
            }
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
            case MOVE_RIGHT:
                return walkRight;
            case MOVE_LEFT:
                return walkLeft;
            case MOVE_UP:
                return player.getFacingRight() ? jumpUpRight : jumpUpLeft;
            case  MOVE_DOWN:
                return player.getFacingRight() ? jumpDownRight : jumpDownLeft;
            case ATTACK:
                return player.getFacingRight() ? attackRight : attackLeft;
            case DIE:
                return die;
            case HURT:
                return hurt;
            default:
                return player.getFacingRight() ? idleRight : idleLeft;
        }
    }
    public BubbleView getBubbleView() {return bubbleView;}

}
