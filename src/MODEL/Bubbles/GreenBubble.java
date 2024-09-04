package MODEL.Bubbles;
import MODEL.Player;
import VIEW.BubbleView;

public class GreenBubble extends Bubble {
    public GreenBubble() {
        super();
        this.bubbleView = new BubbleView(this);
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/GreenBubble";

    }

    @Override
    public Bubble newInstance() {
        return new GreenBubble();
    }
}
