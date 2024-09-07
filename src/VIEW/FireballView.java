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
        if (facingRight) currentSkinPath = bubble.getSkinsPath() + "Enemy34.png";
        else currentSkinPath = bubble.getSkinsPath() + "Enemy33.png";
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

            if (distanceTraveled >= 60) bubble.explode();
        }
        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 10 && explodingAnimationTimer < 20) {
                currentSkinPath = bubble.getSkinsPath() + "Enemy38.png";
                currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            }
            else if (explodingAnimationTimer >= 20) {
                bubble.stopExplosion();
                bubble.erase();
            }
        }
    }

    @Override
    public void setExplodeIMG() {

        currentSkinPath = bubble.getSkinsPath() + "Enemy37.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
    }
}
