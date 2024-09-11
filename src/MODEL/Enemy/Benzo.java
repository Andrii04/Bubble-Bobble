

package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;

/**
 * La classe {@code Benzo} rappresenta un nemico che non ha mosse di attacco e infligge danni tramite contatto.
 *
 * <p>Questo nemico è caratterizzato da una velocità di movimento elevata e la capacità di saltare.
 */
public class Benzo extends Enemy {

    // non ha mosse di attacco
    // ha salto
    // danno tramite contatto

    private final int points = 500;

    /**
     * Crea un'istanza di {@code Benzo} con la posizione, la direzione e il gestore dello stato del gioco specificati.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public Benzo(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 4;
    }

    /**
     * Crea un'istanza di {@code Benzo} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public Benzo(GameStateManager gsm) {
        this(0, 0, true, gsm);
    }
}
