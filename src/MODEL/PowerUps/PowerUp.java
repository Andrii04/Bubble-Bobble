package MODEL.PowerUps;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Level;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.PowerUpView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class PowerUp {
    int x;
    int y;
    ImageIcon skin;
    Player player;
    boolean active;
    Rectangle hitbox;
    boolean explode;
    int fallingSpeed = 4;
    Level currentLevel;
    boolean onFloor;
    boolean erased;
    PowerUpView powerUpView;
    GameStateManager gsm;
    //il modo in cui spawnano sarà fatto dalla classe Player
    //ad esempio se il Player salta 35 volte viene spawnata una Pink Candy

    public PowerUp() {
        gsm = GameStateManager.getInstance();
        currentLevel = gsm.getCurrentLevel();
        player = gsm.getCurrentPlayer();

        Random random = new Random();
        x = random.nextInt(Block.WIDTH + 30, MainFrame.FRAME_HEIGHT - Block.WIDTH - 30);
        y = random.nextInt(Block.HEIGHT + 30, MainFrame.FRAME_HEIGHT - Block.HEIGHT-30);
        active = false;
        explode = false;
        onFloor = false;
        erased = false;
        hitbox = new Rectangle(x, y, 30, 30);
        powerUpView = new PowerUpView(this);
    }

    public abstract void activateEffect();
    public int getX() {return x;}
    public int getY() {return y;}
    public ImageIcon getSkin() {return skin;}
    public boolean isActive() {return active;}
    public Rectangle getHitbox() {return hitbox;}
    public boolean isExploding() {return explode;}
    public int getFallingSpeed() {return fallingSpeed;}
    public boolean isOnFloor() {return onFloor;}
    public boolean isErased() {return erased;}

    public void updateLocation() {

        if (!onFloor) {
            y += fallingSpeed;
            hitbox.setLocation(x, y);
        }

        if (!onFloor && currentLevel.isItSolidBlock(y / Block.HEIGHT, x / Block.WIDTH)) {
            y -= 2;
            hitbox.setLocation(x, y);
            onFloor = true;
        }

        if (hitbox.intersects(player.getHitbox())) {
            activateEffect();
            explode();
        }
    }

    public void erase() {
        explode = false;
        erased = true;
        currentLevel.despawnPowerUp(this);
    }

    public void explode() {
        explode = true;
        powerUpView.setExplodeIMG();
    }

    public void setSkin(ImageIcon skin) {
        this.skin = skin;
    }

    public PowerUpView getPowerUpView() {return powerUpView;}
}
