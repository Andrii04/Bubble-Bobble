package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe RedRing rappresenta un PowerUp che attiva un effetto speciale sul giocatore quando viene raccolto.
 */
public class RedRing extends PowerUp {

    /**
     * Costruttore della classe RedRing. Imposta l'icona del PowerUp.
     */
    public RedRing() {
        super();             // Chiama il costruttore della classe base PowerUp
        // Definisce il percorso dell'immagine del PowerUp

        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/RingRed.png";

        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine

        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. Abilita l'anello rosso per il giocatore e rimuove il PowerUp.
     */
    @Override
    public void activateEffect() {
        // Imposta l'anello rosso del giocatore su true
        player.setRedRing(true);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}