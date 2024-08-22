package GAMESTATEMANAGER;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import MODEL.*;

import javax.swing.*;

public class GameStateManager implements KeyListener, MouseListener, ActionListener {

    public static final int menuState = 0;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int leaderboardState = 3;
    public static final int leveleditorState = 4;
    public static final int userCreationState = 5;

    private static GameStateManager instance;
    private List<GameState> gsm;
    private GameState currentState;
    private int stateNum;

    private LevelEditor levelEditor;
    private List<Level> levels;
    
    private GameStateManager() {
        
        gsm = new ArrayList<GameState>();
        
        gsm.add(new MenuState(this));
        gsm.add(new PlayState());
        gsm.add(new PauseState());
        gsm.add(new LeaderboardState());
        gsm.add(new LevelEditorState());
        gsm.add(new UserCreationState(this));

        //levelEditor = LevelEditor.getInstance();
        stateNum = menuState;
        setState(GameStateManager.menuState);

        levels = new ArrayList<>();
    }

    public static GameStateManager getInstance() {

        if (instance == null) instance = new GameStateManager();
        return instance;
    }
    public int getStateNum(){
        return stateNum;
    }

    public void setState(int state) {
        currentState = gsm.get(state);
        stateNum = state;
        currentState.draw();
    }

    public void addLevel(Level level) {
        levels.add(level);
        //Probabilmente qua verrà chiamato un metodo del Controller che
        //Dice alla View di aggiungere il livello alla rispettiva lista
        //nel LevelEditor
    }
    public void removeLevel(Level level) {
        levels.remove(level);
        //Probabilmente qua verrà chiamato un metodo del Controller che
        //Dice alla View di togliere il livello dalla rispettiva lista
        //nel LevelEditor
    }

    public void update() {currentState.update();}

    public void draw() {currentState.draw();}

    @Override
    public void keyTyped(KeyEvent k) {currentState.keyTyped(k);}

    @Override
    public void keyPressed(KeyEvent k) {currentState.keyPressed(k);}

    @Override
    public void keyReleased(KeyEvent k) {currentState.keyReleased(k);}

    @Override
    public void mouseClicked(MouseEvent e) {
        currentState.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentState.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentState.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        currentState.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        currentState.mouseExited(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentState.actionPerformed(e);
    }
}