package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe BlueRing rappresenta un PowerUp che conferisce al giocatore un anello blu, attivando un effetto specifico.
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class BlueRing extends PowerUp {

    /**
     * Costruttore della classe BlueRing. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public BlueRing() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp BlueRing
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/RingedBlue.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, assegna l'anello blu al giocatore e rimuove il PowerUp dal gioco.
     */
    @Override
    public void activateEffect() {
        // Abilita l'anello blu per il giocatore
        player.setBlueRing(true);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}