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
    private boolean facingRight = true;

    private Level currentLevel;
    private Bubble bubbleType;
    private GameStateManager gsm;
    private ArrayList<Bubble> bubblesFired;

    //physics
    private boolean onFloor;
    private float airSpeed = 0f;

    public Player(UserProfile profile){
        this.profile=profile;
        this.x = 32;
        this.y = 32;
        this.lives = 2; // default
        this.speed = 19; // default


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
            if(currentLevel.getBlockInt(tileY, tileX) >0){
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
                    this.y += airSpeed;
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
                    if(isOnFloor()){
                        this.x -= speed;
                    }
                    else{
                        this.x -= airSpeed;
                    }
            }
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                if(!isColliding(x+speed, y)){
                    if(isOnFloor()) {
                        this.x += speed;
                    }
                    else{
                        this.x += airSpeed;
                    }
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

    public Bubble getBubbleType() {return bubbleType;}
    public ArrayList<Bubble> getBubblesFired() {return bubblesFired;}
    public void removeBubble(Bubble bubble) {bubblesFired.set(bubblesFired.indexOf(bubble), null);}
    public Level getCurrentLevel() {return currentLevel;}
}
