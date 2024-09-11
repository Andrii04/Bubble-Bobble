package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Entity;

/**
 * La classe {@code BoaBoa} rappresenta un nemico volante con movimento veloce.
 *
 * <p>La classe estende {@code Enemy} e implementa un nemico che si sposta rapidamente e può cambiare direzione quando incontra ostacoli.
 */
public class BoaBoa extends Enemy {

    // vola
    // movimento veloce

    private final int points = 4000;
    private boolean goUp;

    /**
     * Crea un'istanza di {@code BoaBoa} con la posizione, la direzione e il gestore dello stato del gioco specificati.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public BoaBoa(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 3;
    }

    /**
     * Crea un'istanza di {@code BoaBoa} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public BoaBoa(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }

    /**
     * Gestisce l'inseguimento del giocatore.
     * Modifica la direzione del nemico in base alla presenza di ostacoli e determina se il nemico deve muoversi verso l'alto o verso il basso.
     */
    void chasePlayer() {
        // pong mechanics
        if (isSolidTile(x + speed + Entity.WIDTH, y)) {
            facingRight = false;
        } else if (isSolidTile(x - speed, y)) {
            facingRight = true;
        }
        if (isSolidTile(x, y + speed + Entity.HEIGHT)) {
            goUp = true;
        } else if (isSolidTile(x, y - speed)) {
            goUp = false;
        }
        updateAction(Action.WALK);
    }

    /**
     * Gestisce il movimento del nemico.
     * Aggiorna la posizione del nemico in base alla direzione e alla variabile {@code goUp}.
     * Imposta la posizione dell'area di collisione e notifica gli osservatori dell'azione di camminata.
     */
    void walk() {
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
        hitbox.setLocation(x, y);
        notifyObservers(Action.WALK);
    }
}