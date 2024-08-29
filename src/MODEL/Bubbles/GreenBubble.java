package MODEL.Bubbles;
import MODEL.Player;

public class GreenBubble extends Bubble {
    public GreenBubble() {
        super();
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/GreenBubble";

    }

    @Override
    public Bubble newInstance() {
        return new GreenBubble();
    }
}
