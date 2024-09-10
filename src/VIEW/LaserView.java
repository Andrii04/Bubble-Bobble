package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.Laser;

import javax.swing.*;
import java.awt.*;

public class LaserView extends BubbleView{
    public LaserView(Bubble bubble) {
        super(bubble);
    }

    @Override
    public void startFiring() {
        currentSkinPath = bubble.getSkinsPath() + "2.png";
        Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }

    @Override
    public void update() {
        if (firing) {
            bubble.updateLocation(bubble.getX(), bubble.getY() + shootingSpeed);
        }
        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 15) bubble.erase();
        }
    }

    @Override
    public void setExplodeIMG() {
        currentSkinPath = bubble.getSkinsPath() + "3.png";
        Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }
}
