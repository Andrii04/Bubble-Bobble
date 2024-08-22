package GAMESTATEMANAGER;

import VIEW.MainFrame;
import VIEW.MenuPanel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuState extends GameState {
    GameStateManager gsm;
    MenuPanel view = MainFrame.getMenuPanel();
    public MenuState(GameStateManager gsm) {
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
                    gsm.setState(GameStateManager.playState);
                    break;
                case 1:
                    gsm.setState(GameStateManager.leaderboardState);
                    break;
                case 2:
                    gsm.setState(GameStateManager.leveleditorState);
                    break;
                case 3:
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