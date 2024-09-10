package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Entity;

public class BoaBoa extends Enemy {

    // vola
    //movimento veloce

    private final int points = 4000;
    private boolean goUp;

    public BoaBoa(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 3;
    }

    public BoaBoa(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }


    void chasePlayer() {
        // pong mechanics
        if (isSolidTile(x + speed + Entity.WIDTH, y)) {
            facingRight = false;
        } else if (isSolidTile(x - speed, y)) {
            facingRight = true;
        }
        if (isSolidTile(x, y + speed + Entity.HEIGHT)) {
            goUp = true;
        } else if (isSolidTile(x, y - speed)) {
            goUp = false;
        }
        updateAction(Action.WALK);
    }

    void walk() {
        if (facingRight) {
            this.x += speed;
        } else {
            this.x -= speed;
        }
        if (goUp) {
            this.y -= speed;
        } else {
            this.y += speed;
        }
        hitbox.setLocation(x, y);
        notifyObservers(Action.WALK);
    }
}