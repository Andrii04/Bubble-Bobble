package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe RedUmbrella rappresenta un PowerUp che avanza il giocatore di un certo numero di livelli
 * quando viene raccolto.
 */
public class RedUmbrella extends PowerUp {

    /**
     * Costruttore della classe RedUmbrella. Imposta l'icona del PowerUp.
     */
    public RedUmbrella() {
        super(); // Chiama il costruttore della classe base PowerUp
        // Definisce il percorso dell'immagine del PowerUp
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaRed.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. Avanza il livello del gioco e rimuove il PowerUp.
     */
    @Override
    public void activateEffect() {
        int nextLevelInt;
        // Determina il livello successivo. Se avanzare di 8 livelli supera il livello 24, imposta il livello a 24.
        if (gsm.getNextLevelInt() + 8 < 24) nextLevelInt = gsm.getNextLevelInt() + 8;
        else nextLevelInt = 24;

        // Imposta il prossimo livello nel GameStateManager
        gsm.setNextLevelInt(nextLevelInt);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}