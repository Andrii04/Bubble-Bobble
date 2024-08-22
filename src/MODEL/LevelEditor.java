package MODEL;

import GAMESTATEMANAGER.GameStateManager;

import java.util.Timer;

public class LevelEditor {

    GameStateManager gsm;
    private static LevelEditor instance;
    boolean solidON;
    boolean removeON;
    Level currentLevel;

    private LevelEditor() {
        //gsm = GameStateManager.getInstance();
    }
    
    public static LevelEditor getInstance() {
        if (instance == null) instance = new LevelEditor();
        return instance;
    }

    public void addLevel(int height, int width, char[] pattern, Timer spawnRate) {
        //aggiunge il nuovo livello alla lista con tutti i livelli
        gsm.addLevel(new Level(height, width,  pattern, spawnRate));
    }
    public void removeLevel(Level level) {
        //rimuove il livello dalla lista con tutti i livelli
    }
    public void modifyLevel(Level level) {
        //seleziona il livello da modificare
    }
    public void setSolid(boolean bool) {solidON = bool;}
    public void setRemove(boolean bool) {removeON = bool;}
    public boolean getSolid() {return solidON;}
    public boolean getRemove() {return removeON;}
}
