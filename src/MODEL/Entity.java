package MODEL;

import java.awt.*;

public interface Entity {
    static final int WIDTH = 32;
    static final int HEIGHT = 32;
    public enum Action{
        IDLE,
        JUMP,
        WALK,
        MOVE_VERTICALLY,
        MOVE_LEFT,
        MOVE_RIGHT,
        ATTACK,
        HURT,
        DIE,
        RAGE,
        BUBBLED
    }
    // physics
    float jumpSpeed = -7f;
    float gravity = 0.3f;
    public void updateAction(Action action);

    public boolean getFacingRight();
    public int getX();
    public int getY();


    public int compareLocation(Entity entity);

    public void spawn();


}
