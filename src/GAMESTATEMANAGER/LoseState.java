package GAMESTATEMANAGER;


import VIEW.LosePanel;
import VIEW.MainFrame;
import VIEW.MenuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LoseState extends GameState{

        private final GameStateManager gsm;
        private final LosePanel view= MainFrame.getLosePanel();

        public LoseState(GameStateManager gsm){this.gsm=gsm;}


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
        public void keyReleased(KeyEvent k) {

        }

        @Override
        public void actionPerformed(ActionEvent k) {

        }

        @Override
        public void mouseClicked(MouseEvent k) {

        }

        @Override
        public void mousePressed(MouseEvent k) {

        }

        @Override
        public void mouseReleased(MouseEvent k) {

        }

        @Override
        public void mouseEntered(MouseEvent k) {

        }

        @Override
        public void mouseExited(MouseEvent k) {

        }
    }



