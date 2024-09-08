package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.awt.*;

public class SuperDrunk extends Enemy{
    private int lives = 10;
    private boolean goUp;
    private final int points = 10000;
    private Timer startTimer;
    private Timer hurtCooldown;
    private Timer attackTimer;
    private boolean canBeHurt;
    public SuperDrunk(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 2;
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
        //shoot
        System.out.println("SuperDrunk shooting");
    }
    public void chasePlayer(){
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
                notifyObservers(Action.WALK);
                break;
            case HURT:
                lives--;
                if( lives <= 0){
                    updateAction(Action.DIE);
                }
                else if(canBeHurt){
                    canBeHurt = false;
                    hurtCooldown.start();
                }
                break;
            case BUBBLED:
                bubbled();
                break;
            case RAGE:
                rage();
                notifyObservers(Action.RAGE);
                break;
            case DIE:
                dead = true;
                player.setPunteggio(player.getPunteggio() + points);
                attackTimer.stop();
                System.out.println("SuperDrunk died");
                break;
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
    public int getLives(){
        return lives;
    }
    public void setLives(){
        this.lives = lives;
    }

    @Override
    public int compareLocation(Entity entity) {
        return 0;
    }

    @Override
    public void spawn() {

    }
}
