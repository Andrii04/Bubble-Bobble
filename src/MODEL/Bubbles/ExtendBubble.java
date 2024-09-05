package MODEL.Bubbles;

public class ExtendBubble extends SpawnedBubble{
    private String letter;

    public ExtendBubble(String letter) {
        super();
        this.letter = letter;
        }

    @Override
    public void startEffect() {
        player.addExtendBubble(this);
    }

    @Override
    public void updateEffectLocation() {

    }

    @Override
    public Bubble newInstance() {
        return null;
    }

    public String getLetter() {return letter;}
}
