package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Bubbles.Laser;
import MODEL.Entity;

import java.util.*;

/**
 * La classe {@code Invader} rappresenta un nemico nel gioco che può inseguire il giocatore e sparare raggi.
 */
public class Invader extends Enemy {

    /**
     * Crea un'istanza di {@code Invader} con la posizione e la direzione specificate.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public Invader(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 3;
    }

    /**
     * Crea un'istanza di {@code Invader} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public Invader(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }

    /**
     * Gestisce il movimento del nemico per inseguire il giocatore.
     * Determina se il nemico è a terra e aggiorna l'azione di conseguenza.
     */
    public void  chasePlayer() {
        if(player.getLives()<=0){
            stop();
        }
        if (isSolidTile(x, y + Entity.HEIGHT + 1) && isSolidTile(x + Entity.WIDTH, y + Entity.HEIGHT + 1)) {
            onFloor = true;
        } else {
            onFloor = false;
        }
        if(!isSolidTile(x,y+Entity.HEIGHT+1) && !isSolidTile(x+Entity.WIDTH,y+Entity.HEIGHT+1)){
            onFloor = false;
        }
        else{
            onFloor = true;
        }
            updateAction(Action.ATTACK);
            updateAction(Action.IDLE);
    }

    /**
     * Il nemico spara un raggio.
     * Crea un'istanza di {@code Laser}, la aggiunge al livello corrente e la spara.
     */
    void shoot() {
        Laser laser = new Laser(player, this);
        currentLevel.addBubble(laser);
        laser.fireBubble();
        System.out.println("Invader shoots");
    }

    /**
     * Gestisce lo stato del nemico quando è all'interno di una bolla.
     * Ferma il timer di attacco, imposta il nemico come bubbled, avvia il timer di rabbia e notifica gli osservatori.
     */
    void bubbled(){
        stop();
        bubbled = true;
        rageTimer.start();
        notifyObservers(Action.BUBBLED);

    }

    /**
     * Attiva lo stato di rabbia del nemico.
     * Imposta il nemico come enraged, aumenta la velocità e modifica il ritardo tra gli attacchi.
     */
    void rage() {
        enraged = true;
        bubbled = false;
        speed += 1;
        attackTimer.setDelay(800);
    }

    /**
     * Avvia il timer di attacco se non è già in esecuzione.
     */
    void attack() {
        if (!attackTimer.isRunning()) {
            attackTimer.start();
        }
    }
}
