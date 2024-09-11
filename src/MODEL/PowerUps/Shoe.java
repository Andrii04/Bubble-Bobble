package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe Shoe rappresenta un PowerUp che aumenta la velocità del giocatore quando viene raccolto.
 */
public class Shoe extends PowerUp {

    /**
     * Costruttore della classe Shoe. Imposta l'icona del PowerUp.
     */
    public Shoe() {
        super(); // Chiama il costruttore della classe base PowerUp
        // Definisce il percorso dell'immagine del PowerUp
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Shoe.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. Modifica la velocità del giocatore e rimuove il PowerUp.
     */
    @Override
    public void activateEffect() {
        // Imposta la velocità del giocatore a 32 quando il PowerUp viene raccolto
        player.setSpeed(32);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}