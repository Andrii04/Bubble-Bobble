package MODEL.Enemy;

import MODEL.Entity;

import javax.swing.*;

public class BoaBoa extends Enemy {

    // vola
    //movimento veloce

    private boolean facingRight = true;
    private int x;
    private int y;
    private final int punteggio =4000;
    boolean enraged;
    Timer ragetimer;

    public BoaBoa(){

    }


    @Override
    public void updateAction(Action action) {

    }

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
