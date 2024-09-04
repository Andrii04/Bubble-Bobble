package MODEL.Bubbles;

public class LightningBubble extends SpawnedBubble{
    public LightningBubble(){
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/LightningBubble";
    }

    @Override
    public void updateLocation(int newX, int newY) {

    }

    @Override
    public Bubble newInstance() {
        return new LightningBubble();
    }
}
