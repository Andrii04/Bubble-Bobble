package Model;

import javax.swing.*;

public abstract class Enemy implements  Entity{

    private int x;
    private int y;
    boolean enraged;
    Timer ragetimer;

    public Enemy(){
        this.x= x;
        this.y=y;
        enraged = false;

    }
    //Ricordare di implementare le classi specifiche non astratte
    //dei nemici



}
