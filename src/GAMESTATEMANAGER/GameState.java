package GAMESTATEMANAGER;

import java.awt.event.*;


/**
 * Classe astratta `GameState` che rappresenta uno stato generico del gioco.
 * Ogni stato del gioco (come menu, gioco attivo, pausa, ecc.) dovrebbe
 * estendere questa classe e implementare i metodi astratti per gestire
 * l'aggiornamento, il rendering e gli eventi di input.
 *
 * Questa classe implementa le interfacce `KeyListener`, `MouseListener` e
 * `ActionListener`, per la gestione degli eventi di tastiera, mouse
 * e azioni specifiche.
 */

public abstract class GameState implements KeyListener, MouseListener, ActionListener {

    /**
     * Metodo astratto per aggiornare lo stato del gioco.
     * Ogni sottoclasse implementerà la logica per aggiornare gli elementi
     * dello stato di gioco corrente, come il movimento dei personaggi, la gestione
     * dei nemici, ecc...
     */

    public abstract void update();

    /**
     * Metodo astratto per disegnare lo stato del gioco.
     * Ogni sottoclasse implementerà la logica per il rendering degli elementi
     * visivi dello stato di gioco corrente.
     */

    public abstract void draw();
}
