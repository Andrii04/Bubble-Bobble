package MODEL;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.ExtendBubble;
import MODEL.Bubbles.GreenBubble;
import MODEL.PowerUps.*;
import VIEW.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

public class Player extends Observable implements Entity {

    private UserProfile profile;
    public static int defaultX = 148;
    public static int defaultY = 384; ;
    private int x;
    private int y;
    private int punteggio;
    private int lives;
    private int speed;
    private int defaultSpeed = 8;
    private boolean facingRight = true;
    private Rectangle hitbox;
    private Level currentLevel;
    private Bubble bubbleType;
    private GameStateManager gsm;
    private Action currentAction;
    private Timer cooldownTimer;
    private boolean cooldown = false;
    private ArrayList<String> extendBubbles;
    private static final String EXTEND = "EXTEND";  // La sequenza corretta



    // physics
    private boolean onFloor;
    private float airSpeed = 0f;

    private boolean pinkRing;
    private boolean redRing;
    private boolean blueRing;

    private int defaultMaxBubbleDistance = 30;
    private int defaultBubbleSpeed = 7;

    private int maxBubbleDistance;
    private int bubbleSpeed;

    private Timer fireRate;
    private Timer defaultFireRate = new Timer(300, e -> {
        ableToFire = true;
        fireRate.stop();
    });

    private boolean ableToFire;

    private int bubblesFired;
    private int bubblesExploded;
    private int waterBubblesExploded;
    private int lightningBubblesExploded;
    private int jumpsCount;

    private int eatenPinkCandies;
    private int eatenBlueCandies;
    private int eatenGreenCandies;

    private int distanceTraveled;

    private boolean blueLantern;

    public Player(UserProfile profile){
        this.profile=profile;
        this.x = defaultX;
        this.y = defaultY;
        this.lives = 3; // default
        this.speed = defaultSpeed; // default
        this.hitbox = new Rectangle(x, y, 32, 32);

        gsm = GameStateManager.getInstance();
        setCurrentLevel(gsm.getCurrentLevel());

        bubbleType = new GreenBubble(this);
        this.extendBubbles = new ArrayList<>();

        cooldownTimer = new Timer(2000, e -> {
            cooldown = false;
        });

        pinkRing = false;
        redRing = false;
        blueRing = false;

        maxBubbleDistance = defaultMaxBubbleDistance;
        bubbleSpeed = defaultBubbleSpeed;

        ableToFire = true;
        fireRate = defaultFireRate;

        bubblesFired = 0;
        bubblesExploded = 0;
        waterBubblesExploded = 0;
        lightningBubblesExploded = 0;
        jumpsCount = 0;

        eatenGreenCandies = 0;
        eatenPinkCandies = 0;
        eatenBlueCandies = 0;

        if ((profile.getPartiteTot() % 5) == 0) currentLevel.spawnPowerUp(new BlueLantern());
    }

    boolean isNotSolid(){
        if(airSpeed<0 && y+airSpeed >= Block.HEIGHT){
            return true;
        }
        return false;
    }

