package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe PinkRing rappresenta un PowerUp che concede al giocatore un anello rosa,
 * il quale conferisce effetti specifici al giocatore.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class PinkRing extends PowerUp {

    /**
     * Costruttore della classe PinkRing. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public PinkRing() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp PinkRing
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/RingPink.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, conferisce al giocatore l'anello rosa.
     */
    @Override
    public void activateEffect() {
        // Imposta l'anello rosa al giocatore
        player.setPinkRing(true);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}