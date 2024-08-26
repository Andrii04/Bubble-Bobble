package MODEL.Enemy;

import MODEL.Entity;

import javax.swing.Timer;

public class Benzo extends Enemy {

    //non ha mosse di attacco
    //ha salto
    //danno tramite contatto

    private boolean facingRight = true;
    private int x;
    private int y;
    private final int points = 500;
    boolean enraged;
    Timer ragetimer;

    public Benzo(){

    }

    @Override
    public void updateAction(Action action) {

    }

    @Override
    public boolean getFacingRight() {
        return facingRight;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int compareLocation(Entity entity) {
        return 0;
    }

    @Override
    public void spawn() {

    }

}
