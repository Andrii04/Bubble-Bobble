package MODEL;

import java.awt.*;
import java.util.Observable;

public class Player extends Observable implements Entity {

    private UserProfile profile;
    private int x;
    private int y;
    private int punteggio;
    private int lives;
    private int speed;
    private boolean facingRight = true;
    private boolean  isSolid;
    private Rectangle solidArea;
    public Player(UserProfile profile){

        this.profile=profile;
        this.x = 0;
        this.y = 0;
        this.lives = 2; // default
        this.speed = 10; // default
        solidArea = new Rectangle(21,21,30,30);

    }

    public void notifyObservers(Action action) {
        setChanged();
        super.notifyObservers(action);
    }

    @Override
    public void updateAction(Action action) {
        switch(action){
            case MOVE_UP:
                this.y-= speed;
                notifyObservers(Action.MOVE_UP);
                break;
            case MOVE_DOWN:
                this.y+= speed;
                notifyObservers(Action.MOVE_DOWN);
                break;
            case MOVE_LEFT:
                this.x-= speed;
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                this.x+= speed;
                facingRight = true;
                notifyObservers(Action.MOVE_RIGHT);
                break;
            case ATTACK:
                notifyObservers(Action.ATTACK);
                break;
            case HURT:
                this.lives--;
                notifyObservers(Action.HURT);
                break;
            case DIE:
                notifyObservers(Action.DIE);
                break;
            default:
                notifyObservers(Action.IDLE);
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
    public boolean getFacingRight(){
        return facingRight;
    }

    @Override
    public int compareLocation(Entity entity) {
        return 0;
    }

    @Override
    public void spawn() {
    }

    public int addPunteggio(int punti){

        return punteggio+punti;
    }

    public int getPunteggio(){

        return this.punteggio;
    }
}
