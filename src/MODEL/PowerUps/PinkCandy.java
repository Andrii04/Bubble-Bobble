package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe PinkCandy rappresenta un PowerUp che concede al giocatore un effetto di caramella rosa.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class PinkCandy extends PowerUp {

    /**
     * Costruttore della classe PinkCandy. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public PinkCandy() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp PinkCandy
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Candie (1).png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, aumenta la distanza massima delle bolle del giocatore
     * e incrementa il contatore delle caramelle rosa mangiate.
     */
    @Override
    public void activateEffect() {
        // Imposta la distanza massima delle bolle del giocatore a 100
        player.setMaxBubbleDistance(100);
        // Incrementa il contatore delle caramelle rosa mangiate dal giocatore
        player.eatPinkCandy();
        // Rimuove il PowerUp dal gioco
        erase();
    }
}