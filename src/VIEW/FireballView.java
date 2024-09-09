package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.Fireball;
import MODEL.Enemy.Boris;

import javax.swing.*;
import java.awt.*;

public class FireballView extends BubbleView {

    public FireballView(Fireball bubble) {
        super(bubble);
    }

    @Override
    public void startFiring() {
        distanceTraveled = 0;
        facingRight = enemy.getFacingRight();
        if (facingRight) currentSkinPath = bubble.getSkinsPath() + "2.png";
        else currentSkinPath = bubble.getSkinsPath() + "1.png";
        ImageIcon skin = new ImageIcon(getClass().getResource(currentSkinPath));
        Image img = skin.getImage().getScaledInstance(skin.getIconWidth()*2, skin.getIconHeight()*2, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(img);
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
                currentSkinPath = bubble.getSkinsPath() + "6.png";;
                ImageIcon skin = new ImageIcon(getClass().getResource(currentSkinPath));
                Image img = skin.getImage().getScaledInstance(skin.getIconWidth()*2, skin.getIconHeight()*2, Image.SCALE_SMOOTH);
                currentSkin = new ImageIcon(img);
            }
            else if (explodingAnimationTimer >= 20) {
                bubble.stopExplosion();
                bubble.erase();
            }
        }
    }

    @Override
    public void setExplodeIMG() {

        currentSkinPath = bubble.getSkinsPath() + "5.png";
        ImageIcon skin = new ImageIcon(getClass().getResource(currentSkinPath));
        Image img = skin.getImage().getScaledInstance(skin.getIconWidth()*2, skin.getIconHeight()*2, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(img);
    }
}
