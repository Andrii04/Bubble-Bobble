package MODEL.Bubbles;
import MODEL.Player;
import VIEW.BubbleView;

/**
 * Rappresenta una bolla di colore verde lanciata dal giocatore.
 *
 * <p>Questa bolla ha una velocità di sparo che dipende dalla velocità della bolla del giocatore e una velocità di galleggiamento fissa.</p>
 */
public class GreenBubble extends Bubble {
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/GreenBubble";}
    /**
     * Crea una nuova istanza di {@code GreenBubble} associata al giocatore specificato.
     *
     * @param player Il giocatore che ha lanciato la bolla.
     */
    public GreenBubble(Player player) {
        super(player);

        shootingSpeed = player.getBubbleSpeed();
        floatingSpeed = 1;
        this.bubbleView = new BubbleView(this);
    }

    /**
     * Crea una nuova istanza di {@code GreenBubble} associata al giocatore specificato.
     *
     * @param player Il giocatore che ha lanciato la bolla.
     * @return Una nuova istanza di {@code GreenBubble}.
     */
    @Override
    public Bubble newInstance(Player player) {
        return new GreenBubble(player);
    }
}