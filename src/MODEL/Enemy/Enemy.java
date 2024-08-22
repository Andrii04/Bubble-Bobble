package MODEL.Enemy;

import MODEL.Entity;

import javax.swing.*;

public abstract class Enemy implements Entity {

    private int x;
    private int y;
    private int points;
    boolean enraged;
    Timer ragetimer;

    public Enemy(){
        this.x= x;
        this.y=y;
        enraged = false;

    }
    //Ricordare di implementare le classi specifiche non astratte
    //dei nemici
    
    public int getPoints(Enemy enemy){
     return points;
    }



}
