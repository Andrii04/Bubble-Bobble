package GAMESTATEMANAGER;

import MODEL.LevelEditor;
import VIEW.LosePanel;
import VIEW.MainFrame;
import VIEW.MenuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class LoseState extends GameState{

        public LoseState(){}


        public void update() {}

        public void draw() {
            MainFrame.setPanel(MainFrame.getPausePanel());}


        @Override
        public void keyTyped(KeyEvent k) {

        }

        @Override
        public void keyPressed(KeyEvent k) {

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



