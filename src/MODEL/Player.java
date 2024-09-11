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


/**
 * Rappresenta un giocatore nel gioco, gestendo posizione, punteggio, vite e azioni.
 * La classe estende {@link Observable} per permettere la notifica degli osservatori
 * riguardo ai cambiamenti dello stato del giocatore.
 */
public class Player extends Observable implements Entity {

    private UserProfile profile;
    public static int defaultX = 20;
    public static int defaultY = 623; ;
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
    private int letterEcount;
    private static final String EXTEND = "EXTEND";  // La sequenza corretta

    // fisica del giocatore
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


    /**
     * Crea un nuovo giocatore con il profilo specificato.
     *
     * @param profile il profilo utente associato al giocatore
     */
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

        cooldownTimer = new Timer(1500, e -> {
            cooldown = false;
            notifyObservers(Action.IDLE);
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
        letterEcount = 0;

        if (((profile.getPartiteTot()) % 5) == 0) currentLevel.spawnPowerUp(new BlueLantern());
    }


    /**
     * Verifica se il giocatore può attraversare un blocco solido
     *
     * @return true se il giocatore sta saltando, altrimenti false
     */
    boolean isNotSolid(){
        if(airSpeed<0 ){
            return true;
        }
        return false;
    }

    /**
     * Verifica se il giocatore sta collidendo con un blocco solido nella posizione specificata.
     *
     * @param x la coordinata x della posizione da controllare
     * @param y la coordinata y della posizione da controllare
     * @return true se c'è una collisione con un blocco solido, altrimenti false
     */
    boolean isColliding(int x, float y) {
        int leftTile = x; // Leftmost tile
        int rightTile = x + 2 * Block.WIDTH; // Rightmost tile
        int topTile = (int) y; // Topmost tile
        int bottomTile = (int) y + 2 * Block.HEIGHT; // Bottommost tile
        return isSolidTile(rightTile, topTile) || isSolidTile(leftTile, topTile) || isSolidTile(leftTile, bottomTile) || isSolidTile(rightTile, bottomTile);
    }

    /**
     * Verifica se il tile nella posizione specificata è solido.
     *
     * @param x la coordinata x del tile da controllare
     * @param y la coordinata y del tile da controllare
     * @return true se il tile è solido, altrimenti false
     */
    boolean isSolidTile(int x ,int y){
        int tileX = x / Block.WIDTH;
        int tileY = y / Block.HEIGHT;
        return tileX >=0 && tileX < currentLevel.getPattern()[0].length &&
                tileY >= 0 && tileY < currentLevel.getPattern().length &&
                currentLevel.isItSolidBlock(tileY, tileX);

    }

    /**
     * Aggiorna l'azione corrente del giocatore.
     *
     * @param action l'azione da eseguire
     */
    @Override
    public void updateAction(Action action) {
        switch(action){
            case JUMP: // setta airSpeed (move vertically gestisce il movimento)
                currentAction = Action.JUMP;
                if(isOnFloor()){
                    MainFrame.playSound(3);
                    onFloor = false;
                    airSpeed = jumpSpeed;
                    updateAction(Action.MOVE_VERTICALLY);
                    if (pinkRing) {
                        punteggio += 100;
                    }

                    jumpsCount++;
                    if (jumpsCount >= 35) {
                        currentLevel.spawnPowerUp(new GreenCandy());
                        jumpsCount = 0;
                    }
                }
                break;
            case MOVE_VERTICALLY:
                currentAction = Action.MOVE_VERTICALLY;
                // player sta per aria (saltando/cadendo)
                if(!isColliding(x, y+airSpeed)|| isNotSolid()) {
                    this.y += airSpeed;
                    hitbox.setLocation(x, y);
                    airSpeed += gravity;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                //se tocca il bordo inferiore dello schermo ricomincia da sopra
                if (y + airSpeed >= MainFrame.FRAME_HEIGHT - Entity.HEIGHT) {
                    this.y = 0;
                    hitbox.setLocation(x, y);
                    onFloor = false;
                    notifyObservers(Action.MOVE_VERTICALLY);
                }
                // player sta toccando il suolo
                else if(isColliding(x,y+airSpeed)&& airSpeed > 0 ){
                    // può fermarsi solo se è un pavimento e non un muro
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

                if (blueRing) {
                    punteggio += 10;
                }

                break;
            case WALK: // player si muove orrizontalmente
                    currentAction = Action.WALK;
                    if (!facingRight) {
                        if (!isColliding(x - speed, y)) {
                            this.x -= speed;
                            if (blueRing) {
                                punteggio += 10;
                            }
                        }
                    } else {
                        if (!isColliding(x + speed, y)) {
                            if (isOnFloor()) {
                                this.x += speed;
                                if (blueRing) {
                                    punteggio += 10;
                                }
                            }
                        }
                    }
                    if(!isColliding(x,y+Entity.HEIGHT+1)){
                        onFloor = false;
                    }
                    hitbox.setLocation(x, y);
                    notifyObservers(Action.WALK);

                distanceTraveled += speed;
                if (distanceTraveled >= (MainFrame.FRAME_WIDTH-Block.WIDTH*2)*15) {
                    currentLevel.spawnPowerUp(new Shoe());
                    distanceTraveled = 0;
                }
                break;
            case ATTACK:
                if (ableToFire) {
                    MainFrame.playSound(0);
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
            case HURT: // puo essere colpito solo se non è in cooldown
                if (!cooldown) {
                    MainFrame.playSound(2);
                    cooldown = true;
                    cooldownTimer.start();
                    currentAction = Action.HURT;
                    this.lives--;
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
                MainFrame.playSound(1);
                currentAction = Action.DIE;
                notifyObservers(Action.DIE);
                break;
            default: // idle : quando è fermo
                notifyObservers(Action.IDLE);
                break;
        }
    }

    /**
     * Imposta la coordinata x del giocatore.
     *
     * @param x la nuova coordinata x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Imposta la coordinata y del giocatore.
     *
     * @param y la nuova coordinata y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Restituisce se il giocatore sta guardando verso destra.
     *
     * @return true se il giocatore sta guardando verso destra, altrimenti false
     */
    public boolean getFacingRight() {
        return facingRight;
    }

    /**
     * Imposta il livello corrente del giocatore.
     *
     * @param currentLevel il livello corrente
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Notifica gli osservatori riguardo a un cambiamento dell'azione.
     *
     * @param action l'azione che ha cambiato
     */
    public void notifyObservers(Action action) {
        setChanged();
        super.notifyObservers(action);
    }


    /**
     * Imposta il punteggio del giocatore.
     *
     * @param punteggio il nuovo punteggio
     */
    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }
    /**
     * Imposta se il giocatore è a terra.
     *
     * @param onFloor true se il giocatore è a terra, altrimenti false
     */
    public void setIsOnFloor(boolean onFloor) {
        this.onFloor = onFloor;
    }

    /**
     * Aggiunge una vita al giocatore.
     */
    public void gainLife() {
        lives++;
    }

    /** Imposta se il giocatore sta guardando verso destra.
     *
     * @param facingRight
     */
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    /**
     * Restituisce il punteggio del giocatore.
     *
     * @return il punteggio
     */
    public int getPunteggio() {
        return this.punteggio;
    }

    /**
     * Restituisce se il giocatore è attualmente a terra.
     *
     * @return true se il giocatore è a terra, altrimenti false
     */
    public boolean isOnFloor() {
        return onFloor;
    }

    /**
     * Restituisce la velocità dell'aria del giocatore.
     *
     * @return la velocità dell'aria
     */
    public float getAirSpeed() {
        return airSpeed;
    }

    /**
     * Restituisce la hitbox del giocatore.
     *
     * @return la hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * Restituisce il numero di vite del giocatore.
     *
     * @return il numero di vite
     */
    public int getLives() {
        return lives;
    }
    /**
     * Restituisce la coordinata x del giocatore.
     *
     * @return la coordinata x
     */
    public int getX() {
        return x;
    }

    /**
     * Restituisce la coordinata y del giocatore.
     *
     * @return la coordinata y
     */
    public int getY() {
        return y;
    }

    /**
     * Restituisce il livello corrente del giocatore.
     *
     * @return il livello corrente
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Restituisce la hitbox del giocatore basata su coordinate specificate.
     *
     * @param x la coordinata x
     * @param y la coordinata y
     * @return la hitbox
     */
    public Rectangle getHitbox(int x, int y) {
        return hitbox;
    }

    /**
     * Restituisce l'azione corrente del giocatore.
     *
     * @return l'azione corrente
     */
    public Action getCurrentAction() {
        return currentAction;
    }
    /**
     * Restituisce il profilo utente del giocatore.
     *
     * @return il profilo utente
     */
    public UserProfile getProfile() {
        return profile;
    }


    /**
     * Aggiunge una bolla Extend al giocatore.
     *
     * @param bubble la bolla Extend da aggiungere
     */
    public void addExtendBubble(ExtendBubble bubble) {
        String letter = bubble.getLetter();
        if (!letter.equals("E") && !extendBubbles.contains(letter)) {
            extendBubbles.add(letter);
        } else if (letter.equals("E")) {
            if (letterEcount <= 1) {
                extendBubbles.add(letter);
                letterEcount++;
            }
        }
        checkExtendCompletion();
    }

    /**
     * Resetta le bolle Extend del giocatore.
     */
    private void resetExtend() {
        extendBubbles.clear();
    }

    /**
     * Restituisce la lista delle bolle Extend raccolte dal giocatore.
     *
     * @return la lista delle bolle Extend
     */
    public ArrayList<String> getExtendBubbles() {
        return extendBubbles;
    }

    /**
     * Controlla se il giocatore ha completato la sequenza di bolle Extend e, in tal caso, guadagna una vita.
     */
    private void checkExtendCompletion() {
        if (extendBubbles.size() == EXTEND.length()) {
            gainLife();
            resetExtend();
        }
    }
    /**
     * Restituisce la distanza massima che le bolle possono raggiungere.
     *
     * @return la distanza massima delle bolle
     */
    public int getMaxBubbleDistance() {
        return maxBubbleDistance;
    }

    /**
     * Restituisce la velocità delle bolle.
     *
     * @return la velocità delle bolle
     */
    public int getBubbleSpeed() {
        return bubbleSpeed;
    }

    /**
     * Restituisce il timer per il tasso di fuoco delle bolle.
     *
     * @return il timer del tasso di fuoco
     */
    public Timer getFireRate() {
        return fireRate;
    }

    /**
     * Restituisce se il giocatore ha l'anello rosa.
     *
     * @return true se il giocatore ha l'anello rosa, altrimenti false
     */
    public boolean isPinkRing() {
        return pinkRing;
    }

    /**
     * Restituisce se il giocatore ha l'anello rosso.
     *
     * @return true se il giocatore ha l'anello rosso, altrimenti false
     */
    public boolean isRedRing() {
        return redRing;
    }
    /**
     * Restituisce se il giocatore ha l'anello blu.
     *
     * @return true se il giocatore ha l'anello blu, altrimenti false
     */
    public boolean isBlueRing() {
        return blueRing;
    }


    /**
     * Imposta la distanza massima che le bolle possono raggiungere.
     *
     * @param num la nuova distanza massima delle bolle
     */
    public void setMaxBubbleDistance(int num) {
        maxBubbleDistance = num;
    }

    /**
     * Imposta la velocità delle bolle.
     *
     * @param num la nuova velocità delle bolle
     */
    public void setBubbleSpeed(int num) {
        bubbleSpeed = num;
    }

    /**
     * Imposta il tasso di fuoco delle bolle con un nuovo intervallo.
     *
     * @param delay il nuovo intervallo in millisecondi
     */
    public void setFireRate(int delay) {
        Timer newFireRate = new Timer(delay, e -> {
            ableToFire = true;
            fireRate.stop();
        });
        fireRate = newFireRate;
    }

    /**
     * Imposta la velocità del giocatore.
     *
     * @param num la nuova velocità del giocatore
     */
    public void setSpeed(int num) {
        speed = num;
    }

    /**
     * Imposta se il giocatore ha l'anello rosa.
     *
     * @param bool true se il giocatore ha l'anello rosa, altrimenti false
     */
    public void setPinkRing(boolean bool) {
        pinkRing = bool;
    }

    /**
     * Imposta se il giocatore ha l'anello rosso.
     *
     * @param bool true se il giocatore ha l'anello rosso, altrimenti false
     */
    public void setRedRing(boolean bool) {
        redRing = bool;
    }

    /**
     * Imposta se il giocatore ha l'anello blu.
     *
     * @param bool true se il giocatore ha l'anello blu, altrimenti false
     */
    public void setBlueRing(boolean bool) {
        blueRing = bool;
    }

    /**
     * Incrementa il contatore delle bolle esplose e controlla se deve spawnare un PowerUp.
     */
    public void bubbleExploded() {
        bubblesExploded++;
        if (bubblesExploded >= 35) {
            currentLevel.spawnPowerUp(new BlueCandy());
            bubblesExploded = 0;
        }
    }

    /**
     * Incrementa il contatore delle bolle d'acqua esplose e controlla se deve spawnare un PowerUp.
     */
    public void waterBubbleExploded() {
        waterBubblesExploded++;
        if (waterBubblesExploded == 15) currentLevel.spawnPowerUp(new OrangeUmbrella());
        else if (waterBubblesExploded == 20) currentLevel.spawnPowerUp(new PinkUmbrella());
        else if (waterBubblesExploded >= 25) {
            currentLevel.spawnPowerUp(new RedUmbrella());
            waterBubblesExploded = 0;
        }
    }

    /**
     * Incrementa il contatore delle bolle di fulmine esplose e controlla se deve spawnare un PowerUp.
     */
    public void lightningBubblesExploded() {
        lightningBubblesExploded++;
        if (lightningBubblesExploded >= 12 && gsm.getCurrentLevelInt() != 24) {
            currentLevel.spawnPowerUp(new Clock());
            lightningBubblesExploded = 0;
        }
    }

    /**
     * Incrementa il contatore delle caramelle rosa mangiate e controlla se deve spawnare un PowerUp.
     */
    public void eatPinkCandy() {
        eatenPinkCandies++;
        if (eatenPinkCandies >= 3) {
            currentLevel.spawnPowerUp(new PinkRing());
            eatenPinkCandies = 0;
        }
    }

    /**
     * Incrementa il contatore delle caramelle verdi mangiate e controlla se deve spawnare un PowerUp.
     */
    public void eatGreenCandy() {
        eatenGreenCandies++;
        if (eatenGreenCandies >= 3) {
            currentLevel.spawnPowerUp(new RedRing());
            eatenGreenCandies = 0;
        }
    }

    /**
     * Incrementa il contatore delle caramelle blu mangiate e controlla se deve spawnare un PowerUp.
     */
    public void eatBlueCandy() {
        eatenBlueCandies++;
        if (eatenBlueCandies >= 3) {
            currentLevel.spawnPowerUp(new BlueRing());
            eatenBlueCandies = 0;
        }
    }

}