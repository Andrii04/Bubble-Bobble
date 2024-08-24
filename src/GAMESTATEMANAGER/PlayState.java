package GAMESTATEMANAGER;

import MODEL.Player;
import VIEW.MainFrame;
import VIEW.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayState extends GameState {
    private Player player;
    private GameStateManager gsm;
    public PlayState(Player player, GameStateManager gsm) {
        this.player = player;
        this.gsm = gsm;
        player.idle();
    }

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
                player.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown();
                break;
            case KeyEvent.VK_LEFT:
                player.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                player.moveRight();
                break;
            case KeyEvent.VK_E:
                player.attack();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent k) {
        if(k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_DOWN || k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT || k.getKeyCode() == KeyEvent.VK_E){
            player.idle();
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
