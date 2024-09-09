package MODEL.Bubbles;

import VIEW.SpawnedBubbleView;

public class WaterBubble extends SpawnedBubble{
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/WaterBubble";}
    public WaterBubble() {
        super();
        this.bubbleView = new SpawnedBubbleView(this);
        hitbox.setSize(44, 45);
    }

    @Override
    public void startEffect() {

    }

    @Override
    public void updateEffectLocation() {

    }

    @Override
    public Bubble newInstance() {
        return new WaterBubble();
    }
}
