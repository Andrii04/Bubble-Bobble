package GAMESTATEMANAGER;

import MODEL.Player;
import VIEW.MainFrame;
import VIEW.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static MODEL.Entity.Action.*;

public class PlayState extends GameState {
    private Player player;
    // level
    private GameStateManager gsm;
    public PlayState(Player player, GameStateManager gsm) {
        this.player = player;
        this.gsm = gsm;
        player.updateAction(IDLE);
    }
    // if tile general solid and player moving ( if upwards false ) general collision = true
    // if tile bubble solid and bubble moving then bubble collision = true

    @Override
    public void update() {

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
                player.updateAction(MOVE_UP);
                break;
            case KeyEvent.VK_DOWN:
                player.updateAction(MOVE_DOWN);
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
        if(k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_DOWN || k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT|| k.getKeyCode() == KeyEvent.VK_E){
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
