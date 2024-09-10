package GAMESTATEMANAGER;

import VIEW.MainFrame;
import VIEW.WinPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * La classe `WinState` gestisce lo stato della schermata di vittoria del gioco.
 * Consente di navigare tra le opzioni tramite la tastiera
 * e selezionare diverse azioni (ritornare al menu principale, vedere la classifica o uscire dal gioco).
 */
public class WinState extends GameState {
    // Gestore degli stati del gioco
    GameStateManager gsm;

    // Riferimento alla vista del pannello della schermata di vittoria
    WinPanel view = MainFrame.getWinPanel();

    /**
     * Costruttore della classe WinState.
     * @param gsm il gestore degli stati del gioco (GameStateManager)
     */
    public WinState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    /**
     * Metodo per aggiornare lo stato della schermata di vittoria.
     * In questo caso, non sono presenti elementi dinamici da aggiornare.
     */
    @Override
    public void update() {
        // Nessun aggiornamento necessario nella schermata di vittoria statica
    }

    /**
     * Metodo per disegnare la vista della schermata di vittoria.
     * Questo metodo imposta il WinPanel come il pannello attivo nel MainFrame.
     */
    @Override
    public void draw() {
        MainFrame.setPanel(view);
    }

    /**
     * Metodo per gestire le azioni eseguite dai pulsanti o dai componenti interattivi della schermata.
     * @param e l'evento dell'azione (non implementato in questo caso)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Non utilizzato nella schermata di vittoria attuale
    }

    /**
     * Metodo per gestire la digitazione dei tasti.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del tasto digitato
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire la pressione dei tasti.
     * Consente di navigare tra le opzioni della schermata di vittoria (sopra/sotto) e selezionare l'opzione con INVIO.
     * @param e evento del tasto premuto
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Navigazione del cursore tramite le frecce
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            view.cursorUp();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            view.cursorDown();
        }
        // Selezione dell'opzione con il tasto INVIO
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (view.getSelectedOption()) {
                case 0:
                    // Torna al menu principale
                    gsm.resetGame();
                    gsm.setState(GameStateManager.menuState);
                    break;
                case 1:
                    // Passa allo stato della classifica
                    gsm.resetGame();
                    gsm.setState(GameStateManager.leaderboardState);
                    break;
                case 2:
                    // Esce dal gioco
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * Metodo per gestire il rilascio dei tasti.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del tasto rilasciato
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire i click del mouse.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse cliccato
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire la pressione del mouse.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse premuto
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire il rilascio del mouse.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse rilasciato
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire l'entrata del mouse nella finestra della schermata di vittoria.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse entrato
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire l'uscita del mouse dalla finestra della schermata di vittoria.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse uscito
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Non implementato
    }
}