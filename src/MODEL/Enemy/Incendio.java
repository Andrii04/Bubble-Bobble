package MODEL.Enemy;

import MODEL.Entity;

import javax.swing.*;

public class Incendio extends Enemy{

    //cammina veloce
    //scarso nel salto
    //spara palle di fuoco
    //continua a camminare quando spara

    private boolean facingRight = true;
    private int x;
    private int y;
    private final int points=3000;
    boolean enraged;
    Timer ragetimer;

    public Incendio(){

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

    @Override
    public int getPoints(Enemy enemy){
        return points;
    }

}


