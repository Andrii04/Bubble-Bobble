package Model.Enemy;

import Model.Entity;

import javax.swing.*;

public class Blubba extends Enemy {

    //vola
    //si muova diagonalmente e colpito un muro gira 90 gradi

    private int x;
    private int y;
    private final int punteggio = 5000;
    boolean enraged;
    Timer ragetimer;

    public Blubba() {

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
