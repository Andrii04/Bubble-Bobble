package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.Fireball;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Boris extends Enemy {

    //simile a bonzo nei movimenti
    //può lanciare rocce che uccidono il nemico al contatto
    //le rocce si sbriciolano a contatto co muri e piattaforme
    //salta e ha buon movimento


    private final int points=2000;

    public Boris( int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 2;
    }
    public Boris( GameStateManager gsm){
        this( 0, 0, true, gsm);
    }

    public void move(){
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        if (!isBubbled() && !isDead()) {
            if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            else{
                if(attackTimer.isRunning()){
                    attackTimer.stop();
                }
                onPlayer();
                chasePlayer();
            }
        }
    }

    boolean shouldRetracePath(){
        if (shortestPath.isEmpty()){
            updateAction(Action.IDLE);
            return true;
        }
        return Math.abs(player.getY() - shortestPath.getLast().y )>80;
    }

    void shoot(){
        System.out.println("shooting");
        Fireball fireball = new Fireball();
        fireball.setEnemy(this);
        fireball.setPlayer(player);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
    }
    void bubbled(){
        if(attackTimer.isRunning()){
            attackTimer.stop();
        }
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);

    }
    void die(){
        if(attackTimer.isRunning()){
            attackTimer.stop();
        }
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

    @Override
    public int getPoints(Enemy enemy){
        return points;
    }

}
