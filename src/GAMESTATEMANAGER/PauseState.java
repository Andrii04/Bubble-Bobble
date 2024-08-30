package GAMESTATEMANAGER;

import VIEW.LosePanel;
import VIEW.MainFrame;
import VIEW.PausePanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PauseState extends GameState {

    private final GameStateManager gsm ;
    private final PausePanel view= MainFrame.getPausePanel();

    public PauseState(GameStateManager gsm) {this.gsm=gsm;}

    public void update() {}

    public void draw() {
        MainFrame.setPanel(view);
    }


    @Override
    public void keyTyped(KeyEvent k) {

    }

    @Override
    public void keyPressed(KeyEvent k) {

        if (k.getKeyCode() == KeyEvent.VK_UP) {
            view.cursorUp();
        }
        if (k.getKeyCode() == KeyEvent.VK_DOWN) {
            view.cursorDown();
        }
        if (k.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (view.getSelectedOption()) {
                case 0:
                    gsm.setState(GameStateManager.playState);
                    break;
                case 1:
                    gsm.setState(GameStateManager.leaderboardState); //deve salvare e uscire ma per questo serve Leadbord prima
                    break;

                case 2:
                    System.exit(0);
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent k) {

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
