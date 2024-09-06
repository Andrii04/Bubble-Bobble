package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.Fireball;
import MODEL.Enemy.Boris;

import javax.swing.*;

public class FireballView extends BubbleView {

    int shootingSpeed = 9;
    int floatingSpeed = 1;

    public FireballView(Fireball bubble) {

        super(bubble);
    }

    @Override
    public void startFiring() {
        distanceTraveled = 0;
        facingRight = boris.getFacingRight();
        currentSkinPath = bubble.getSkinsPath() + "1.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
    }

    @Override
    public void update() {
        if (firing) {
            if (facingRight) {
                bubble.updateLocation(bubble.getX() + shootingSpeed, bubble.getY());
            } else if (!facingRight) {
                bubble.updateLocation(bubble.getX() - shootingSpeed, bubble.getY());
            }

            if (distanceTraveled >= 3 && distanceTraveled < 7) {
                currentSkinPath = bubble.getSkinsPath() + "2.png";
                currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            } else if (distanceTraveled >= 7) {
                currentSkinPath = bubble.getSkinsPath() + "3.png";
                currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            }
            distanceTraveled++;

            if (distanceTraveled >= 60) bubble.explode();
        }
        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 10) {
                bubble.stopExplosion();
                bubble.erase();
            }
        }
    }
}
