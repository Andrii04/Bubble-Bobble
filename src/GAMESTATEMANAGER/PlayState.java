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

public class PlayState extends GameState {
    private Player player; // Riferimento al giocatore
    private ArrayList<Enemy> currentEnemies; // Lista degli attuali nemici nel livello

    // Riferimento al gestore degli stati del gioco
    private GameStateManager gsm;

    // Costruttore che inizializza il giocatore e carica il livello
    public PlayState(GameStateManager gsm) {
        this.player = gsm.getCurrentPlayer(); // Ottiene il giocatore corrente dal gestore degli stati
        this.gsm = gsm;
        player.updateAction(IDLE); // Imposta l'azione del giocatore a "IDLE" (fermo)
        loadNewLevel(); // Carica il nuovo livello
    }

    // Metodo per caricare un nuovo livello
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

    // Metodo che viene chiamato ad ogni frame per aggiornare lo stato del gioco
    @Override
    public void update() {
        // automatic fall

        if (!player.isOnFloor()) {
            player.setIsOnFloor(false);
            player.updateAction(MOVE_VERTICALLY);
        }

        if(currentEnemies.isEmpty()){
            if(gsm.getCurrentLevelInt()<24){
                gsm.setNextLevelInt(gsm.getCurrentLevelInt() +1);

                gsm.setCurrentLevel(gsm.getNextLevelInt());
                player.setCurrentLevel(gsm.getCurrentLevel());
                loadNewLevel();
            }
            else{
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

        // Controlla se il giocatore ha finito le vite
        if (player.getLives() <= 0) {
            MainFrame.stopSound();
            player.getProfile().incrementaPartitePerse();
            player.getProfile().setPunteggio(player.getPunteggio());
            player.getProfile().setRound(gsm.getCurrentLevelInt());
            gsm.setState(GameStateManager.loseState);
            MainFrame.playSound(4);

        }


    }
    public Player getPlayer(){
        return player;
    }

    // Disegna la schermata di gioco
    @Override
    public void draw() {
        MainFrame.setPanel(MainFrame.getPlayPanel(new PlayerView(player)));
    }

    // Metodo per gestire quando viene premuto un tasto (non utilizzato qui)
    @Override
    public void keyTyped(KeyEvent k) {

    }

    @Override
    public void keyPressed(KeyEvent k) {
        switch(k.getKeyCode()) {
            case KeyEvent.VK_UP:
                player.updateAction(JUMP);
                break;
            case KeyEvent.VK_LEFT:
                player.setFacingRight(false);
                if(player.isOnFloor()) {
                    player.updateAction(WALK);
                }
                break;
            case KeyEvent.VK_RIGHT:
                player.setFacingRight(true);
                if(player.isOnFloor()){
                    player.updateAction(WALK);
                }
                break;
            case KeyEvent.VK_E:
                player.updateAction(ATTACK);
                break;
            case KeyEvent.VK_ESCAPE: {
                gsm.setState(pauseState);  // Assicurati che il metodo getPauseState() esista e ritorni lo stato di pausa
                break;
            }
        }
    }

    // Gestisce quando un tasto viene rilasciato
    @Override
    public void keyReleased(KeyEvent k) {
        if( k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT|| k.getKeyCode() == KeyEvent.VK_E){
            player.updateAction(IDLE);
        }


    }

    // Metodo per gestire l'azione eseguita (non utilizzato qui)
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
