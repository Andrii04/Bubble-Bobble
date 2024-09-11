package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe PinkUmbrella rappresenta un PowerUp specifico che consente al giocatore di avanzare di 6 livelli nel gioco.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class PinkUmbrella extends PowerUp {

    /**
     * Costruttore della classe PinkUmbrella. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public PinkUmbrella() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp PinkUmbrella
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaPink.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, aumenta il livello corrente del giocatore di 6,
     * senza superare il livello massimo di 24.
     */
    @Override
    public void activateEffect() {
        int nextLevelInt;
        // Calcola il prossimo livello, assicurandosi di non superare il livello 24
        if (gsm.getNextLevelInt() + 6 < 24) nextLevelInt = gsm.getNextLevelInt() + 6;
        else nextLevelInt = 24;

        // Aggiorna il livello successivo nel gestore dello stato del gioco
        gsm.setNextLevelInt(nextLevelInt);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}