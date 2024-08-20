package Model.Bubbles;

import Model.Enemy.Enemy;

import java.awt.Color;

public abstract class Bubble {

    private Color color;
    private int points;
    private int x;
    private int y;
    private boolean containEnemy;

    public Bubble(){
        this.x=x;
        this.y=y;
    }

    public void explode(){}

    public void ecncapsule(Enemy enemy){}




}
