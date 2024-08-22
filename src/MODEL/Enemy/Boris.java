package MODEL.Enemy;

import MODEL.Entity;

import javax.swing.*;

public class Boris extends Enemy {

    //simile a bonzo nei movimenti
    //può lanciare rocce che uccidono il nemico al contatto
    //le rocce si sbriciolano a contatto co muri e piattaforme
    //salta e ha buon movimento

    private int x;
    private int y;
    private final int points=2000;
    boolean enraged;
    Timer ragetimer;

    public Boris(){

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

    @Override
    public int getPoints(Enemy enemy){
        return points;
    }

}
