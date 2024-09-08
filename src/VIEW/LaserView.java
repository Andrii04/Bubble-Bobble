package VIEW;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.Laser;

import javax.swing.*;

public class LaserView extends BubbleView{
    public LaserView(Bubble bubble) {
        super(bubble);
    }

    @Override
    public void startFiring() {
        currentSkinPath = bubble.getSkinsPath() + "2.png";
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
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
        currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
    }
}
