package MODEL.PowerUps;

import javax.swing.*;

/**
 * La classe BlueLantern rappresenta un PowerUp che conferisce al giocatore tutti e tre gli anelli di colore (rosa, blu e rosso).
 * Estende la classe astratta PowerUp e implementa l'effetto specifico del PowerUp.
 */
public class BlueLantern extends PowerUp {

    /**
     * Costruttore della classe BlueLantern. Imposta l'icona del PowerUp utilizzando un'immagine specifica.
     */
    public BlueLantern() {
        super();
        // Percorso dell'immagine dell'icona per il PowerUp BlueLantern
        String skinPath = "/Resources/Bubble Bobble Resources/Power Up/LanternBlue.png";
        // Imposta l'icona del PowerUp utilizzando il percorso dell'immagine
        setSkin(new ImageIcon(getClass().getResource(skinPath)));
    }

    /**
     * Attiva l'effetto del PowerUp. In questo caso, conferisce al giocatore tutti e tre gli anelli di colore (rosa, blu e rosso)
     * e rimuove il PowerUp dal gioco.
     */
    @Override
    public void activateEffect() {
        // Abilita l'anello rosa per il giocatore
        player.setPinkRing(true);
        // Abilita l'anello blu per il giocatore
        player.setBlueRing(true);
        // Abilita l'anello rosso per il giocatore
        player.setRedRing(true);
        // Rimuove il PowerUp dal gioco
        erase();
    }
}