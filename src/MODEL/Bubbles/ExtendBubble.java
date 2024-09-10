package MODEL.Bubbles;

import MODEL.Player;
import VIEW.SpawnedBubbleView;

public class ExtendBubble extends SpawnedBubble{
    private String letter;
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/extend";}
    public ExtendBubble(Player player, String letter) {
        super(player);
        this.letter = letter;
        this.bubbleView = new SpawnedBubbleView(this);
        bubbleView.setExtendBubbleSkin(letter);
        floating = true;
        hitbox.setSize(45, 45);
        }

    @Override
    public void startEffect() {
        player.addExtendBubble(this);// Aggiungi la bolla alla collezione del giocatore
        erase();
    }

    @Override
    public void updateEffectLocation() {

    }

    @Override
    public Bubble newInstance(Player player) {
        return new ExtendBubble(player, letter); // Crea nuova bolla con stessa lettera
    }

    public String getLetter() {return letter;}
}