    // if any point of entity is colliding with a solid tile
    boolean isColliding(int x, float y) {
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
                    if (pinkRing) {/* +100 punti*/}

                    jumpsCount++;
                    if (jumpsCount >= 35) {
                        currentLevel.spawnPowerUp(new GreenCandy());
                        jumpsCount = 0;
                    }
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
                    if( (isSolidTile(x,y)||isSolidTile(x+Entity.WIDTH,y)|| isSolidTile(x+Block.WIDTH,y))){
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

                if (blueRing) {/* +10 punti */}

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
                    if(!isColliding(x,y+Entity.HEIGHT+1)){
                        onFloor = false;
                    }
                    hitbox.setLocation(x, y);
                    notifyObservers(Action.WALK);
                if (blueRing) {/* +10 punti */}

                distanceTraveled += speed;
                if (distanceTraveled >= (MainFrame.FRAME_WIDTH-Block.WIDTH*2)*15) {
                    currentLevel.spawnPowerUp(new Shoe());
                    distanceTraveled = 0;
                }
                break;
            case ATTACK:
                if (ableToFire) {
                    currentAction = Action.ATTACK;
                    Bubble firedBubble = bubbleType.newInstance(this);
                    currentLevel.addBubble(firedBubble);
                    firedBubble.fireBubble();
                    ableToFire = false;
                    fireRate.start();
                    notifyObservers(Action.ATTACK);

                    bubblesFired++;
                    if (bubblesFired >= 35) {
                        currentLevel.spawnPowerUp(new PinkCandy());
                        bubblesFired = 0;
                    }
                }
                break;
            case HURT:
                if (!cooldown) {
                    cooldown = true;
                    cooldownTimer.start();
                    currentAction = Action.HURT;
                    this.lives--;
                    System.out.println("Lives: " + lives);
                    notifyObservers(Action.HURT);
                    if(lives < 0){
                        lives =0;
                    }
                    if (lives == 0) {
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
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
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

    public void addExtendBubble(ExtendBubble bubble) {
        String letter = bubble.getLetter();
        if (!extendBubbles.contains(letter)) {
            extendBubbles.add(letter);
            checkExtendCompletion();
        }
    }

    private void resetExtend() {
        extendBubbles.clear();
        System.out.println("Reset bolle Extend!");
    }

    public ArrayList<String> getExtendBubbles() {
        return extendBubbles;
    }

    private void checkExtendCompletion() {
        if (extendBubbles.size() == EXTEND.length()) {
            gainLife();
            resetExtend();
        }
    }



    public Bubble getBubbleType() {return bubbleType;}
    public Level getCurrentLevel() {return currentLevel;}
    public Rectangle getHitbox(int x, int y) {return hitbox;}
    public Action getCurrentAction() {return currentAction;}

    public int getMaxBubbleDistance() {return maxBubbleDistance;}
    public int getBubbleSpeed() {return bubbleSpeed;}
    public void setMaxBubbleDistance(int num) {maxBubbleDistance = num;}
    public void setBubbleSpeed(int num) {bubbleSpeed = num;}

    public Timer getFireRate() {return fireRate;}
    public void setFireRate(int delay) {
        Timer newFireRate = new Timer(delay, e -> {
            ableToFire = true;
            fireRate.stop();
        });
        fireRate = newFireRate;
    }

    public void setSpeed(int num) {speed = num;}

    public boolean isPinkRing() {return pinkRing;}
    public boolean isRedRing() {return redRing;}
    public boolean isBlueRing() {return blueRing;}
    public void setPinkRing(boolean bool) {pinkRing = bool;}
    public void setRedRing(boolean bool) {redRing = bool;}
    public void setBlueRing(boolean bool) {blueRing = bool;}

    public void bubbleExploded() {
        bubblesExploded++;
        if (bubblesExploded >= 35) {
            currentLevel.spawnPowerUp(new BlueCandy());
            bubblesExploded = 0;
        }
    }
    public void waterBubbleExploded() {
        waterBubblesExploded++;
        if (waterBubblesExploded == 15) currentLevel.spawnPowerUp(new OrangeUmbrella());
        else if (waterBubblesExploded == 20) currentLevel.spawnPowerUp(new PinkUmbrella());
        else if (waterBubblesExploded >= 25) {
            currentLevel.spawnPowerUp(new RedUmbrella());
            waterBubblesExploded = 0;
        }
    }
    public void lightningBubblesExploded() {
        lightningBubblesExploded++;
        if (lightningBubblesExploded >= 12) {
            currentLevel.spawnPowerUp(new Clock());
            lightningBubblesExploded = 0;
        }
    }

    public void eatPinkCandy() {
        eatenPinkCandies++;
        if (eatenPinkCandies >= 3) {
            currentLevel.spawnPowerUp(new PinkRing());
            eatenPinkCandies = 0;
        }
    }
    public void eatGreenCandy() {
        eatenGreenCandies++;
        if (eatenGreenCandies >= 3) {
            currentLevel.spawnPowerUp(new RedRing());
            eatenGreenCandies = 0;
        }
    }
    public void eatBlueCandy() {
        eatenBlueCandies++;
        if (eatenBlueCandies >= 3) {
            currentLevel.spawnPowerUp(new BlueRing());
            eatenBlueCandies = 0;
        }
    }
    public void setFacingRight(boolean bool) {facingRight = bool;}
}
