package MODEL;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.GreenBubble;
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
    private ArrayList<Character> extendLetters;



    // physics
    private boolean onFloor;
    private float airSpeed = 0f;

    public Player(UserProfile profile){
        this.profile=profile;
        this.x = 130;
        this.y = 0;
        this.lives = 100; // default
        this.speed = 10; // default
        this.hitbox = new Rectangle(x, y, 32, 32);

        gsm = GameStateManager.getInstance();
        setCurrentLevel(gsm.getCurrentLevel());

        bubbleType = new GreenBubble();
        extendLetters = new ArrayList<>();

        cooldownTimer = new Timer(2000, e -> {
            cooldown = false;
        });
    }

    private boolean isNotSolid(){
        if(airSpeed<0 && y+airSpeed >= Block.HEIGHT){
            return true;
        }
        return false;
    }

    // if any point of entity is colliding with a solid tile
    public boolean isColliding(int x, float y) {
        int leftTile = x; // Leftmost tile
        int rightTile = x + 2 * Block.WIDTH; // Rightmost tile
        int topTile = (int) y; // Topmost tile
        int bottomTile = (int) y + 2 * Block.HEIGHT; // Bottommost tile
        return isSolidTile(rightTile, topTile) || isSolidTile(leftTile, topTile) || isSolidTile(leftTile, bottomTile) || isSolidTile(rightTile, bottomTile);
    }
    // if a given position's tile is solid
    boolean isSolidTile(int x ,int y){
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        return tileX >=0 && tileX < currentLevel.getPattern()[0].length &&
                tileY >= 0 && tileY < currentLevel.getPattern().length &&
                currentLevel.isItSolidBlock(tileY, tileX);

    }

    @Override
    public void updateAction(Action action) {
        switch(action){
            case JUMP:
                currentAction = Action.JUMP;
                if(isOnFloor()){
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                }
                break;
            case MOVE_VERTICALLY:
                currentAction = Action.MOVE_VERTICALLY;
                // if player is in block (error handling), if player isn't colliding with block, if player is jumping up and hitting a non wall block
                if(!isColliding(x, y+airSpeed)|| isNotSolid()) {
                    this.y += airSpeed;
                    hitbox.setLocation(x, y);
                    airSpeed += gravity;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                //top and bottom connected
                if (y + airSpeed >= MainFrame.FRAME_HEIGHT - Entity.HEIGHT) {
                    this.y = 0;
                    hitbox.setLocation(x, y);
                    onFloor = false;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                //player is falling to the ground
                else if(isColliding(x,y+airSpeed)&& airSpeed > 0 ){
                    if( (isSolidTile(x,y)||isSolidTile(x+Entity.HEIGHT,y)|| isSolidTile(x+Block.WIDTH,y))){
                        this.y += airSpeed;
                        hitbox.setLocation(x, y);
                        airSpeed += gravity;
                        notifyObservers(Action.MOVE_VERTICALLY);
                    }
                    else {
                        airSpeed = 0;
                        onFloor = true;
                        notifyObservers(Action.IDLE);
                    }
                }
                break;
            case WALK:
                System.out.println(y);
                    currentAction = Action.WALK;
                    if (!facingRight) {
                        if (!isColliding(x - speed, y)) {
                            this.x -= speed;
                        }
                    } else {
                        if (!isColliding(x + speed, y)) {
                            if (isOnFloor()) {
                                this.x += speed;
                            }
                        }
                    }
                    hitbox.setLocation(x, y);
                    notifyObservers(Action.WALK);
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
    public void setCurrentLevel (Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void notifyObservers(Action action) {
        setChanged();
        super.notifyObservers(action);
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

    // Metodo per ridurre le vite
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    // Aggiungi una vita al giocatore
    public void gainLife() {
        lives++;
    }



    // Metodo per impostare direttamente le vite (se necessario)
    public void setLives(int lives) {
        this.lives = lives;
    }


    // Aggiunta della lettera raccolta
    public void collectExtendLetter(char letter) {
        if (!extendLetters.contains(letter)) {
            extendLetters.add(letter);
        }
    }

    public void resetExtend() {
        extendLetters.clear();
    }

    public ArrayList<Character> getExtendLetters() {
        return extendLetters;
    }



    public Bubble getBubbleType() {return bubbleType;}
    public Level getCurrentLevel() {return currentLevel;}
    public Rectangle getHitbox(int x, int y) {return hitbox;}
    public Action getCurrentAction() {return currentAction;}


    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
}
