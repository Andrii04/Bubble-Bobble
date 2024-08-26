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
    private boolean collides;
    private Rectangle solidArea;
    private Level currentLevel;
    private Bubble bubbleType;

    public Player(UserProfile profile){
        this.solidArea = new Rectangle(0,0,54,54);
        this.profile=profile;
        this.x = 0;
        this.y = 0;
        this.lives = 2; // default
        this.speed = 19; // default
        solidArea = new Rectangle(21,21,30,30);
        this.bubbleType = new GreenBubble(this);
    }

    public void isColliding(){




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
        int leftWorldX = this.x + solidArea.x;
        int rightWorldX = this.x + solidArea.x + solidArea.width;
        int topWorldY = this.y + solidArea.y;
        int bottomWorldY = this.y + solidArea.y+solidArea.height;

        int leftCol = leftWorldX / Block.WIDTH;
        int rightCol = rightWorldX / Block.WIDTH;
        int topRow = topWorldY / Block.HEIGHT;
        int bottomRow = bottomWorldY / Block.HEIGHT;

        int tileNum1, tileNum2;

        switch(action){
            case MOVE_UP:
                topRow = (topWorldY - speed)/Block.HEIGHT;
                if(currentLevel.getBlockInt(leftCol,topRow) == 0 || currentLevel.getBlockInt(rightCol,topRow) == 0){
                    this.y -= speed;
                }
                notifyObservers(Action.MOVE_UP);
                break;
            case MOVE_DOWN:
                bottomRow = (bottomWorldY + speed ) / Block.HEIGHT;
                if(currentLevel.getBlockInt(leftCol,bottomRow) == 0 || currentLevel.getBlockInt(rightCol,bottomRow) == 0) {
                    this.y += speed;
                }
                    notifyObservers(Action.MOVE_DOWN);
                break;
            case MOVE_LEFT:
                leftCol = (leftWorldX- speed ) / Block.HEIGHT;
                if(currentLevel.getBlockInt(leftCol,topRow) == 0 || currentLevel.getBlockInt(leftCol,bottomRow) == 0)
                {
                    this.x -= speed;
                }
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                rightCol = (rightWorldX+speed) / Block.HEIGHT;
                if(currentLevel.getBlockInt(rightCol,topRow) == 0 || currentLevel.getBlockInt(rightCol,bottomRow) == 0){
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
