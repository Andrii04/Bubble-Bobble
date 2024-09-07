package MODEL;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.ExtendBubble;
import MODEL.Bubbles.GreenBubble;
import VIEW.BubbleView;
import VIEW.MainFrame;

import javax.swing.*;
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
    private Rectangle hitbox;
    private Level currentLevel;
    private Bubble bubbleType;
    private GameStateManager gsm;
    private Action currentAction;
    private Timer cooldownTimer;
    private boolean cooldown = false;
    private ArrayList<ExtendBubble> currentExtendBubbles;
    private String currentLetters;
    //physics
    private boolean isJumping;
    private boolean onFloor;
    private float airSpeed = 0f;

    public Player(UserProfile profile){
        this.profile=profile;
        this.x = 148;
        this.y = 384;
        this.lives = 100; // default
        this.speed = 16; // default
        this.hitbox = new Rectangle(x, y, 32, 32);

        gsm = GameStateManager.getInstance();
        setCurrentLevel(gsm.getCurrentLevel());

        bubbleType = new GreenBubble();
        currentExtendBubbles = new ArrayList<>();
        currentLetters = "";

        cooldownTimer = new Timer(2000, e -> {
            cooldown = false;
        });
    }



    private boolean isNotSolid(){
        if(isJumping && airSpeed<0 && !(this.x+ airSpeed <0 || this.x+ airSpeed > MainFrame.FRAME_WIDTH|| this.y+ airSpeed <0 || this.y+ airSpeed > MainFrame.FRAME_HEIGHT) && isSolidTile(x/Block.WIDTH, y/Block.HEIGHT)){
            return true;
        }
        return false;
    }

    public boolean isColliding(int x, float y) {
        int left = x;
        int right = x +32;
        float top = y;
        float bottom = y+32;

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
                isJumping = true;
                currentAction = Action.JUMP;
                if(isOnFloor()){
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_VERTICALLY:
                currentAction = Action.MOVE_VERTICALLY;
                // if player is in block (error handling), if player isn't colliding with block, if player is jumping up and hitting a non wall block
                if(isColliding(x,y) ||!isColliding(x, y+airSpeed)|| isNotSolid()) {
                    this.y += airSpeed;
                    hitbox.setLocation(x, y);
                    airSpeed += gravity;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                //player is falling to the ground
                else if(isColliding(x, y+airSpeed) && airSpeed > 0){
                    isJumping = false;
                    airSpeed = 0;
                    onFloor = true;
                    notifyObservers(Action.IDLE);
                }
                // when player hits something fall down
                else{
                    isJumping = false;
                    airSpeed = 0;
                    onFloor = false;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_LEFT:
                currentAction = Action.MOVE_LEFT;
                if(!isColliding(x-speed, y)){
                     if (!isOnFloor()){
                         this.x -= airSpeed;
                     }
                     else{
                            this.x -= speed;
                     }
                    hitbox.setLocation(x, y);
            }
                facingRight = false;
                notifyObservers(Action.MOVE_LEFT);
                break;
            case MOVE_RIGHT:
                currentAction = Action.MOVE_RIGHT;
                if(!isColliding(x+speed, y)){
                    if (airSpeed > 0 && !isOnFloor()) {
                        this.x += airSpeed;
                        hitbox.setLocation(x, y);
                    }
                    else{
                        this.x += speed;
                        hitbox.setLocation(x, y);
                    }
            }
                facingRight = true;
                notifyObservers(Action.MOVE_RIGHT);
                break;
            case ATTACK:
                currentAction = Action.ATTACK;
                Bubble firedBubble = bubbleType.newInstance();
                firedBubble.setPlayer(this);
                currentLevel.addBubble(firedBubble);
                firedBubble.fireBubble();
                notifyObservers(Action.ATTACK);
                break;
            case HURT:
                if (!cooldown) {
                    cooldown = true;
                    cooldownTimer.start();
                    currentAction = Action.HURT;
                    this.lives--;
                    System.out.println("Lives: " + lives);
                    notifyObservers(Action.HURT);
                    if (lives <= 0) {
                        notifyObservers(Action.DIE);
                    }
                }
                break;
            case DIE:
                currentAction = Action.DIE;
                notifyObservers(Action.DIE);
                // gsm.setGameState(GameStateManager.GameState.GAMEOVER);
                break;
            default:
                notifyObservers(Action.IDLE);
                System.out.println("pl position: " + x + " " + y);
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

    public Rectangle getHitbox() {return hitbox;}
    public int getLives() {return lives;}

    public Bubble getBubbleType() {return bubbleType;}
    public Level getCurrentLevel() {return currentLevel;}
    public Rectangle getHitbox(int x, int y) {return hitbox;}
    public Action getCurrentAction() {return currentAction;}

    public ArrayList<ExtendBubble> getCurrentExtendBubbles() {return currentExtendBubbles;}

    public void addExtendBubble(ExtendBubble bubble) {

        if (!currentLetters.contains(bubble.getLetter())) {
            currentExtendBubbles.add(bubble);
            currentLetters += bubble.getLetter();
        }
        if (currentLetters.length() == 6) fullExtendBubbles();
    }

    public void fullExtendBubbles() {
        //dona 1 vita in più al giocatore e inizializza le lettere accumulate

        lives++;
        //chiama metodo di ogni ExtendBubble
        currentExtendBubbles.clear();
        currentLetters = "";
    }
}
