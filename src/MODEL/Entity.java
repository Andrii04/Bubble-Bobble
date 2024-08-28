package MODEL;

import java.awt.*;

public interface Entity {
    public enum Action{
        IDLE,
        JUMP,
        MOVE_VERTICALLY,
        MOVE_LEFT,
        MOVE_RIGHT,
        ATTACK,
        HURT,
        DIE
    }
    public void updateAction(Action action);

    public boolean getFacingRight();
    public int getX();
    public int getY();

    public int compareLocation(Entity entity);

    public void spawn();



}
