package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.Laser;
import MODEL.Entity;

import java.util.*;

public class Invader extends Enemy{
    public Invader(int x, int y, boolean facingRight, GameStateManager gsm){
        super(x, y, facingRight,gsm);
        speed = 3;
    }
    public Invader(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }
    public void  chasePlayer() {
        if (isSolidTile(x, y + Entity.HEIGHT + 1)) {
            onFloor = true;
        } else {
            onFloor = false;
        }
        if(!isSolidTile(x,y+Entity.HEIGHT+1)){
            onFloor = false;
        }
        else{
            onFloor = true;
        }
            updateAction(Action.ATTACK);
            updateAction(Action.IDLE);
    }
    void shoot(){
        Laser laser = new Laser(player, this);
        currentLevel.addBubble(laser);
        laser.fireBubble();
        System.out.println("Invader shoots");
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
