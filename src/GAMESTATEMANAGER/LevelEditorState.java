package GAMESTATEMANAGER;

import MODEL.LevelEditor;
import VIEW.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import VIEW.*;

/**
 * La classe LevelEditorState gestisce lo stato dell'editor di livelli del gioco.
 * Consente la creazione, modifica e salvataggio dei livelli attraverso un'interfaccia grafica.
 */
public class LevelEditorState extends GameState {

    // Riferimento al singleton LevelEditor per la gestione della logica dell'editor di livelli
    LevelEditor levelEditor;

    // Pannello grafico dell'editor di livelli
    LevelEditorPanel view;

    /**
     * Costruttore della classe LevelEditorState.
     * Inizializza il LevelEditor utilizzando il pattern Singleton.
     */
    public LevelEditorState() {
        levelEditor = LevelEditor.getInstance();
    }

    /**
     * Metodo di aggiornamento chiamato ogni frame, se necessario.
     * Attualmente vuoto poiché non ci sono aggiornamenti continui richiesti per questo stato.
     */
    public void update() {
    }

    /**
     * Disegna il pannello dell'editor di livelli nella finestra principale.
     */
    public void draw() {
        view = MainFrame.getLevelEditorPanel();
        MainFrame.setPanel(view);
    }

    /**
     * Gestisce l'evento di digitazione dei tasti.
     * Attualmente non utilizzato.
     *
     * @param k l'evento KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent k) {

    }

    /**
     * Gestisce l'evento di pressione dei tasti.
     * Attualmente non utilizzato.
     *
     * @param k l'evento KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent k) {

    }

    /**
     * Gestisce l'evento di rilascio dei tasti.
     * Attualmente non utilizzato.
     *
     * @param k l'evento KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent k) {

    }

    /**
     * Gestisce l'azione eseguita da un componente che genera un ActionEvent.
     * Attualmente non utilizzato.
     *
     * @param e l'evento ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Gestisce l'evento di clic del mouse.
     * Identifica se il clic è su una JLabel per gestire azioni specifiche come "REMOVE", "SOLID", "SELECT LEVEL", "SAVE", o "MENU".
     * Altrimenti, gestisce il clic sui blocchi nel livello corrente.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().getClass().equals(JLabel.class)) {
            JLabel source = (JLabel) e.getSource();
            String sourceText = source.getText();

            switch (sourceText) {
                case "REMOVE" -> {
                    levelEditor.setRemove();
                    return;
                }
                case "SOLID" -> {
                    levelEditor.setSolid();
                    return;
                }
                case "SELECT LEVEL" -> {
                    view.chooseLevel();
                    return;
                }
                case "SAVE" -> {
                    levelEditor.saveLevel();
                    return;
                }
                case "MENU" -> {
                    GameStateManager.getInstance().setState(GameStateManager.menuState);
                }
            }
            source.setForeground(Color.green); // Cambia colore del testo quando viene selezionata un'opzione
        } else if (levelEditor.getCurrentLevel() != null) {
            // Gestisce il clic sui blocchi del livello
            int mouseX = e.getX();
            int mouseY = e.getY();
            levelEditor.handleBlockClick(mouseX, mouseY);
        }
    }

    /**
     * Gestisce l'evento di pressione del mouse.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Gestisce l'evento di rilascio del mouse.
     * Attualmente non utilizzato.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Gestisce l'evento di ingresso del mouse in una JLabel.
     * Cambia il colore del testo quando il cursore del mouse passa sopra una JLabel.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource().getClass().equals(JLabel.class)) {
            JLabel source = (JLabel) e.getSource();
            source.setForeground(Color.white);
        }
    }

    /**
     * Gestisce l'evento di uscita del mouse da una JLabel.
     * Ripristina il colore del testo quando il cursore del mouse lascia una JLabel.
     *
     * @param e l'evento MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().getClass().equals(JLabel.class)) {
            JLabel source = (JLabel) e.getSource();
            source.setForeground(Color.green);
        }
    }
}
