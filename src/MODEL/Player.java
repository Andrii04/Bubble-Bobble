package MODEL;

import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.GreenBubble;
import VIEW.MainFrame;

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
    private Rectangle solidArea;
    private Level currentLevel;
    private Bubble bubbleType;

    public Player(UserProfile profile){
        this.profile=profile;
        this.x = 20;
        this.y = 20;
        this.lives = 2; // default
        this.speed = 19; // default
        solidArea = new Rectangle(21,21,30,30);
        this.bubbleType = new GreenBubble(this);
    }


    public boolean isColliding(int x, int y) {
        int left = x+3;
        int right = x +50;
        int top = y+3;
        int bottom = y + 50;

        for (int i = left; i < right; i++) {
            for (int j = top; j < bottom; j++) {
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
                System.out.println("Colliding" + " " + tileX + " " + tileY + " " + currentLevel.getBlockInt(tileY, tileX) + " " + currentLevel.isItSolidBlock(tileY, tileX) + " " + currentLevel.getPattern()[tileY][tileX] + " " + currentLevel.getSolidCheckPattern()[tileY][tileX] );
                return true;
            }
            else{
                System.out.println("Not Colliding");
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
            case MOVE_UP:
                if(!isColliding(x, y-speed)){
                this.y -= speed;
                }
                notifyObservers(Action.MOVE_UP);
                break;
            case MOVE_DOWN:
                if(!isColliding(x, y+speed)) {
                    this.y += speed;
                }
                    notifyObservers(Action.MOVE_DOWN);
                break;
            case MOVE_LEFT:
                if(!isColliding(x-speed, y)){
                this.x -= speed;
            }
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                if(!isColliding(x+speed, y)){
               this.x += speed;
            }
                facingRight = true;
                notifyObservers(Action.MOVE_RIGHT);
                break;
            case ATTACK:
                bubbleType.getBubbleView().fireBubble();
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
    public Bubble getBubbleType() {return bubbleType;}
}
