package CONTROLLER;

import GAMESTATEMANAGER.GameStateManager;
import java.awt.event.*;
import MODEL.*;
import VIEW.*;


/**
 * La classe Controller gestisce gli eventi di input e delega le azioni al
 * GameStateManager. È implementata come un singleton per garantire che ci sia
 * solo una istanza di Controller in tutto il programma.
 */
public class Controller implements KeyListener, MouseListener, ActionListener {
    private GameStateManager gsm; // Riferimento al gestore degli stati del gioco
    private static Controller instance; // Istanze singleton del Controller

    /**
     * Costruttore privato per impedire la creazione di più istanze di Controller.
     * Inizializza il GameStateManager.
     */
    private Controller() {
        gsm = GameStateManager.getInstance();
    }

    /**
     * Restituisce l'istanza singleton del Controller. Crea una nuova istanza
     * se non esiste ancora.
     *
     * @return l'istanza singleton del Controller
     */
    public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent k) {
        gsm.keyTyped(k); // Delega l'evento keyTyped al GameStateManager
    }

    @Override
    public void keyPressed(KeyEvent k) {
        gsm.keyPressed(k); // Delega l'evento keyPressed al GameStateManager
    }

    @Override
    public void keyReleased(KeyEvent k) {
        gsm.keyReleased(k); // Delega l'evento keyReleased al GameStateManager
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gsm.mouseClicked(e); // Delega l'evento mouseClicked al GameStateManager
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gsm.mousePressed(e); // Delega l'evento mousePressed al GameStateManager
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gsm.mouseReleased(e); // Delega l'evento mouseReleased al GameStateManager
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        gsm.mouseEntered(e); // Delega l'evento mouseEntered al GameStateManager
    }

    @Override
    public void mouseExited(MouseEvent e) {
        gsm.mouseExited(e); // Delega l'evento mouseExited al GameStateManager
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gsm.actionPerformed(e); // Delega l'evento actionPerformed al GameStateManager
    }

    /**
     * Carica un livello specificato nel LevelEditor e aggiorna la vista.
     *
     * @param level il numero del livello da caricare
     */
    public void LEloadLevel(int level) {
        Level selectedLevel = gsm.getLevel(level); // Ottiene il livello dal GameStateManager
        LevelEditor.getInstance().setCurrentLevel(selectedLevel); // Imposta il livello corrente nel LevelEditor
        MainFrame.getLevelEditorPanel().setCurrentLevel(selectedLevel); // Aggiorna la vista del LevelEditor
    }

    /**
     * Imposta lo stato dei pulsanti nella vista del LevelEditor per riflettere
     * se il pulsante "solid" o "remove" è attivo o meno.
     *
     * @param solidON true se il pulsante "solid" deve essere attivo, false altrimenti
     * @param removeON true se il pulsante "remove" deve essere attivo, false altrimenti
     */
    public void LEsetButtons(boolean solidON, boolean removeON) {
        // Imposta lo stato dei pulsanti nella vista del LevelEditor
    }

    /**
     * Aggiunge un blocco alla vista del LevelEditor nella posizione specificata.
     *
     * @param blockRow la riga del blocco da aggiungere
     * @param blockCol la colonna del blocco da aggiungere
     */
    public void LEaddBlock(int blockRow, int blockCol) {
        // Aggiunge un blocco alla vista del LevelEditor
    }

    /**
     * Rimuove un blocco dalla vista del LevelEditor nella posizione specificata.
     *
     * @param blockRow la riga del blocco da rimuovere
     * @param blockCol la colonna del blocco da rimuovere
     */
    public void LEremoveBlock(int blockRow, int blockCol) {
        // Rimuove un blocco dalla vista del LevelEditor
    }

    /**
     * Imposta un blocco come solido nella vista del LevelEditor nella posizione specificata.
     *
     * @param blockRow la riga del blocco da impostare come solido
     * @param blockCol la colonna del blocco da impostare come solido
     */
    public void LEsetSolid(int blockRow, int blockCol) {
        // Imposta un blocco come solido nella vista del LevelEditor
    }

    /**
     * Ridisegna il livello corrente nella vista del LevelEditor.
     */
    public void LEredrawLevel() {
        LevelEditorPanel levelEditorPanel = MainFrame.getLevelEditorPanel(); // Ottiene il pannello del LevelEditor
        levelEditorPanel.setRedrawingLevel(true); // Imposta il flag per ridisegnare il livello
        levelEditorPanel.repaint(); // Ridisegna il pannello
    }
}