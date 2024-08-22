package MODEL.Enemy;

import MODEL.Entity;

import javax.swing.*;

public class BonnieBo extends Enemy {

    //no mosse di attacco
    //rimbalza ad alta velocità
    //dannno tramite contatto

    private int x;
    private int y;
    private final int punteggio = 1000;
    boolean enraged;
    Timer ragetimer;

    public BonnieBo(){

    }

    @Override
    public void updatelocation(int x, int y) {

    }

    @Override
    public void attack() {

    }

    @Override
    public void die() {

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
