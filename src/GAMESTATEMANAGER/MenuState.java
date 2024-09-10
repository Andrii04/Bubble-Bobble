package GAMESTATEMANAGER;

import VIEW.MainFrame;
import VIEW.MenuPanel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * La classe `MenuState` gestisce lo stato del menu principale del gioco.
 * Consente di navigare tra le opzioni del menu tramite la tastiera
 * e selezionare diverse azioni tra cui: iniziare il gioco, vedere la classifica, aprire l'editor di livelli e uscire.
 */

public class MenuState extends GameState {
    // Gestore degli stati del gioco
    GameStateManager gsm;

    // Riferimento alla vista del pannello del menu
    MenuPanel view = MainFrame.getMenuPanel();

    /**
     * Costruttore della classe MenuState.
     * @param gsm il gestore degli stati del gioco (GameStateManager)
     */

    public MenuState(GameStateManager gsm) {
        MainFrame.playMusic(6);
        this.gsm = gsm;
    }

    /**
     * Metodo per aggiornare lo stato del menu.
     * In questo caso, non sono presenti elementi dinamici da aggiornare.
     */
    @Override
    public void update() {
        // Nessun aggiornamento necessario nel menu statico
    }

    /**
     * Metodo per disegnare la vista del menu.
     * Questo metodo imposta il MenuPanel come il pannello attivo nel MainFrame.
     */
    @Override
    public void draw() {
        MainFrame.setPanel(view);
    }

    /**
     * Metodo per gestire le azioni eseguite dai pulsanti o dai componenti interattivi del menu.
     * @param e l'evento dell'azione (non implementato in questo caso)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Non utilizzato nel menu attuale
    }

    /**
     * Metodo per gestire gli eventi dei tasti.
     * Non è implementato in questo stato.
     * @param e evento del tasto digitato
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Non implementato
    }

    /**
     * Consente di navigare tra le opzioni del menu (sopra/sotto) e selezionare l'opzione con INVIO.
     * @param e evento del tasto premuto
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Navigazione del cursore nel menu tramite le frecce
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
                    // Passa allo stato di creazione dell'utente
                    gsm.setState(GameStateManager.userCreationState);
                    break;
                case 1:
                    // Passa allo stato della classifica
                    gsm.setState(GameStateManager.leaderboardState);
                    break;
                case 2:
                    // Passa allo stato dell'editor di livelli
                    gsm.setState(GameStateManager.leveleditorState);
                    break;
                case 3:
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
     * Metodo per gestire l'entrata del mouse nella finestra del menu.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse entrato
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Non implementato
    }

    /**
     * Metodo per gestire l'uscita del mouse dalla finestra del menu.
     * Questo metodo non è implementato in questo stato.
     * @param e evento del mouse uscito
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Non implementato
    }
}