package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.IncendioFireball;
import MODEL.Entity;

/**
 * La classe {@code Incendio} rappresenta un nemico nel gioco che si muove velocemente, ha capacità limitate di salto e spara palle di fuoco.
 */
public class Incendio extends Enemy {

    //cammina veloce
    //scarso nel salto
    //spara palle di fuoco
    //continua a camminare quando spara

    private final int points=3000;

    /**
     * Crea un'istanza di {@code Incendio} con la posizione e la direzione specificate.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public Incendio(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 3;
    }

    /**
     * Crea un'istanza di {@code Incendio} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public Incendio(GameStateManager gsm) {
        super(gsm);
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
     * Gestisce il movimento del nemico.
     * Se il nemico è bubbled o morto, notifica gli osservatori con le azioni appropriate.
     * Altrimenti, se il nemico è vivo e non bubbled, gestisce l'attacco e l'inseguimento del giocatore.
     */
    public void move(){
        if(player.getLives()<=0){
            stop();
        }
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        if(!isBubbled() && !isDead()){
            if(player.getY() != y && attackTimer.isRunning()){
                attackTimer.stop();
            }
            else if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            onPlayer();
            chasePlayer();
        }
    }

    /**
     * Il nemico spara una palla di fuoco.
     * Crea un'istanza di {@code IncendioFireball}, la aggiunge al livello corrente e la spara.
     */
    void shoot() {
        // to do
        IncendioFireball fireball = new IncendioFireball(player, this);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
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
}


