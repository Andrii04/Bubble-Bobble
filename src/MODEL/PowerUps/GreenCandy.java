package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe GreenCandy rappresenta un PowerUp che concede al giocatore un effetto di caramella verde.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class GreenCandy extends PowerUp {

    /**
     * Costruttore della classe GreenCandy. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public GreenCandy() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp GreenCandy
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (2).png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, riduce il tempo di ricarica del tiro del giocatore e incrementa il contatore di caramelle verdi mangiate.
     */
    @Override
    public void activateEffect() {
        // Imposta il tasso di fuoco del giocatore a 50 millisecondi
        player.setFireRate(50);
        // Incrementa il contatore di caramelle verdi mangiate dal giocatore
        player.eatGreenCandy();
        // Rimuove il PowerUp dal gioco
        erase();
    }
}