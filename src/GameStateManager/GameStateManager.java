package GameStateManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import Model.*;
import View.*;
import Controller.*;

public class GameStateManager implements KeyListener {

    public static final int menuState = 0;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int leaderboardState = 3;
    public static final int leveleditorState = 4;

    private static GameStateManager instance;
    private List<GameState> gsm;
    private GameState currentState;

    private List<Level> levels;
    
    private GameStateManager() {
        
        gsm = new ArrayList<GameState>();
        
        gsm.add(new MenuState());
        gsm.add(new PlayState());
        gsm.add(new PauseState());
        gsm.add(new LeaderboardState());
        gsm.add(new LevelEditorState());

        levels = new ArrayList<>();
    }

    public static GameStateManager createGameStateManager() {

        if (instance == null) instance = new GameStateManager();
        return instance;
    }

    public void setState(int state) {
        currentState = gsm.get(state);
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
}