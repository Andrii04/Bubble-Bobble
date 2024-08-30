package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Entity;

import javax.swing.*;

public class Blubba extends Enemy {

    //vola
    //si muova diagonalmente e colpito un muro gira 90 gradi

    private boolean facingRight = true;
    private int x;
    private int y;
    private final int punteggio = 5000;
    boolean enraged;
    Timer ragetimer;

    public Blubba( int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
    }
    public Blubba( GameStateManager gsm){
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
