package GAMESTATEMANAGER;

import MODEL.Enemy.Enemy;
import MODEL.Entity;
import MODEL.Player;
import VIEW.EnemyView;
import VIEW.MainFrame;
import VIEW.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static GAMESTATEMANAGER.GameStateManager.pauseState;
import static MODEL.Entity.Action.*;

public class PlayState extends GameState {
    private int numOfLevels;
    private Player player;
    private ArrayList<Enemy> currentEnemies;
    private ArrayList<EnemyView> currentEnemiesView;
    // level
    private GameStateManager gsm;


    public PlayState(GameStateManager gsm) {
        this.player = gsm.getCurrentPlayer();
        this.gsm = gsm;
        player.updateAction(IDLE);
        numOfLevels = 24;
        loadNewLevel();
    }

    private void loadNewLevel(){
        // player position
        player.setX(Player.defaultX);
        player.setY(Player.defaultY);
        // istantiate enemies
        currentEnemies = gsm.getCurrentLevel().getEnemies();
        for(Enemy enemy: currentEnemies){
            if (enemy != null) {
                enemy.setPlayer(player);
                enemy.setCurrentLevel(gsm.getCurrentLevel());
            }
        }
        // add others
    }
    @Override
    public void update() {
        // automatic fall

        if (!player.isOnFloor()) {
            player.setIsOnFloor(false);
            player.updateAction(MOVE_VERTICALLY);
        }

        if(currentEnemies.isEmpty()){
            if(gsm.getCurrentLevelInt()<numOfLevels){
                gsm.setCurrentLevel(gsm.getCurrentLevelInt()+1);
                player.setCurrentLevel(gsm.getCurrentLevel());
                loadNewLevel();
            }
            else{
                gsm.resetGame();
                gsm.setState(GameStateManager.winState);
            }
        }
        // update enemy positions based on player position
        for(Enemy enemy: currentEnemies){
            if (enemy != null) {
                enemy.move();
            }
        }

        if (player.getLives() <= 0) {
            gsm.setState(GameStateManager.loseState);
        }


    }
    public Player getPlayer(){
        return player;
    }
    @Override
    public void draw() {
        MainFrame.setPanel(MainFrame.getPlayPanel(new PlayerView(player)));
    }

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

    @Override
    public void keyReleased(KeyEvent k) {
        if( k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT|| k.getKeyCode() == KeyEvent.VK_E){
            player.updateAction(IDLE);
        }


    }

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
