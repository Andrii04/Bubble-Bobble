package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe BlueCandy rappresenta un PowerUp che aumenta la velocità delle bolle del giocatore.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class BlueCandy extends PowerUp {

    /**
     * Costruttore della classe BlueCandy. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public BlueCandy() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp BlueCandy
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (3).png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, aumenta la velocità delle bolle del giocatore
     * e aggiorna il conteggio delle caramelle blu mangiate dal giocatore.
     * Infine, rimuove il PowerUp dal gioco.
     */
    @Override
    public void activateEffect() {
        // Imposta la velocità delle bolle del giocatore a 11
        player.setBubbleSpeed(11);
        // Incrementa il conteggio delle caramelle blu mangiate dal giocatore
        player.eatBlueCandy();
        // Rimuove il PowerUp dal gioco
        erase();
    }
}
