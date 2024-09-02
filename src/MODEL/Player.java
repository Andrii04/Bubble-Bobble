package MODEL;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.GreenBubble;
import VIEW.BubbleView;
import VIEW.MainFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class Player extends Observable implements Entity {

    private UserProfile profile;
    private int x;
    private int y;
    private int punteggio;
    private int lives;
    private int speed;
    private boolean isMoving;
    private boolean facingRight = true;
    private Rectangle hitbox;
    private Level currentLevel;
    private Bubble bubbleType;
    private GameStateManager gsm;
    private ArrayList<Bubble> bubblesFired;

    //physics
    private boolean onFloor;
    private float airSpeed = 0f;

    public Player(UserProfile profile){
        this.profile=profile;
        this.x = 42;
        this.y = 344;
        this.lives = 2; // default
        this.speed = 16; // default
        this.hitbox = new Rectangle(x, y, 48, 48);

        gsm = GameStateManager.getInstance();
        setCurrentLevel(gsm.getCurrentLevel());

        bubblesFired = new ArrayList<>();
        bubbleType = new GreenBubble();
    }




    public boolean isColliding(int x, float y) {
        int left = x+10;
        int right = x +38;
        float top = y+10;
        float bottom = y + 38;

        int bottomInt = (int)bottom;
        int topInt = (int)top;

        for (int i = left; i < right; i++) {
            for (int j = topInt; j < bottomInt; j++) {
                if (isSolidTile(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isSolidTile(int x ,int y){
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        if(tileX >= 0 && tileX < currentLevel.getPattern()[0].length && tileY >= 0 && tileY < currentLevel.getPattern().length){
            if(currentLevel.isItSolidBlock(tileY, tileX)){
                // test
                //System.out.println("Colliding" + " " + tileX + " " + tileY + " " + currentLevel.getBlockInt(tileY, tileX) + " " + currentLevel.isItSolidBlock(tileY, tileX) + " " + currentLevel.getPattern()[tileY][tileX] + " " + currentLevel.getSolidCheckPattern()[tileY][tileX] + " x: " + x + " y: " + y);
                return true;
            }
            else{
                return false;

            }
        }
        return true;
    }

     public void setCurrentLevel (Level currentLevel) {
         this.currentLevel = currentLevel;
     }
    public void notifyObservers(Action action) {
        setChanged();
        super.notifyObservers(action);
    }
    @Override
    public void updateAction(Action action) {
        switch(action){
            case JUMP:
                if(isOnFloor()){
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_VERTICALLY:
                if(!isColliding(x, y+airSpeed)) {
                    isMoving = true;
                    this.y += airSpeed;
                    hitbox.setLocation(x, y);
                    airSpeed += gravity;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                else if(isColliding(x, y+airSpeed) && airSpeed > 0){
                    airSpeed = 0;
                    onFloor = true;
                    notifyObservers(Action.IDLE);
                }
                else{
                    airSpeed = 0;
                    onFloor = false;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_LEFT:
                if(!isColliding(x-speed, y)){
                     if (airSpeed > 0 && !isOnFloor()){
                        this.x -= airSpeed;
                        hitbox.setLocation(x, y);
                    }
                     else{
                            this.x -= speed;
                            hitbox.setLocation(x, y);
                     }
                     isMoving = true;
            }
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                if(!isColliding(x+speed, y)){
                    if (airSpeed > 0 && !isOnFloor()) {
                        this.x += airSpeed;
                        hitbox.setLocation(x, y);
                    }
                    else{
                        this.x += speed;
                        hitbox.setLocation(x, y);
                    }
                    isMoving = true;
            }
                facingRight = true;
                notifyObservers(Action.MOVE_RIGHT);
                break;
            case ATTACK:
                Bubble firedBubble = bubbleType.newInstance();
                firedBubble.setPlayer(this);
                bubblesFired.add(firedBubble);
                firedBubble.fireBubble();
                notifyObservers(Action.ATTACK);
                break;
            case HURT:
                this.lives--;
                System.out.println("Lives: " + lives);
                notifyObservers(Action.HURT);
                if(lives == 0){
                    notifyObservers(Action.DIE);}
                break;
            case DIE:
                notifyObservers(Action.DIE);
                // gsm.setGameState(GameStateManager.GameState.GAMEOVER);
                break;
            default:
                notifyObservers(Action.IDLE);
                isMoving = false;
                break;
        }
    }

    public boolean getIsMoving(){
        return isMoving;
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
    public void setPunteggio(int punteggio){
        this.punteggio = punteggio;
    }
    public int getPunteggio(){

        return this.punteggio;
    }
    public boolean isOnFloor(){
        return onFloor;
    }
    public void setIsOnFloor(boolean onFloor){
        this.onFloor = onFloor;
    }
    public float getAirSpeed(){
        return airSpeed;
    }
    public void setAirSpeed(int airSpeed){
        this.airSpeed = airSpeed;
    }
    public float getJumpSpeed(){
        return jumpSpeed;
    }

    public float getGravity() {
        return gravity;
    }
    public Rectangle getHitbox() {return hitbox;}
    public int getLives() {return lives;}

    public Bubble getBubbleType() {return bubbleType;}
    public ArrayList<Bubble> getBubblesFired() {return bubblesFired;}
    public void removeBubble(Bubble bubble) {bubblesFired.set(bubblesFired.indexOf(bubble), null);}
    public Level getCurrentLevel() {return currentLevel;}
    public Rectangle getHitbox(int x, int y) {return hitbox;}
}
