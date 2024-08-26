package GAMESTATEMANAGER;

import VIEW.MainFrame;
import VIEW.WinPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class WinState extends GameState{
    GameStateManager gsm;
    WinPanel view = MainFrame.getWinPanel();

    public WinState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        MainFrame.setPanel(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            view.cursorUp();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            view.cursorDown();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (view.getSelectedOption()) {
                case 0:
                    gsm.setState(GameStateManager.menuState);
                    break;
                case 1:
                    gsm.setState(GameStateManager.leaderboardState);
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
