package MODEL.Bubbles;

import VIEW.SpawnedBubbleView;

public class FireBubble extends SpawnedBubble{
    public FireBubble() {
        super();
        this.bubbleView = new SpawnedBubbleView(this);
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/fire";
    }

    @Override
    public Bubble newInstance() {
        return new FireBubble();
    }

    @Override

    public void startEffect() {

    }

    @Override
    public void updateEffectLocation() {

    }
}
