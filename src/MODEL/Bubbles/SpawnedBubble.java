package MODEL.Bubbles;
//classe per le bolle che spawnano automaticamente (non vengono sparate dal Player)
public abstract class SpawnedBubble extends Bubble{
    @Override
    public abstract Bubble newInstance();

    public void spawn() {

    }
}
