package VIEW;

import MODEL.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
    private String currentAction = "idle";
    private int currentFrame;

    public PlayerView(Player player) {
        this.player = player;
        this.x = player.getX();
        this.y = player.getY();
        player.addObserver(this);
        try {
            walkRight = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run2.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run3.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run4.png"))};
            idleRight = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run3.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run4.png"))};
            idleLeft = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft3.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/Bub&Bob4.png"))};
            walkLeft = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/RunLeft2.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft3.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft/Bub&Bob4.png"))};
            jumpUpRight = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run6.png"))};
            jumpUpLeft = new BufferedImage[]{
                ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft6.png"))};
            jumpDownRight = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Run/Run8.png"))};
            jumpDownLeft = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/RunLeft//RunLeft8.png"))};
            attackRight = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Attack/CharacterAttack.png")),
            };
            attackLeft = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Attack/CharacterAttack1.png"))
            };
            die = new BufferedImage[]{
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob16.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob17.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob18.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob19.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob20.png")),
                    ImageIO.read(getClass().getResourceAsStream("/Resources/Bubble Bobble Resources/Character/Die/Bub&Bob21.png")),};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Observable o, Object arg) {
        if(o instanceof Player){
            Player player = (Player) o;
            if(arg instanceof String){
                String action = (String) arg;
                this.currentAction = action;
                x = player.getX();
                y = player.getY();
                currentFrame = (currentFrame + 1) % getCurrentAnimation().length;
            }
        }
    }
    public void draw(Graphics2D g2d){
        BufferedImage[] currentAnimation = getCurrentAnimation();
        if (currentAnimation != null){
            x = player.getX();
            y = player.getY();
            g2d.drawImage(currentAnimation[currentFrame], x, y, null);
        }
    }
    private BufferedImage[] getCurrentAnimation(){
        switch(currentAction){
            case "moveRight":
                return walkRight;
            case "moveLeft":
                return walkLeft;
            case "moveUp":
                return player.getFacingRight() ? jumpUpRight : jumpUpLeft;
            case "moveDown":
                return player.getFacingRight() ? jumpDownRight : jumpDownLeft;
            case "attack":
                return player.getFacingRight() ? attackRight : attackLeft;
            case "die":
                return die;
            default:
                return player.getFacingRight() ? idleRight : idleLeft;
        }
    }

}
