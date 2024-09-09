package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
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
    void shoot(){
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
