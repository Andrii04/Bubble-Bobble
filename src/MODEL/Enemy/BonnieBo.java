package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BonnieBo extends Enemy {
    //no mosse di attacco
    //rimbalza ad alta velocità
    //dannno tramite contatto
    private final int punteggio = 1000;
    private boolean goUp;
    public BonnieBo( int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 3;

    }
    public BonnieBo( GameStateManager gsm){
        this( 0, 0, true, gsm);
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int compareLocation(Entity entity) {
        return 0;
    }

    @Override
    public void spawn() {

    }



}
