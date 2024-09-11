package GAMESTATEMANAGER;

import MODEL.Enemy.Enemy;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static GAMESTATEMANAGER.GameStateManager.pauseState;
import static MODEL.Entity.Action.*;

/**
 * La classe PlayState gestisce lo stato di gioco attivo, in cui il giocatore
 * controlla il personaggio, interagisce con i nemici e avanza nei livelli.
 */
public class PlayState extends GameState {
    private Player player; // Riferimento al giocatore
    private ArrayList<Enemy> currentEnemies; // Lista degli attuali nemici nel livello

    // Riferimento al gestore degli stati del gioco
    private GameStateManager gsm;

    /**
     * Costruttore che inizializza il giocatore e carica il livello corrente.
     *
     * @param gsm il gestore degli stati del gioco
     */
    public PlayState(GameStateManager gsm) {
        this.player = gsm.getCurrentPlayer(); // Ottiene il giocatore corrente dal gestore degli stati
        this.gsm = gsm;
        player.updateAction(IDLE); // Imposta l'azione del giocatore a "IDLE" (fermo)
        loadNewLevel(); // Carica il nuovo livello
    }

    /**
     * Metodo privato per caricare un nuovo livello e inizializzare il giocatore e i nemici.
     */
    private void loadNewLevel() {
        // Imposta la posizione iniziale del giocatore
        player.setX(Player.defaultX);
        player.setY(Player.defaultY);

        // Inizializza i nemici nel livello corrente
        currentEnemies = gsm.getCurrentLevel().getEnemies();
        for (Enemy enemy : currentEnemies) {
            if (enemy != null) {
                enemy.setPlayer(player); // Associa il giocatore al nemico
                enemy.setCurrentLevel(gsm.getCurrentLevel()); // Associa il livello corrente al nemico
            }
        }
    }

    /**
     * Metodo chiamato ad ogni frame per aggiornare lo stato del gioco.
     * Controlla lo stato del giocatore, dei nemici e verifica la progressione nel livello.
     */
    @Override
    public void update() {
        // Verifica se il giocatore sta cadendo
        if (!player.isOnFloor()) {
            player.setIsOnFloor(false);
            player.updateAction(MOVE_VERTICALLY);
        }

        // Verifica se tutti i nemici sono stati sconfitti
        if(currentEnemies.isEmpty()){
            // Passa al livello successivo se ci sono altri livelli
            if(gsm.getCurrentLevelInt() < 24) {
                gsm.setNextLevelInt(gsm.getCurrentLevelInt() + 1);
                gsm.setCurrentLevel(gsm.getNextLevelInt());
                player.setCurrentLevel(gsm.getCurrentLevel());
                loadNewLevel();
            } else {
                // Gioco completato, aggiorna il profilo del giocatore e passa allo stato di vittoria
                MainFrame.stopSound();
                MainFrame.playSound(7);
                player.getProfile().incrementaPartiteVinte();
                player.getProfile().setPunteggio(player.getPunteggio());
                player.getProfile().setRound(gsm.getCurrentLevelInt());
                gsm.setState(GameStateManager.winState);
            }
        }

        // Aggiorna la posizione dei nemici in base alla posizione del giocatore
        for (Enemy enemy : currentEnemies) {
            if (enemy != null) {
                enemy.move();
            }
        }

        // Controlla se il giocatore ha esaurito tutte le vite
        if (player.getLives() <= 0) {
            MainFrame.stopSound();
            player.getProfile().incrementaPartitePerse();
            player.getProfile().setPunteggio(player.getPunteggio());
            player.getProfile().setRound(gsm.getCurrentLevelInt());
            gsm.setState(GameStateManager.loseState);
            MainFrame.playSound(4);
        }
    }

    /**
     * Restituisce il giocatore corrente.
     *
     * @return l'oggetto Player che rappresenta il giocatore
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Disegna la schermata di gioco, visualizzando il pannello di gioco corrente.
     */
    @Override
    public void draw() {
        MainFrame.setPanel(MainFrame.getPlayPanel(new PlayerView(player)));
    }

    /**
     * Gestisce la pressione di un tasto durante il gioco.
     *
     * @param k l'evento KeyEvent generato dalla pressione di un tasto
     */
    @Override
    public void keyPressed(KeyEvent k) {
        switch(k.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.updateAction(JUMP); // Il giocatore salta
                break;
            case KeyEvent.VK_LEFT:
                player.setFacingRight(false); // Il giocatore si muove a sinistra
                if(player.isOnFloor()) {
                    player.updateAction(WALK);
                }
                break;
            case KeyEvent.VK_RIGHT:
                player.setFacingRight(true); // Il giocatore si muove a destra
                if(player.isOnFloor()){
                    player.updateAction(WALK);
                }
                break;
            case KeyEvent.VK_E:
                player.updateAction(ATTACK); // Il giocatore attacca
                break;
            case KeyEvent.VK_ESCAPE:
                // Metti in pausa il gioco
                gsm.setState(pauseState);
                break;
        }
    }

    /**
     * Gestisce il rilascio di un tasto.
     *
     * @param k l'evento KeyEvent generato dal rilascio di un tasto
     */
    @Override
    public void keyReleased(KeyEvent k) {
        if(k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT || k.getKeyCode() == KeyEvent.VK_E) {
            player.updateAction(IDLE); // Ferma il giocatore
        }
    }

    // Altri metodi per gestire eventi come azioni, clic del mouse, etc.
    @Override
    public void keyTyped(KeyEvent k) {}

    @Override
    public void actionPerformed(ActionEvent e) {}

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