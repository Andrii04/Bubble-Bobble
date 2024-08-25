package CONTROLLER;

import GAMESTATEMANAGER.GameStateManager;

import javax.swing.*;
import java.awt.event.*;
import MODEL.*;
import VIEW.*;


public class Controller implements KeyListener, MouseListener, ActionListener {
    GameStateManager gsm;
    private static Controller instance;

    private Controller() {
        gsm = GameStateManager.getInstance();
    }

    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent k) {
        gsm.keyTyped(k);
    }

    @Override
    public void keyPressed(KeyEvent k) {
                gsm.keyPressed(k);
    }

    @Override
    public void keyReleased(KeyEvent k) {
        gsm.keyReleased(k);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gsm.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gsm.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gsm.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        gsm.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        gsm.mouseExited(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gsm.actionPerformed(e);
    }

    //LE sta per Level Editor (i metodi associati al Level Editor)
    public void LEloadLevel(int level) {

        Level selectedLevel = gsm.getLevel(level);
        LevelEditor.getInstance().setCurrentLevel(selectedLevel);
        MainFrame.getLevelEditorPanel().setCurrentLevel(selectedLevel);

    }
    public void LEsetButtons(boolean solidON, boolean removeON) {
        //setta la view dei pulsanti remove, solid e altri se ce ne saranno,
        //in base al loro valore nell'istanza di LevelEditor
    }
    public void LEaddBlock(int blockRow, int blockCol) {
        //aggiunge il blocco alla view del LevelEditor, forse chiamando un suo metodo
    }
    public void LEremoveBlock(int blockRow, int blockCol) {
        //rimuove il blocco dalla view del LevelEditor, forse chiamando un suo metodo
    }
    public void LEsetSolid(int blockRow, int blockCol) {
        //setta il blocco a solid nella view del LevelEditor, forse chiamando un suo metodo
        //ancora da programmare come appaiono i blocchi non solidi nel levelEditor
        //probabilmente un po' opachi e trasparenti
    }
}
