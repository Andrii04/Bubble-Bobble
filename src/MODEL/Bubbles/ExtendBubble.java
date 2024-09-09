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

    }

    @Override
    public void updateEffectLocation() {

    }

    @Override
    public Bubble newInstance(Player player) {
        return null;
    }

    public String getLetter() {return letter;}
}
