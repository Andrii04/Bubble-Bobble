package MODEL.Bubbles;
import MODEL.Player;
import VIEW.BubbleView;

public class GreenBubble extends Bubble {

    public GreenBubble(Player player) {
        super(player);

        shootingSpeed = player.getBubbleSpeed();
        floatingSpeed = 1;
        this.bubbleView = new BubbleView(this);
        skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/GreenBubble";

    }

    @Override
    public Bubble newInstance(Player player) {
        return new GreenBubble(player);
    }
}
