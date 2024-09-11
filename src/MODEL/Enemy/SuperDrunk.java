package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bottle;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.awt.*;

public class SuperDrunk extends Enemy{
    private int lives = 2;
    private boolean goUp;
    private final int points = 10000;
    private Timer startTimer;
    private Timer hurtCooldown;
    private Timer attackTimer;
    private boolean canBeHurt;
    public SuperDrunk(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 3;
        updateAction(Action.IDLE);
        startTimer = new Timer(1000, e -> {
            startTimer.stop();
            updateAction(Action.WALK);
            canBeHurt = true;
            });
        hurtCooldown = new Timer(500, e -> {
            canBeHurt = true;
            updateAction(Action.WALK);
            hurtCooldown.stop();
        });
        attackTimer = new Timer(1000, e -> shoot());
        attackTimer.setRepeats(true);
        super.hitbox = new Rectangle(x, y, 48*2, 51*2);
        startTimer.start();
        attackTimer.start();
    }
    public SuperDrunk(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }

    public boolean isColliding(int x, int y){
        if(x <= 17 || x > 660|| y <= 17  || y >= 560){
            return true;
        }
        return false;
    }

    public void shoot(){
        if (currentLevel != null) {
            Bottle bottle1;
            Bottle bottle2;
            Bottle bottle3;
            Bottle bottle4;
            Bottle bottle5;

            if (facingRight) {
                bottle1 = new Bottle(player, this, Bottle.BottleTrajectory.UPUP_RIGHT);
                bottle2 = new Bottle(player, this, Bottle.BottleTrajectory.UP_RIGHT);
                bottle3 = new Bottle(player, this, Bottle.BottleTrajectory.HORIZONTAL_RIGHT);
                bottle4 = new Bottle(player, this, Bottle.BottleTrajectory.DOWN_RIGHT);
                bottle5 = new Bottle(player, this, Bottle.BottleTrajectory.DOWNDOWN_RIGHT);
            } else {
                bottle1 = new Bottle(player, this, Bottle.BottleTrajectory.UPUP_LEFT);
                bottle2 = new Bottle(player, this, Bottle.BottleTrajectory.UP_LEFT);
                bottle3 = new Bottle(player, this, Bottle.BottleTrajectory.HORIZONTAL_LEFT);
                bottle4 = new Bottle(player, this, Bottle.BottleTrajectory.DOWN_LEFT);
                bottle5 = new Bottle(player, this, Bottle.BottleTrajectory.DOWNDOWN_LEFT);
            }

            currentLevel.addBubble(bottle1);
            currentLevel.addBubble(bottle2);
            currentLevel.addBubble(bottle3);
            currentLevel.addBubble(bottle4);
            currentLevel.addBubble(bottle5);

            bottle1.fireBubble();
            bottle2.fireBubble();
            bottle3.fireBubble();
            bottle4.fireBubble();
            bottle5.fireBubble();
            //shoot
        }
    }
void onPlayer(){
        if(hitbox.intersects(player.getHitbox())){
            if(bubbled){
                updateAction(Action.DIE);
            }
            else{
                player.updateAction(Action.HURT);
            }
        }
}
    void chasePlayer(){
        // pong mechanics
        if (isColliding(x+speed,y)){
            facingRight = false;
        }
        else if(isColliding(x- speed,y)){
            facingRight = true;
        }
        if(isColliding(x,y+ speed)){
            goUp = true;
        }
        else if(isColliding(x,y- speed)){
            goUp = false;
        }
        updateAction(Action.WALK);
    }

    @Override
    public void updateAction(Action action) {
        switch(action){
            default:
                notifyObservers(Action.IDLE);
                break;
            case WALK:
                if(!attackTimer.isRunning()){
                    attackTimer.start();
                }
                if(facingRight){
                    this.x+=speed;
                }
                else{
                    this.x-=speed;
                }
                if(goUp){
                    this.y-=speed;
                }
                else{
                    this.y+=speed;
                }
                hitbox.setLocation(x,y);
                if(enraged){
                    notifyObservers(Action.RAGE);
                }
                else{
                    notifyObservers(Action.WALK);
                }
                break;
            case HURT:
                MainFrame.playSound(2);
                lives--;
                if( lives <= 0){
                    enraged = false;
                    updateAction(Action.BUBBLED);
                }
                else if(canBeHurt){
                    canBeHurt = false;
                    hurtCooldown.start();
                }
                break;
            case BUBBLED:
                stop();
                bubbled();
                break;
            case RAGE:
                rage();
                notifyObservers(Action.RAGE);
                break;
            case DIE:
                stop();
                dead = true;
                wave = false;
                player.setPunteggio(player.getPunteggio() + points);
                deathTimer.start();
                MainFrame.playSound(5);
                notifyObservers(Action.DIE);
                break;
        }
    }

    void rage(){
        lives = 5;
        enraged = true;
        bubbled = false;
        speed += 2;
        attackTimer.setDelay(800);
        updateAction(Action.WALK);
    }

    @Override
    public void move(){
        if(isBubbled()) {
            if (hitbox.intersects(player.getHitbox())) {
                updateAction(Action.DIE);
                notifyObservers(Action.DIE);
                return;
            }
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        else if(!isDead() && !isBubbled()){
            onPlayer();
            chasePlayer();
        }
    }
}
