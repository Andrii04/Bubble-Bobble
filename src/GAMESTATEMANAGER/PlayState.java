package GAMESTATEMANAGER;

import MODEL.Enemy.Enemy;
import MODEL.Player;
import VIEW.EnemyView;
import VIEW.MainFrame;
import VIEW.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static MODEL.Entity.Action.*;

public class PlayState extends GameState {
    private Player player;
    private ArrayList<Enemy> currentEnemies;
    private ArrayList<EnemyView> currentEnemiesView;
    // level
    private GameStateManager gsm;
    public PlayState(GameStateManager gsm) {
        this.player =gsm.getCurrentPlayer();
        this.gsm = gsm;
        player.updateAction(IDLE);
        loadNewLevel();
    }

    private void loadNewLevel(){
        // istantiate enemies
        currentEnemies = gsm.getCurrentLevel().getEnemies();
        for(Enemy enemy: currentEnemies){
            enemy.setPlayer(player);
            enemy.setCurrentLevel(gsm.getCurrentLevel());
        }
        // add others
    }
    @Override
    public void update() {
        // update enemy positions based on player position
        for(Enemy enemy: currentEnemies){
            if (enemy != null) {
                if (!enemy.isBubbled() && !enemy.isDead()) {
                    enemy.onPlayer();
                    enemy.chasePlayer();
                } else {
                    enemy.updatePosition();
                }
            }
        }
        if (!player.isOnFloor() | !player.isColliding(player.getX(), player.getY() +1)) {
            player.updateAction(MOVE_VERTICALLY);
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
                player.updateAction(MOVE_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                player.updateAction(MOVE_RIGHT);
                break;
            case KeyEvent.VK_E:
                player.updateAction(ATTACK);
                break;
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
