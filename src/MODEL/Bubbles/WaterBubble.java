package MODEL.Bubbles;

public class WaterBubble extends SpawnedBubble{
    public WaterBubble() {
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/WaterBubble";
    }

    @Override
    public Bubble newInstance() {
        return new WaterBubble();
    }
}
