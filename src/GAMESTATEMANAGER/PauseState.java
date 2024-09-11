package GAMESTATEMANAGER;

import VIEW.MainFrame;
import VIEW.PausePanel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * La classe PauseState gestisce lo stato di pausa del gioco.
 * Quando il gioco è in pausa, mostra un menu con opzioni come "Continua", "Leaderboard" e "Esci".
 */
public class PauseState extends GameState {

    // Riferimento al gestore degli stati del gioco
    private final GameStateManager gsm;

    // Riferimento al pannello di pausa (view) recuperato dal MainFrame
    private final PausePanel view = MainFrame.getPausePanel();

    /**
     * Costruttore che inizializza lo stato di pausa.
     *
     * @param gsm il gestore degli stati del gioco che controlla i vari stati del gioco
     */
    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    /**
     * Metodo di aggiornamento dello stato. In questo caso, non ci sono aggiornamenti
     * da eseguire mentre il gioco è in pausa.
     */
    public void update() {}

    /**
     * Disegna il pannello di pausa, impostando il pannello corretto nella finestra principale.
     */
    public void draw() {
        MainFrame.setPanel(view); // Cambia il pannello visualizzato alla schermata di pausa
    }

    /**
     * Gestisce l'evento di digitazione di un tasto. Non è utilizzato in questo stato.
     *
     * @param k l'evento KeyEvent generato dalla pressione di un tasto
     */
    @Override
    public void keyTyped(KeyEvent k) {}

    /**
     * Gestisce la pressione di un tasto durante lo stato di pausa.
     * Implementa la navigazione nel menu e la selezione delle opzioni.
     *
     * @param k l'evento KeyEvent generato dalla pressione di un tasto
     */
    @Override
    public void keyPressed(KeyEvent k) {
        // Verifica quale tasto è stato premuto
        switch(k.getKeyCode()) {
            case KeyEvent.VK_UP:
                // Sposta il cursore in alto nel menu di pausa
                view.cursorUp();
                break;
            case KeyEvent.VK_DOWN:
                // Sposta il cursore in basso nel menu di pausa
                view.cursorDown();
                break;
            case KeyEvent.VK_ENTER:
                // Gestisce l'azione selezionata in base all'opzione corrente del menu
                switch(view.getSelectedOption()) {
                    case 0:
                        // Opzione "Continua": riprende il gioco
                        gsm.continueGame();
                        break;
                    case 1:
                        // Opzione "Leaderboard": cambia lo stato a leaderboardState (schermata della classifica)
                        gsm.setState(GameStateManager.leaderboardState);
                        break;
                    case 2:
                        // Opzione "Esci": chiude l'applicazione
                        System.exit(0);
                        break;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                // Se viene premuto ESC, riprende il gioco
                gsm.continueGame();
        }

        // Le seguenti condizioni sembrano ridondanti perché già gestite nel blocco switch sopra
        if (k.getKeyCode() == KeyEvent.VK_UP) {
            view.cursorUp(); // Sposta il cursore in alto
        }
        if (k.getKeyCode() == KeyEvent.VK_DOWN) {
            view.cursorDown(); // Sposta il cursore in basso
        }
        if (k.getKeyCode() == KeyEvent.VK_ENTER) {
            // Ripete la gestione delle opzioni del menu
            switch (view.getSelectedOption()) {
                case 0:
                    gsm.continueGame(); // Riprende il gioco
                    break;
                case 1:
                    gsm.setState(GameStateManager.leaderboardState); // Cambia allo stato della leaderboard
                    break;
                case 2:
                    System.exit(0); // Esce dall'applicazione
                    break;
            }
        }
    }

    /**
     * Gestisce il rilascio di un tasto. Non utilizzato in questo stato.
     *
     * @param k l'evento KeyEvent generato dal rilascio di un tasto
     */
    @Override
    public void keyReleased(KeyEvent k) {}

    /**
     * Gestisce l'azione eseguita da un componente che genera un ActionEvent.
     * Non è utilizzato in questo stato.
     *
     * @param e l'evento ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {}

    /**
     * Gestisce l'evento di clic del mouse. Non è utilizzato in questo stato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Gestisce la pressione del mouse. Non è utilizzato in questo stato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Gestisce il rilascio del mouse. Non è utilizzato in questo stato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Gestisce l'evento di ingresso del mouse su un componente. Non è utilizzato in questo stato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Gestisce l'evento di uscita del mouse da un componente. Non è utilizzato in questo stato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {}
}