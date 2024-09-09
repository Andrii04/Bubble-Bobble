package MODEL.Bubbles;

import MODEL.Player;
import VIEW.SpawnedBubbleView;

public class WaterBubble extends SpawnedBubble{
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/WaterBubble";}
    public WaterBubble(Player player) {
        super(player);
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
    public Bubble newInstance(Player player) {
        return new WaterBubble(player);
    }
}
