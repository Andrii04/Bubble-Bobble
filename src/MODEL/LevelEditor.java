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

    public void modifyLevel(Level level) {
        //seleziona il livello da modificare

    }
    public void setSolid(boolean bool) {solidON = bool;}
    public void setRemove(boolean bool) {removeON = bool;}
    public boolean getSolid() {return solidON;}
    public boolean getRemove() {return removeON;}
}
