package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Entity;

import javax.swing.*;

public class BoaBoa extends Enemy {

    // vola
    //movimento veloce

    private final int punteggio =4000;
    private boolean goUp;
    public BoaBoa(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight, gsm);
        speed = 3;
    }
    public BoaBoa( GameStateManager gsm){
        this(0,0,true,gsm);
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
            case RAGE:
                rage();
                notifyObservers(Action.RAGE);
                break;
            case BUBBLED:
                bubbled();
                break;
            case DIE:
                dead = true;
                player.setPunteggio(player.getPunteggio() + points);
                deathTimer.start();
                notifyObservers(Action.DIE);
                break;
        }
    }

    public boolean getFacingRight() {
        return facingRight;
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
}
