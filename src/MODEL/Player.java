package MODEL;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bubble;
import MODEL.Bubbles.GreenBubble;
import MODEL.PowerUps.*;
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
    private int defaultSpeed = 16;
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
    private boolean isJumping;
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
        this.x = 148;
        this.y = 384;
        this.lives = 3; // default
        this.speed = defaultSpeed; // default
        this.hitbox = new Rectangle(x, y, 32, 32);

        gsm = GameStateManager.getInstance();
        setCurrentLevel(gsm.getCurrentLevel());

        bubbleType = new GreenBubble(this);
        extendLetters = new ArrayList<>();

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

    private boolean isNotSolid(){
        if(isJumping && airSpeed<0 && !(this.x+ airSpeed <0 || this.x+ airSpeed > MainFrame.FRAME_WIDTH|| this.y+ airSpeed <0 || this.y+ airSpeed > MainFrame.FRAME_HEIGHT) && isSolidTile(x/Block.WIDTH, y/Block.HEIGHT)){
            return true;
        }
        return false;
    }

    public boolean isColliding(int x, float y) {
        int left = x;
        int right = x + Entity.WIDTH-1;
        float top = y;
        float bottom = y+Entity.HEIGHT-1;

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

                if (blueRing) {/* +10 punti */}

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

                if (blueRing) {/* +10 punti */}

                distanceTraveled += speed;
                if (distanceTraveled >= (MainFrame.FRAME_WIDTH-Block.WIDTH*2)*15) {
                    currentLevel.spawnPowerUp(new Shoe());
                    distanceTraveled = 0;
                }

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
