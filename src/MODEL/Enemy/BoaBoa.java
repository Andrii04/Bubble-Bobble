package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
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

    public BoaBoa(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
    }
    public BoaBoa( GameStateManager gsm){
        super( gsm);
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
