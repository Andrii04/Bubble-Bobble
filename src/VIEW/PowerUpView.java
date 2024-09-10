package VIEW;

import MODEL.PowerUps.PowerUp;

import javax.swing.*;
import java.awt.*;

public class PowerUpView {
    PowerUp powerUp;
    int explodingAnimationTimer;

    public PowerUpView(PowerUp powerUp) {
        this.powerUp = powerUp;
        explodingAnimationTimer = 0;
    }

    public void draw(Graphics2D g2d) {
        if (!powerUp.isErased() && (!powerUp.isActive() || powerUp.isExploding())) {
            g2d.drawImage(powerUp.getSkin().getImage(), powerUp.getX(), powerUp.getY(), null);
            g2d.draw(powerUp.getHitbox());
        }
    }

    public void update() {
        if (!powerUp.isErased()) {
            if (!powerUp.isActive()) powerUp.updateLocation();
            else if (powerUp.isExploding()) {
                explodingAnimationTimer++;
                if (explodingAnimationTimer >= 15) powerUp.stopExplosion();

            }
        }
    }

    public void setExplodeIMG() {
        String skinPath = "/Resources/Bubble Bobble Resources/Bubbles/BlueBubble5.png";
        powerUp.setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }
}
