package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe OrangeUmbrella rappresenta un PowerUp che concede al giocatore un effetto di ombrello arancione.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class OrangeUmbrella extends PowerUp {

    /**
     * Costruttore della classe OrangeUmbrella. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public OrangeUmbrella() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp OrangeUmbrella
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/UmbrellaOrange.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, aumenta il livello successivo del giocatore di 4.
     * Se il livello risultante supera il massimo (24), il livello successivo sarà impostato a 24.
     */
    @Override
    public void activateEffect() {
        // Calcola il prossimo livello. Aumenta il livello corrente di 4, ma non superare il livello massimo (24).
        int nextLevelInt;
        if (gsm.getNextLevelInt() + 4 < 24) nextLevelInt = gsm.getNextLevelInt() + 4;
        else nextLevelInt = 24;

        // Imposta il prossimo livello
        gsm.setNextLevelInt(nextLevelInt);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}