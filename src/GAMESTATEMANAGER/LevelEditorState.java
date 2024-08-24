package GAMESTATEMANAGER;

import MODEL.LevelEditor;
import VIEW.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import VIEW.*;

public class LevelEditorState extends GameState {
    LevelEditor levelEditor;
    LevelEditorPanel view;

    public LevelEditorState() {
        levelEditor = LevelEditor.getInstance();
    }

    public void update() {
    }

    public void draw() {
        view = MainFrame.getLevelEditorPanel();
        MainFrame.setPanel(view);
    }

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
        if (e.getSource().getClass().equals(JLabel.class)) {
            JLabel source = (JLabel) e.getSource();
            String sourceText = source.getText();

            switch (sourceText) {
                case "REMOVE" -> {
                    if (levelEditor.getRemove()) levelEditor.setRemove(false);
                    else levelEditor.setRemove(true);
                }
                case "SOLID" -> {
                    if (levelEditor.getSolid()) levelEditor.setSolid(false);
                    else levelEditor.setSolid(true);
                }
                case "SELECT LEVEL" -> {
                    view.chooseLevel();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().getClass().equals(JLabel.class)) {
            JLabel source = (JLabel) e.getSource();
            source.setForeground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().getClass().equals(JLabel.class)) {
            JLabel source = (JLabel) e.getSource();
            source.setForeground(Color.green);
        }
    }
}

