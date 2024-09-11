package GAMESTATEMANAGER;

import VIEW.MainFrame;
import VIEW.PausePanel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PauseState extends GameState {

    // Riferimento al gestore degli stati del gioco
    private final GameStateManager gsm;

    // Riferimento al pannello di pausa (view) recuperato dal MainFrame
    private final PausePanel view = MainFrame.getPausePanel();

    // Costruttore che riceve il gestore degli stati del gioco
    public PauseState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    // Metodo vuoto update: in pausa non ci sono aggiornamenti da fare
    public void update() {}

    // Metodo che disegna la schermata di pausa, impostando il pannello corretto
    public void draw() {
        MainFrame.setPanel(view); // Cambia il pannello visualizzato alla schermata di pausa
    }

    // Metodo per gestire l'evento di un tasto premuto (non utilizzato in questo stato)
    @Override
    public void keyTyped(KeyEvent k) {}

    // Metodo per gestire quando viene premuto un tasto
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

    // Metodo per gestire quando viene rilasciato un tasto (non utilizzato in questo stato)
    @Override
    public void keyReleased(KeyEvent k) {}

    // Metodo per gestire l'azione quando viene eseguita un'azione (non utilizzato in questo stato)
    @Override
    public void actionPerformed(ActionEvent e) {}

    // Metodi relativi agli eventi del mouse (non utilizzati in questo stato)
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}