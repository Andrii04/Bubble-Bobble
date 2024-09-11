package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe Clock rappresenta un PowerUp che consente di attivare o disattivare un effetto di orologio nel livello corrente.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class Clock extends PowerUp {

    /**
     * Costruttore della classe Clock. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public Clock() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp Clock
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/Clock.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, commuta lo stato del clock nel livello corrente (attiva/disattiva l'effetto di clock).
     */
    @Override
    public void activateEffect() {
        // Inverte lo stato del clock nel livello corrente
        currentLevel.setClock(!currentLevel.isClock());
        // Rimuove il PowerUp dal gioco
        erase();
    }
}
