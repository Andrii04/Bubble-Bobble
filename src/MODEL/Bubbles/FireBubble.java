package MODEL.Bubbles;

public class FireBubble extends SpawnedBubble{
    public FireBubble() {
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/FireBubble";
    }


    @Override
    public Bubble newInstance() {
        return new FireBubble();
    }
}
