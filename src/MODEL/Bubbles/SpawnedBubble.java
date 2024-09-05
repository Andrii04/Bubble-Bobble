package MODEL.Bubbles;

import VIEW.SpawnedBubbleView;

//classe per le bolle che spawnano automaticamente (non vengono sparate dal Player)
public abstract class SpawnedBubble extends Bubble {

    public SpawnedBubble() {
        super();
        //this.bubbleView = new SpawnedBubbleView(this); ricordare di settarla nelle sottoclassi
        floating = true;
        //updateLocation che setta le coordinate dove spawnano (prob random)
    }

    public abstract void startEffect();
    public abstract void updateEffectLocation();

    @Override
    public void updateLocation(int newX, int newY) {

        if (floating) {
            if (hitbox.intersects(player.getHitbox())) explode();
        }
    }
}
