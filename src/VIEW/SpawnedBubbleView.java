package VIEW;

import MODEL.Bubbles.Bubble;

import java.awt.*;

public class SpawnedBubbleView extends BubbleView {
    public SpawnedBubbleView(Bubble bubble) {
        super(bubble);
        floating = true;
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
                bubble.startEffect();
            }
        }
        else if (bubble.isEffect()) bubble.updateEffectLocation();
    }
}
