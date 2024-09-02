package GAMESTATEMANAGER;

import CONTROLLER.Leaderboard;
import MODEL.LeaderboardM;
import VIEW.LeaderboardPanel;
import VIEW.MainFrame;

import java.awt.*;
import java.awt.event.*;

public class LeaderboardState extends GameState {

    private GameStateManager gsm;
    private Leaderboard leaderboardController; // Il controller della leaderboard
    private LeaderboardM model;  // Il modello della leaderboard
    private LeaderboardPanel leaderboardPanel; // Il pannello della leaderboard


    public LeaderboardState(GameStateManager gsm) {

        this.gsm = gsm;
        model = new LeaderboardM();
        leaderboardPanel = MainFrame.getLeaderboardPanel();
        leaderboardController = new Leaderboard(model, leaderboardPanel);
        leaderboardController.loadLeaderboard("leaderboard.txt");
        addListeners();

        // Aggiungi i listener ai pulsanti
        addListeners();
    }

    private void addListeners() {
        leaderboardPanel.getExitLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logica per uscire o tornare al menu principale
                System.exit(0); // Esempio di uscita dall'applicazione
            }
        });

        leaderboardPanel.getMenuLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logica per tornare al menu
                gsm.setState(gsm.menuState); // Assumendo che tu abbia una variabile menuState in GameStateManager
            }
        });
    }

    @Override
    public void update() {
        // Implementa la logica di aggiornamento se necessaria
    }

    @Override
    public void draw() {
        MainFrame.setPanel(leaderboardPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Gestione degli eventi ActionEvent se necessario
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Gestione degli eventi KeyEvent se necessario
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Gestione degli eventi KeyEvent se necessario
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Gestione degli eventi KeyEvent se necessario
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Gestione degli eventi MouseEvent se necessario
    }
}
