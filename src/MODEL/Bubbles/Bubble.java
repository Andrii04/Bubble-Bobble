package MODEL.Bubbles;

import MODEL.Enemy.Enemy;

import java.awt.Color;
import VIEW.BubbleView;
import VIEW.PlayPanel;
import MODEL.Entity.Action;
import MODEL.Player;

import javax.swing.*;

public abstract class Bubble {

    private Color color;
    private int points;
    private int x;
    private int y;
    private boolean containEnemy;
    BubbleView bubbleView;
    Player player;
    String skinsPath; //sarà il path della skin senza il numero alla fine



    public Bubble(){
        this.bubbleView = new BubbleView(this);
    }

    public void explode(Enemy enemy){
        if (enemy != null) enemy.updateAction(Action.DIE);
        bubbleView.explode(enemy);
    }

    public void ecncapsule(Enemy enemy){
        bubbleView.encapsule(enemy);
        //cambia animazione nemico: incapsulato
    }
    public void updateLocation(int x, int y) {
        this.x = x;
        this.y = y;
        bubbleView.updateLocation(x, y);
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public String getSkinsPath() {return skinsPath;}
    public BubbleView getBubbleView() {return bubbleView;}
    public Player getPlayer() {return player;}
    public void setPlayer(Player player) {this.player = player;}
    public abstract Bubble newInstance();
    public void finishedFiring() {player.removeBubble(this);}



}
