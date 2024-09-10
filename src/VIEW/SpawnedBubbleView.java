package VIEW;

import MODEL.Bubbles.Bubble;

import javax.swing.*;
import java.awt.*;

public class SpawnedBubbleView extends BubbleView {
    public SpawnedBubbleView(Bubble bubble) {
        super(bubble);
    }

    //stesso update per ogni bolla che spawna automaticamente
    @Override
    public void update() {

        if (floating) {

            bubble.handleFloatingCollision();
            distanceTraveled++;

        } else if (exploding) {
            explodingAnimationTimer++;
            if (explodingAnimationTimer >= 20) {
                bubble.stopExplosion();
                if (bubble.getHitWall()) bubble.erase();
                else bubble.startEffect();
            }
        }
        else if (bubble.isEffect()) {
            bubble.updateEffectLocation();
        }
        else if (bubble.getBubbledEnemy() != null && bubble.getBubbledEnemy().isExploded())
        {
            bubble.pom();
        }
        else if (pom) {
            pomAnimationTimer++;
            if (pomAnimationTimer >= 80) bubble.erase();
        }
    }
}
