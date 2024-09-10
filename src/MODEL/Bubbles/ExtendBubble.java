package MODEL.Bubbles;

import MODEL.Player;

public class ExtendBubble extends SpawnedBubble{
    private String letter;

    public ExtendBubble(Player player, String letter) {
        super(player);
        this.letter = letter;
        }

    @Override
    public void startEffect() {
        player.addExtendBubble(this);  // Aggiungi la bolla alla collezione del giocatore
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
