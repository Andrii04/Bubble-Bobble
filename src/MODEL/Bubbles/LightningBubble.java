package MODEL.Bubbles;

public class LightningBubble extends SpawnedBubble{
    public LightningBubble(){
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/LightningBubble";
    }

    @Override
    public Bubble newInstance() {
        return new LightningBubble();
    }
}
