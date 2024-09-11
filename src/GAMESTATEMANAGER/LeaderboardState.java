package GAMESTATEMANAGER;

import CONTROLLER.Leaderboard;
import MODEL.LeaderboardM;
import VIEW.LeaderboardPanel;
import VIEW.MainFrame;

import java.awt.*;
import java.awt.event.*;

/**
 * La classe LeaderboardState gestisce lo stato della leaderboard nel gioco.
 * Mostra i punteggi dei giocatori e offre la possibilità di tornare al menu principale o uscire dal gioco.
 */
public class LeaderboardState extends GameState {

    // Gestore degli stati del gioco
    private GameStateManager gsm;

    // Controller della leaderboard
    private Leaderboard leaderboardController;

    // Modello della leaderboard
    private LeaderboardM model;

    // Pannello grafico della leaderboard
    private LeaderboardPanel leaderboardPanel;

    /**
     * Costruttore che inizializza lo stato della leaderboard.
     * Recupera il controller, il modello e il pannello della leaderboard, e aggiunge i listener ai pulsanti del pannello.
     *
     * @param gsm il gestore degli stati del gioco
     */
    public LeaderboardState(GameStateManager gsm) {
        this.gsm = gsm;
        leaderboardController = gsm.getLeaderboard();
        model = leaderboardController.getModel();
        leaderboardPanel = MainFrame.getLeaderboardPanel();
        addListeners(); // Aggiunge i listener ai pulsanti
    }

    /**
     * Aggiunge i listener agli elementi del pannello della leaderboard.
     * Gestisce le azioni per uscire dall'applicazione o tornare al menu principale.
     */
    private void addListeners() {
        leaderboardPanel.getExitLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logica per uscire dall'applicazione
                System.exit(0); // Uscita dall'applicazione
            }
        });

        leaderboardPanel.getMenuLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logica per tornare al menu principale
                gsm.setState(gsm.menuState); // Torna al menu principale
            }
        });
    }

    /**
     * Metodo di aggiornamento che viene chiamato ad ogni frame di gioco.
     * Attualmente vuoto, ma può essere usato per aggiornare la logica della leaderboard.
     */
    @Override
    public void update() {
        // Implementa la logica di aggiornamento se necessaria
    }

    /**
     * Disegna il pannello della leaderboard nella finestra principale.
     */
    @Override
    public void draw() {
        MainFrame.setPanel(leaderboardPanel);
    }

    /**
     * Gestisce gli eventi ActionEvent.
     * Attualmente non utilizzato, ma disponibile per eventuali esigenze future.
     *
     * @param e l'evento ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Gestione degli eventi ActionEvent se necessario
    }

    /**
     * Gestisce gli eventi di digitazione dei tasti.
     * Attualmente non utilizzato.
     *
     * @param e l'evento KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Gestione degli eventi KeyEvent se necessario
    }

    /**
     * Gestisce gli eventi di pressione dei tasti.
     * Attualmente non utilizzato.
     *
     * @param e l'evento KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Gestione degli eventi KeyEvent se necessario
    }

    /**
     * Gestisce gli eventi di rilascio dei tasti.
     * Attualmente non utilizzato.
     *
     * @param e l'evento KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Gestione degli eventi KeyEvent se necessario
    }

    /**
     * Gestisce gli eventi di clic del mouse.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    /**
     * Gestisce l'evento di pressione del mouse.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    /**
     * Gestisce l'evento di rilascio del mouse.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    /**
     * Gestisce l'evento di ingresso del mouse nel componente.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    /**
     * Gestisce l'evento di uscita del mouse dal componente.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }
}