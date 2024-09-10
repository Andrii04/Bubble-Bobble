package VIEW;

import MODEL.Bubbles.Bubble;

import javax.swing.*;
import java.awt.*;

public class BottleView extends BubbleView{
    int bottleRotationTimer;
    public BottleView(Bubble bubble) {
        super(bubble);
        bottleRotationTimer = 0;
    }

    @Override
    public void startFiring() {
        currentSkinPath = bubble.getSkinsPath() + "5.png";
        /*Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin); */
    }

    @Override
    public void update() {

        //animazione bottiglia con rotazione
        if (firing) {
            bottleRotationTimer++;
            if (bottleRotationTimer >= 5 && bottleRotationTimer < 11) {
                currentSkinPath = bubble.getSkinsPath() + "6.png";
                //currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            } else if (bottleRotationTimer >= 11 && bottleRotationTimer < 16) {
                currentSkinPath = bubble.getSkinsPath() + "7.png";
                //currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            } else if (bottleRotationTimer >= 16 && bottleRotationTimer < 21) {
                currentSkinPath = bubble.getSkinsPath() + "8.png";
                //currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
            } else if (bottleRotationTimer >= 21) {
                currentSkinPath = bubble.getSkinsPath() + "5.png";
                //currentSkin = new ImageIcon(getClass().getResource(currentSkinPath));
                bottleRotationTimer = 0;
            }
        }

        if (firing) bubble.updateLocation();

        else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 15) bubble.erase();
        }
    }

    @Override
    public void setExplodeIMG() {
        //non c'è un'immagine di esplosione per le bottiglie quindi userei questa
        currentSkinPath = "/Resources/Bubble Bobble Resources/Bubbles/BlueBubble5.png";
        Image originalSkin = new ImageIcon(getClass().getResource(currentSkinPath)).getImage();
        Image resizedSkin = originalSkin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        currentSkin = new ImageIcon(resizedSkin);
    }
}