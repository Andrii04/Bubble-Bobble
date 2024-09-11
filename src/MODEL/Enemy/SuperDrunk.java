package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.Bottle;
import MODEL.Entity;
import VIEW.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code SuperDrunk} rappresenta un nemico nel gioco con capacità avanzate come sparare bolle e inseguire il giocatore.
 * Questo nemico ha un numero di vite, un timer per le sue azioni, e può passare in uno stato di rabbia che aumenta la sua velocità e modifica il suo comportamento.
 */
public class SuperDrunk extends Enemy {

    /** Numero di vite iniziali del nemico. */
    private int lives = 2;

    /** Indica se il nemico sta andando verso l'alto. */
    private boolean goUp;

    /** Punti guadagnati per la morte di questo nemico. */
    private final int points = 10000;

    /** Timer per il ritardo dell'inizio delle azioni del nemico. */
    private Timer startTimer;

    /** Timer per il cooldown dopo che il nemico è stato ferito. */
    private Timer hurtCooldown;

    /** Timer per il ritardo tra i lanci di bolle. */
    private Timer attackTimer;

    /** Indica se il nemico può essere ferito. */
    private boolean canBeHurt;

    /**
     * Crea un'istanza di {@code SuperDrunk} con la posizione e la direzione specificate.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public SuperDrunk(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 3;
        updateAction(Action.IDLE);
        startTimer = new Timer(1000, e -> {
            startTimer.stop();
            updateAction(Action.WALK);
            canBeHurt = true;
        });
        hurtCooldown = new Timer(500, e -> {
            canBeHurt = true;
            updateAction(Action.WALK);
            hurtCooldown.stop();
        });
        attackTimer = new Timer(1000, e -> shoot());
        attackTimer.setRepeats(true);
        super.hitbox = new Rectangle(x, y, 48*2, 51*2);
        startTimer.start();
        attackTimer.start();
    }

    /**
     * Crea un'istanza di {@code SuperDrunk} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public SuperDrunk(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }

    /**
     * Verifica se il nemico sta collidendo con i bordi del livello.
     *
     * @param x La posizione x da controllare.
     * @param y La posizione y da controllare.
     * @return {@code true} se il nemico sta collidendo con i bordi, {@code false} altrimenti.
     */
    public boolean isColliding(int x, int y) {
        if (x <= 17 || x > 660 || y <= 17 || y >= 560) {
            return true;
        }
        return false;
    }

    /**
     * Il nemico spara bolle in diverse direzioni.
     * Le bolle sono create e aggiunte al livello corrente.
     */
    public void shoot() {
        if (currentLevel != null) {
            Bottle bottle1;
            Bottle bottle2;
            Bottle bottle3;
            Bottle bottle4;
            Bottle bottle5;

            if (facingRight) {
                bottle1 = new Bottle(player, this, Bottle.BottleTrajectory.UPUP_RIGHT);
                bottle2 = new Bottle(player, this, Bottle.BottleTrajectory.UP_RIGHT);
                bottle3 = new Bottle(player, this, Bottle.BottleTrajectory.HORIZONTAL_RIGHT);
                bottle4 = new Bottle(player, this, Bottle.BottleTrajectory.DOWN_RIGHT);
                bottle5 = new Bottle(player, this, Bottle.BottleTrajectory.DOWNDOWN_RIGHT);
            } else {
                bottle1 = new Bottle(player, this, Bottle.BottleTrajectory.UPUP_LEFT);
                bottle2 = new Bottle(player, this, Bottle.BottleTrajectory.UP_LEFT);
                bottle3 = new Bottle(player, this, Bottle.BottleTrajectory.HORIZONTAL_LEFT);
                bottle4 = new Bottle(player, this, Bottle.BottleTrajectory.DOWN_LEFT);
                bottle5 = new Bottle(player, this, Bottle.BottleTrajectory.DOWNDOWN_LEFT);
            }

            currentLevel.addBubble(bottle1);
            currentLevel.addBubble(bottle2);
            currentLevel.addBubble(bottle3);
            currentLevel.addBubble(bottle4);
            currentLevel.addBubble(bottle5);

            bottle1.fireBubble();
            bottle2.fireBubble();
            bottle3.fireBubble();
            bottle4.fireBubble();
            bottle5.fireBubble();
            // shoot
            // System.out.println("SuperDrunk shooting");
        }
    }

    /**
     * Gestisce la morte del nemico.
     * Ferma il timer di attacco.
     */
void onPlayer(){
        if(hitbox.intersects(player.getHitbox())){
            if(bubbled){
                updateAction(Action.DIE);
            }
            else{
                player.updateAction(Action.HURT);
            }
        }
}
    void chasePlayer(){
        // pong mechanics
        if (isColliding(x+speed,y)){
            facingRight = false;
        }
        else if(isColliding(x- speed,y)){
            facingRight = true;
        }
        if(isColliding(x,y+ speed)){
            goUp = true;
        }
        else if(isColliding(x,y- speed)){
            goUp = false;
        }
        updateAction(Action.WALK);
    }

    /**
     * Aggiorna l'azione del nemico in base allo stato attuale.
     *
     * @param action L'azione da eseguire.
     */
    @Override
    public void updateAction(Action action) {
        switch (action) {
            default:
                notifyObservers(Action.IDLE);
                break;
            case WALK:
                if (!attackTimer.isRunning()) {
                    attackTimer.start();
                }
                if (facingRight) {
                    this.x += speed;
                } else {
                    this.x -= speed;
                }
                if (goUp) {
                    this.y -= speed;
                } else {
                    this.y += speed;
                }
                hitbox.setLocation(x,y);
                if(enraged){
                    notifyObservers(Action.RAGE);
                }
                else{
                    notifyObservers(Action.WALK);
                }
                break;
            case HURT:
                MainFrame.playSound(2);
                lives--;
                if( lives <= 0){
                    enraged = false;
                    updateAction(Action.BUBBLED);
                }
                else if(canBeHurt){
                    canBeHurt = false;
                    hurtCooldown.start();
                }
                break;
            case BUBBLED:
                stop();
                bubbled();
                break;
            case RAGE:
                rage();
                notifyObservers(Action.RAGE);
                break;
            case DIE:
                stop();
                dead = true;
                wave = false;
                player.setPunteggio(player.getPunteggio() + points);
                deathTimer.start();
                MainFrame.playSound(5);
                notifyObservers(Action.DIE);
                break;
        }
    }

    /**
     * Attiva lo stato di rabbia del nemico.
     * Aumenta le vite, la velocità e cambia il ritardo tra gli attacchi.
     */
    void rage(){
        lives = 5;
        enraged = true;
        bubbled = false;
        speed += 2;
        attackTimer.setDelay(800);
        updateAction(Action.WALK);
    }

    /**
     * Gestisce il movimento del nemico in base allo stato corrente.
     * Se il nemico è dentro una bolla o morto, esegue le azioni appropriate.
     */
    @Override
    public void move(){
        if(isBubbled()) {
            if (hitbox.intersects(player.getHitbox())) {
                updateAction(Action.DIE);
                notifyObservers(Action.DIE);
                return;
            }
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        else if(!isDead() && !isBubbled()){
            onPlayer();
            chasePlayer();
        }
    }
}
