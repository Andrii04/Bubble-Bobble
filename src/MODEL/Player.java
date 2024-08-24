package MODEL;

import java.util.Observable;

public class Player extends Observable implements Entity {

    private UserProfile profile;
    private int x;
    private int y;
    private int punteggio;
    private int lives;
    private int speed;
    private boolean facingRight;
    public Player(UserProfile profile){

        this.profile=profile;
        this.x = 0;
        this.y = 0;
        this.lives = 2; // default
        this.speed = 3; // default

    }


    @Override
    public void updatelocation(int x, int y) {

    }

    public void moveRight(){
        this.x+= speed;
        this.facingRight=true;
        setChanged();
        notifyObservers("moveRight");
    }
    public void moveLeft(){
        this.x-= speed;
        this.facingRight=false;
        setChanged();
        notifyObservers("moveLeft");
    }
    public void moveUp(){
        this.y-= speed;
        setChanged();
        notifyObservers("moveUp");
    }
    public void moveDown(){
        this.y+= speed;
        setChanged();
        notifyObservers("moveDown");
    }
    @Override
    public void attack() {
        setChanged();
        notifyObservers("attack");
    }

    @Override
    public void die() {
        setChanged();
        notifyObservers("die");
    }
    public void idle(){
        setChanged();
        notifyObservers("idle");
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
