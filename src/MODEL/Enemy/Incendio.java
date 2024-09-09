package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.Fireball;
import MODEL.Bubbles.IncendioFireball;
import MODEL.Entity;

import javax.swing.*;

public class Incendio extends Enemy{

    //cammina veloce
    //scarso nel salto
    //spara palle di fuoco
    //continua a camminare quando spara

    private final int points=3000;


    public Incendio(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight,gsm);
        speed = 3;
    }
    public Incendio(GameStateManager gsm){
        super(gsm);
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
    public void move(){
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        if(!isBubbled() && !isDead()){
            if(player.getY() != y && attackTimer.isRunning()){
                attackTimer.stop();
            }
            else if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            onPlayer();
            chasePlayer();
        }
        }

    void shoot(){
        // to do
        System.out.println("Incendio shoot");
        IncendioFireball fireball = new IncendioFireball(player, this);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
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


